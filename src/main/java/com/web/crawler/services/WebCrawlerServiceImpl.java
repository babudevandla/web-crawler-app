package com.web.crawler.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.cache.annotation.CacheResult;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.crawler.config.AppProperties;
import com.web.crawler.model.PageInfo;
import com.web.crawler.model.PageTreeInfo;
import com.web.crawler.model.SearchResult;
import com.web.crawler.rest.controller.WebCrawlerApiController;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerApiController.class);

	@Autowired
	public WebCrawlerListener webCrawlerListener;

	@Autowired
	private AppProperties crawlerProperties;

	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();

	@Override
	@CacheResult(cacheName = "web-crawler-service")
	public PageTreeInfo getWebCrawl(final String url, final int depth, final List<String> processedUrls) {

		LOGGER.debug("Starting crawler for url {} for depth {}", url, depth);

		if (depth < 0) {
			LOGGER.info("Maximum depth reached, backing out for url {}", url);
			return null;
		}  else {
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(url)) {
                updatedProcessedUrls.add(url);
                final PageTreeInfo pageTreeInfo = new PageTreeInfo(url);
                crawl(url).ifPresent(pageInfo -> {
                    pageTreeInfo.title(pageInfo.getTitle()).valid(true);
                    LOGGER.info("Found {} links on the web page: {}", pageInfo.getLinks().size(), url);
                    pageInfo.getLinks().forEach(link -> {
                        pageTreeInfo.addNodesItem(getWebCrawl(link.attr("abs:href"), depth - 1, updatedProcessedUrls));
                    });
                });
                return pageTreeInfo;
            } else {
                return null;
            }
        }
	}

	public Optional<PageInfo> crawl(final String url) {

		LOGGER.info("Fetching contents for url: {}", url);
		try {
			Connection connection = Jsoup.connect(url);
			final Document doc = connection.timeout(crawlerProperties.getTimeOut())
					.followRedirects(crawlerProperties.isFollowRedirects()).get();

			if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code
			// indicating that everything is great.
			{
				System.out.println("\n**Visiting** Received web page at " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
			}
			
			/** .select returns a list of links here **/
			final Elements links = doc.select("a[href]");
			final String title = doc.title();
			LOGGER.debug("Fetched title: {}, links[{}] for url: {}", title, links.nextAll(), url);
			return Optional.of(new PageInfo(title, url, links));
		} catch (final IOException | IllegalArgumentException e) {
			LOGGER.error(String.format("Error getting contents of url %s", url), e);
			return Optional.empty();
		}

	}

	/**
	 * Our main launching point for the Spider's functionality. Internally it
	 * creates spider legs that make an HTTP request and parse the response (the web
	 * page).
	 * 
	 * @param url        - The starting point of the spider
	 * @param searchWord - The word or string that you are searching for
	 */
	@Override
	@CacheResult(cacheName = "web-crawler-service")
	public List<SearchResult> search(String url, String searchWord) {
		
		List<SearchResult> results = new ArrayList<>();
		
		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			String currentUrl;

			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			webCrawlerListener.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
			Document document = webCrawlerListener.getHtmlDocument();
			boolean success = webCrawlerListener.searchForWord(searchWord);
			if (success) {
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
				SearchResult result =  new SearchResult();
				result.setTitle(document.title());
				result.setUrl(url);
				result.setSearchWord(searchWord);
				results.add(result);
			}
			this.pagesToVisit.addAll(webCrawlerListener.getLinks());
		}
		System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
		return results;
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We also do
	 * a check to make sure this method doesn't return a URL that has already been
	 * visited.
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}

}

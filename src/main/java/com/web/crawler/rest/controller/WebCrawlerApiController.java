package com.web.crawler.rest.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.crawler.config.AppProperties;
import com.web.crawler.model.PageTreeInfo;
import com.web.crawler.model.SearchResult;
import com.web.crawler.services.WebCrawlerService;

@RestController
public class WebCrawlerApiController implements WebCrawlerApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerApiController.class);

	@Autowired
	private WebCrawlerService webCrawlerService;

	@Autowired
	private AppProperties crawlerProperties;
	
	/**
	 * Exam : http://json.parser.online.fr/, http://www.mit.edu/, 
	 */
	@Override
	@GetMapping(value = "/crawler", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<PageTreeInfo> getWebCrawl(
			@NotNull @RequestParam(value = "url", required = true) final String url) {

		LOGGER.info("Request for deep crawling received for url: {}");
		int depth = crawlerProperties.getMaxDepthAllowed();
		PageTreeInfo pageTreeInfo = webCrawlerService.getWebCrawl(url,depth,null);
		
		return new ResponseEntity<>(pageTreeInfo, HttpStatus.OK);
	}
	
	
	@Override
	@GetMapping(value = "/search", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SearchResult>> search(
			@NotNull @RequestParam(value = "url", required = true) final String url,
			@NotNull @RequestParam(value = "searchWord", required = true) final String searchWord) {

		LOGGER.info("Request for search crawling received for url: {}");
		List<SearchResult> searchResult = webCrawlerService.search(url,searchWord);
		
		return new ResponseEntity<>(searchResult, HttpStatus.OK);
	}

}
package com.web.crawler.services;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.web.crawler.model.PageTreeInfo;
import com.web.crawler.model.SearchResult;

public interface WebCrawlerService {

	public PageTreeInfo getWebCrawl(String url, int depth, List<String> processedUrls);

	public List<SearchResult> search(@NotNull String url, String searchWord);

}

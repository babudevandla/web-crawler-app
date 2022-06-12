package com.web.crawler.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.web.crawler.model.PageTreeInfo;
import com.web.crawler.model.SearchResult;


public interface WebCrawlerApi {

//	@ApiOperation(value = "Get the web page tree info upto certain depth", notes = "", response = PageTreeInfo.class, tags = { "web-crawler", })
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = PageTreeInfo.class),
//			@ApiResponse(code = 400, message = "Invalid page url", response = Void.class),
//			@ApiResponse(code = 401, message = "Unauthorized to use the service", response = Void.class),
//			@ApiResponse(code = 404, message = "Page not found", response = Void.class) })
	ResponseEntity<PageTreeInfo> getWebCrawl(
			//@ApiParam(value = "Url of the webpage for getting tree info", required = true) 
			String url);

	
//	@ApiOperation(value = "Search word the web page ", notes = "", response = SearchResult.class, tags = { "web-crawler", })
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = SearchResult.class),
//			@ApiResponse(code = 400, message = "Invalid page url", response = Void.class),
//			@ApiResponse(code = 401, message = "Unauthorized to use the service", response = Void.class),
//			@ApiResponse(code = 404, message = "Page not found", response = Void.class) })
	ResponseEntity<List<SearchResult>> search(
			//@ApiParam(value = "Url of the webpage for getting tree info", required = true) 
			String url,
			//@ApiParam(value = "Search word of the webpage for getting tree info", required = true) 
			String searchWord);



}
package com.web.crawler.model;

import java.io.Serializable;

public class SearchResult implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title;

	private String url;

	private String searchWord;

	public SearchResult() {

	}

	public SearchResult(String title, String url, String searchWord) {
		super();
		this.title = title;
		this.url = url;
		this.searchWord = searchWord;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

}

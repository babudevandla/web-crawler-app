package com.web.crawler.model;

import java.io.Serializable;

import org.jsoup.select.Elements;

public class PageInfo implements Serializable {

	private static final long serialVersionUID = 1993875051659981029L;

	private String title;

	private String url;

	private Elements links;

	public PageInfo() {

	}

	public PageInfo(String title, String url, Elements links) {
		super();
		this.title = title;
		this.url = url;
		this.links = links;
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

	public Elements getLinks() {
		return links;
	}

	public void setLinks(Elements links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "PageInfo [title=" + title + ", url=" + url + ", links=" + links + "]";
	}

}
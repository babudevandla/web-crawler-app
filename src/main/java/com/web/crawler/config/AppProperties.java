package com.web.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * JavaBean bound to Application properties in application configuration.
 */
@Configuration
@ConfigurationProperties(prefix = "app.crawler")
@Validated
public class AppProperties {

	 /**
     * default depth for web crawler.
     */
    private int defaultDepth;

    /**
     * max depth allowed for a request based on service capability and SLAs to
     * prevent DOS
     */
    private int maxDepthAllowed;

    /**
     *
     * timeout for http requests in seconds
     */
    private int timeOut;

    /**
     * follow redirects for the given url
     */
    private boolean followRedirects;

	public int getDefaultDepth() {
		return defaultDepth;
	}

	public void setDefaultDepth(int defaultDepth) {
		this.defaultDepth = defaultDepth;
	}

	public int getMaxDepthAllowed() {
		return maxDepthAllowed;
	}

	public void setMaxDepthAllowed(int maxDepthAllowed) {
		this.maxDepthAllowed = maxDepthAllowed;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}
    
    
}
package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 1236. Web Crawler

public final class WebCrawler {

	private Set<String> crawledUrls;

	public List<String> crawl(String startUrl, HtmlParser parser) {
		this.crawledUrls = new HashSet<>();
		dfs(startUrl, parser);
		return new ArrayList<>(crawledUrls);
	}

	private void dfs(String url, HtmlParser parser) {
		if (crawledUrls.contains(url)) {
			return;
		}
		crawledUrls.add(url);
		for (String next : parser.getUrls(url)) {
			if (host(next).equals(host(url))) {
				dfs(next, parser);
			}
		}
	}

	private String host(String url) {
		// remove "http://"
		String ss = url.substring(7);
		return ss.split("/")[0];
	}

	public static void main(String[] args) {}

	public interface HtmlParser {
		List<String> getUrls(String url);
	}
}

package com.svetanis.datastructures.graph.webcrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1236. Web Crawler
//
// Input: startUrl -- the page to begin at; parser -- getUrls(url) returns the links found on that
// page. Follow the links, but only to pages on the same host as startUrl. Returns every page
// reached, in any order, startUrl included. On LeetCode: 1 <= urls.length <= 1000, 1 <= urls[i]
// .length <= 300, every url begins with "http://" (no port), has no fragment, and appears once;
// startUrl is one of the urls.
//
// The one idea: one dfs call is one page. The set crawledUrls holds the pages already visited, so a
// page that links back (the web is full of cycles) is not fetched twice and the walk ends. On a new
// page: record it, then follow each link whose host is the same as this page's host. All pages
// reached this way share the host of startUrl, so comparing with the current page's host is the
// same as comparing with the start. The host is what lies between "http://" (7 characters) and the
// next "/", or the end of the url.
//
// Time: O(V + E * L) -- each of V pages is fetched once; each of the E links found costs a host
// comparison that reads at most L = 300 characters.
// Space: O(V) -- the set of visited pages; the walk is at most V calls deep.
//
// Siblings: WebCrawlerQueue searches breadth first with a queue; WebCrawlerMultithreadedDfs and
// WebCrawlerMultithreadedQueue solve 1242, the same crawl on many threads.

public final class WebCrawler {

	private Set<String> crawledUrls;

	public List<String> crawl(String startUrl, HtmlParser parser) {
		this.crawledUrls = new HashSet<>(); // BASE: a fresh set for every call
		dfs(startUrl, parser);
		return new ArrayList<>(crawledUrls); // RETURN: every page reached, in no particular order
	}

	private void dfs(String url, HtmlParser parser) {
		if (crawledUrls.contains(url)) { // SKIP: already visited
			return;
		}
		crawledUrls.add(url); // RECORD
		for (String next : parser.getUrls(url)) { // WALK: each link on the page
			if (host(next).equals(host(url))) { // SKIP: a link to another host is not followed
				dfs(next, parser);
			}
		}
	}

	private String host(String url) {
		// remove "http://"
		String ss = url.substring(7);
		return ss.split("/")[0];
	}

	public static void main(String[] args) {
		String[] u = { "http://news.yahoo.com", "http://news.yahoo.com/news",
				"http://news.yahoo.com/news/topics/", "http://news.google.com",
				"http://news.yahoo.com/us" };
		Map<String, List<String>> links = new HashMap<>();
		links.put(u[0], Arrays.asList(u[4]));
		links.put(u[1], Collections.emptyList());
		links.put(u[2], Arrays.asList(u[0], u[1]));
		links.put(u[3], Arrays.asList(u[2], u[1]));
		links.put(u[4], Collections.emptyList());
		List<String> crawled = new WebCrawler().crawl(u[2], links::get);
		Collections.sort(crawled);
		System.out.println(crawled); // [http://news.yahoo.com, http://news.yahoo.com/news,
		// http://news.yahoo.com/news/topics/, http://news.yahoo.com/us]

		String[] v = { "http://news.yahoo.com", "http://news.yahoo.com/news",
				"http://news.yahoo.com/news/topics/", "http://news.google.com" };
		Map<String, List<String>> links2 = new HashMap<>();
		links2.put(v[0], Arrays.asList(v[2]));
		links2.put(v[1], Collections.emptyList());
		links2.put(v[2], Arrays.asList(v[1]));
		links2.put(v[3], Arrays.asList(v[2], v[1], v[0]));
		List<String> crawled2 = new WebCrawler().crawl(v[3], links2::get);
		Collections.sort(crawled2);
		System.out.println(crawled2); // [http://news.google.com]
	}

	public interface HtmlParser {
		List<String> getUrls(String url);
	}
}

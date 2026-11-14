package com.svetanis.datastructures.graph.webcrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

// 1236. Web Crawler
//
// Input: startUrl; parser -- parser.getUrls(url) returns the links on the page at url. Every url
// is "http://", a hostname, and an optional path; the hostname is the text up to the next '/'.
// Returns every url reachable from startUrl by following links that stay on startUrl's hostname,
// startUrl included, each once, in any order. On LeetCode: 1 <= urls.length <= 1000,
// 1 <= urls[i].length <= 300, startUrl is one of the urls, the urls are distinct, and no url has
// a port.
//
// The one idea: a breadth-first search over the pages, marked when queued. One element of visited
// is a page that is on the queue or has been taken off it. A link is added to visited at the
// moment it is put on the queue, not when it is taken off, so a page that many pages link to is
// queued once. A link is queued only when its hostname equals startUrl's, exactly: the text
// between "http://" and the next '/' must be the same, so news.yahoo.com.cn, abcnews.yahoo.com,
// and a url that carries news.yahoo.com only in its path are all left out for news.yahoo.com.
// When the queue is empty, visited holds the answer.
//
// Time: O((U + E) * L) -- U pages and E links on them; each link's hostname is cut out of a url of
// up to L characters.
// Space: O(U) -- the visited set and the queue.
//
// Siblings: WebCrawler searches depth first with recursion; WebCrawlerMultithreadedDfs and
// WebCrawlerMultithreadedQueue solve 1242, the same crawl on many threads.

public final class WebCrawlerQueue {

	public List<String> crawl(String startUrl, HtmlParser parser) {
		Set<String> visited = new HashSet<>(); // SEEN: queued now or earlier
		Queue<String> queue = new LinkedList<>();
		String host = host(startUrl);
		queue.add(startUrl);
		visited.add(startUrl); // MARK: when queued
		while (!queue.isEmpty()) { // WALK: page by page, nearest first
			String curr = queue.poll();
			List<String> targetUrls = parser.getUrls(curr);
			for (String url : targetUrls) {
				if (!visited.contains(url) && host(url).equals(host)) { // NEW and on the same host, exactly
					queue.add(url);
					visited.add(url); // MARK: when queued
				}
			}
		}
		return new ArrayList<>(visited); // RETURN: every page reached
	}

	private String host(String url) {
		// remove "http://"
		String ss = url.substring(7);
		return ss.split("/")[0]; // HOST: up to the first '/'
	}

	public static void main(String[] args) {
		String[] urls = { "http://news.yahoo.com", "http://news.yahoo.com/news", "http://news.yahoo.com/news/topics/",
				"http://news.google.com", "http://news.yahoo.com/us" };
		WebCrawlerQueue crawler = new WebCrawlerQueue();
		int[][] edges1 = { { 2, 0 }, { 2, 1 }, { 3, 2 }, { 3, 1 }, { 0, 4 } };
		System.out.println(new TreeSet<>(crawler.crawl(urls[2], parser(urls, edges1)))); // [http://news.yahoo.com, http://news.yahoo.com/news, http://news.yahoo.com/news/topics/, http://news.yahoo.com/us]
		int[][] edges2 = { { 0, 2 }, { 2, 1 }, { 3, 2 }, { 3, 1 }, { 3, 0 } };
		System.out.println(new TreeSet<>(crawler.crawl(urls[3], parser(urls, edges2)))); // [http://news.google.com]
		String[] near = { "http://news.yahoo.com", "http://abcnews.yahoo.com", "http://news.yahoo.com.cn",
				"http://news.google.com/news.yahoo.com" };
		int[][] edges3 = { { 0, 1 }, { 0, 2 }, { 0, 3 } };
		System.out.println(new TreeSet<>(crawler.crawl(near[0], parser(near, edges3)))); // [http://news.yahoo.com]
	}

	private static HtmlParser parser(String[] urls, int[][] edges) {
		Map<String, List<String>> links = new HashMap<>();
		for (String url : urls) {
			links.put(url, new ArrayList<>());
		}
		for (int[] edge : edges) {
			links.get(urls[edge[0]]).add(urls[edge[1]]); // LINK: page edge[0] links to page edge[1]
		}
		return links::get;
	}

	public interface HtmlParser {
		List<String> getUrls(String url);
	}
}

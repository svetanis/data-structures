package com.svetanis.datastructures.graph.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// 1242. Web Crawler Multithreaded
//
// Input: startUrl; parser -- parser.getUrls(url) returns the links on the page at url; it is slow,
// so many pages must be fetched at the same time on several threads. Every url is "http://", a
// hostname, and an optional path; the hostname is the text up to the next '/'. Returns every url
// reachable from startUrl by following links that stay on startUrl's hostname, startUrl included,
// each once, in any order. On LeetCode: 1 <= urls.length <= 1000, 1 <= urls[i].length <= 300,
// startUrl is one of the urls, the urls are distinct, and no url has a port.
//
// The one idea: the depth-first search of 1236, where every link found becomes its own task on a
// thread pool. Two things change because of the threads. First, two tasks can find the same link
// at the same moment, so visited is a concurrent set, and a link is claimed with visited.add(next):
// add returns true for exactly one of the callers, and only that one submits the task -- each page
// is fetched once. Second, someone must wait until the crawl is over. Here each task waits
// (future.get()) for the tasks of its own children, and the main thread runs the start page, so
// when the main thread's wait ends, every page below it has finished.
//
// A task that waits keeps its thread busy while it waits, and a page keeps waiting as long as any
// page below it is still running. That decides the kind of pool. A fixed pool of 64 threads runs
// out on a chain of 66 pages, each linking only to the next: the main thread holds page 1, the 64
// threads all wait on pages 2 to 65, and page 66 sits in the pool's queue with no thread left to
// run it -- the crawl never ends. A cached pool starts a new thread whenever none is free, so every
// submitted page runs; it needs at most one thread per page, at most 1000. The hostname test is
// exact equality with startUrl's: news.yahoo.com.cn and abcnews.yahoo.com are not news.yahoo.com.
//
// Time: O((U + E) * L) work -- U pages and E links on them, each link's hostname cut out of a url
// of up to L characters; the fetches run side by side.
// Space: O(U) -- the visited set, and up to one waiting thread per page.
//
// Siblings: WebCrawlerMultithreadedQueue keeps a fixed pool of 64 threads and lets only the main
// thread wait: tasks put their children's futures on a shared queue and never wait themselves.
// WebCrawler and WebCrawlerQueue solve 1236 on one thread.

public final class WebCrawlerMultithreadedDfs {

	private HtmlParser parser;
	private ExecutorService executor;

	public List<String> crawl(String startUrl, HtmlParser parser) {
		this.parser = parser;
		this.executor = Executors.newCachedThreadPool(); // POOL: a new thread whenever every thread is waiting

		String host = host(startUrl);
		Set<String> visited = ConcurrentHashMap.newKeySet(); // CLAIMED: safe for many threads at once
		visited.add(startUrl);
		dfs(startUrl, host, visited); // MAIN THREAD runs the start page and waits below it
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(visited); // RETURN: every page claimed
	}

	private void dfs(String url, String host, Set<String> crawledUrls) {
		List<Future<?>> futures = new ArrayList<>();
		for (String next : parser.getUrls(url)) { // WALK: the links on this page
			if (host(next).equals(host) && crawledUrls.add(next)) { // SAME HOST, and this thread claimed it
				futures.add(executor.submit(() -> dfs(next, host, crawledUrls))); // SUBMIT: the child's own task
			}
		}
		for (Future<?> future : futures) { // WAIT: for every child, holding this thread
			try {
				future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String host(String url) {
		// remove "http://"
		String ss = url.substring(7);
		return ss.split("/")[0]; // HOST: up to the first '/'
	}

	private String host2(String url) { // UNUSED: "http://" + hostname, a second way to cut it
		int index = url.indexOf('/', 7);
		return (index != -1) ? url.substring(0, index) : url;
	}

	private String extractHostname(String url) { // UNUSED: the hostname through java.net.URL
		try {
			URL u = new URL(url);
			return u.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		String[] urls = { "http://news.yahoo.com", "http://news.yahoo.com/news", "http://news.yahoo.com/news/topics/",
				"http://news.google.com", "http://news.yahoo.com/us" };
		int[][] edges1 = { { 2, 0 }, { 2, 1 }, { 3, 2 }, { 3, 1 }, { 0, 4 } };
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedDfs().crawl(urls[2], parser(urls, edges1)))); // [http://news.yahoo.com, http://news.yahoo.com/news, http://news.yahoo.com/news/topics/, http://news.yahoo.com/us]
		int[][] edges2 = { { 0, 2 }, { 2, 1 }, { 3, 2 }, { 3, 1 }, { 3, 0 } };
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedDfs().crawl(urls[3], parser(urls, edges2)))); // [http://news.google.com]
		String[] near = { "http://news.yahoo.com", "http://abcnews.yahoo.com", "http://news.yahoo.com.cn" };
		int[][] edges3 = { { 0, 1 }, { 0, 2 } };
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedDfs().crawl(near[0], parser(near, edges3)))); // [http://news.yahoo.com]
		String[] chain = new String[100];
		int[][] edges4 = new int[99][];
		for (int i = 0; i < 100; i++) {
			chain[i] = "http://a.com/%d".formatted(i);
		}
		for (int i = 0; i < 99; i++) {
			edges4[i] = new int[] { i, i + 1 };
		}
		System.out.println(new WebCrawlerMultithreadedDfs().crawl(chain[0], parser(chain, edges4)).size()); // 100
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

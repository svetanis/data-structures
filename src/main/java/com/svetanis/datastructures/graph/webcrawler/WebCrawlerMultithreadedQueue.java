package com.svetanis.datastructures.graph.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// 1242. Web Crawler Multithreaded
//
// Input: startUrl; parser -- parser.getUrls(url) returns the links on the page at url; it is slow,
// so many pages must be fetched at the same time on several threads. Every url is "http://", a
// hostname, and an optional path; the hostname is the text up to the next '/'. Returns every url
// reachable from startUrl by following links that stay on startUrl's hostname, startUrl included,
// each once, in any order. On LeetCode: 1 <= urls.length <= 1000, 1 <= urls[i].length <= 300,
// startUrl is one of the urls, the urls are distinct, and no url has a port.
//
// The one idea: the search of 1236 on a pool of 64 threads, arranged so that no task ever waits.
// A task fetches one page's links. For each link on the same hostname it claims the link in the
// concurrent visited set -- visited.add(next) returns true for exactly one caller, even when two
// tasks find the same link at the same moment -- submits a task for it, puts that task's future on
// a shared queue, and then simply ends. Only the main thread waits: it takes the futures off the
// queue one at a time and waits for each. A task puts its children's futures on the queue before
// it finishes, so when the main thread's wait for a task ends, that task's children are already
// queued. The queue is therefore empty only when every submitted task has been waited for, which
// is when the crawl is complete. Because no worker ever blocks, 64 threads are enough for any
// number of pages. The hostname test is exact equality with startUrl's: news.yahoo.com.cn and
// abcnews.yahoo.com are not news.yahoo.com.
//
// Time: O((U + E) * L) work -- U pages and E links on them, each link's hostname cut out of a url
// of up to L characters; the fetches run side by side.
// Space: O(U) -- the visited set and the queue of futures.
//
// Siblings: WebCrawlerMultithreadedDfs lets every task wait for its own children, which holds one
// thread per waiting page, so it needs a pool that can grow. WebCrawler and WebCrawlerQueue solve
// 1236 on one thread.

public final class WebCrawlerMultithreadedQueue {

	private HtmlParser parser;
	private ExecutorService executor;

	public List<String> crawl(String startUrl, HtmlParser parser) {
		this.parser = parser;
		this.executor = Executors.newFixedThreadPool(64); // POOL: fixed is safe, no worker waits

		String host = host(startUrl);
		Set<String> visited = ConcurrentHashMap.newKeySet(); // CLAIMED: safe for many threads at once
		visited.add(startUrl);
		Queue<Future<?>> queue = new ConcurrentLinkedQueue<>(); // PENDING: futures not yet waited for
		queue.add(executor.submit(() -> dfs(startUrl, host, visited, queue)));
		while (!queue.isEmpty()) { // DRAIN: only the main thread waits
			try {
				queue.poll().get(); // WAIT: its children are queued before it finishes
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		return new ArrayList<>(visited); // RETURN: every page claimed
	}

	private void dfs(String url, String host, Set<String> visited, Queue<Future<?>> queue) {
		for (String next : parser.getUrls(url)) { // WALK: the links on this page
			if (host(next).equals(host) && visited.add(next)) { // SAME HOST, and this thread claimed it
				queue.add(executor.submit(() -> dfs(next, host, visited, queue))); // SUBMIT and queue the future
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
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedQueue().crawl(urls[2], parser(urls, edges1)))); // [http://news.yahoo.com, http://news.yahoo.com/news, http://news.yahoo.com/news/topics/, http://news.yahoo.com/us]
		int[][] edges2 = { { 0, 2 }, { 2, 1 }, { 3, 2 }, { 3, 1 }, { 3, 0 } };
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedQueue().crawl(urls[3], parser(urls, edges2)))); // [http://news.google.com]
		String[] near = { "http://news.yahoo.com", "http://abcnews.yahoo.com", "http://news.yahoo.com.cn" };
		int[][] edges3 = { { 0, 1 }, { 0, 2 } };
		System.out.println(new TreeSet<>(new WebCrawlerMultithreadedQueue().crawl(near[0], parser(near, edges3)))); // [http://news.yahoo.com]
		String[] chain = new String[100];
		int[][] edges4 = new int[99][];
		for (int i = 0; i < 100; i++) {
			chain[i] = "http://a.com/%d".formatted(i);
		}
		for (int i = 0; i < 99; i++) {
			edges4[i] = new int[] { i, i + 1 };
		}
		System.out.println(new WebCrawlerMultithreadedQueue().crawl(chain[0], parser(chain, edges4)).size()); // 100
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

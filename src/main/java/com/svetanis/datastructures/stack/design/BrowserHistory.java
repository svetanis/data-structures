package com.svetanis.datastructures.stack.design;

import java.util.ArrayList;
import java.util.List;

// 1472. Design Browser History
//
// A browser that starts on a home page, visits new pages, and moves back or forward a number
// of steps. back and forward return the page they land on.
//
// One list holds the pages in the order visited, and 'current' is the index of the page shown.
// Moving back or forward only moves 'current', stopped at either end of the list. Visiting a
// page first drops every page after 'current', since those forward pages can no longer be
// reached, then appends the new page.

public final class BrowserHistory {
	// Time Complexity: visit O(1) amortized, a page is removed at most once; back, forward O(1)
	// Space Complexity: O(n) for the n pages visited

	private int current;
	private List<String> list;

	public BrowserHistory(String homepage) {
		this.current = 0;
		this.list = new ArrayList<>();
		this.list.add(homepage);
	}

	public void visit(String url) {
		while (list.size() > current + 1) {
			list.remove(list.size() - 1); // a forward page, unreachable after a new visit
		}
		list.add(url);
		current++;
	}

	public String back(int steps) {
		current = Math.max(0, current - steps); // no further back than the home page
		return list.get(current);
	}

	public String forward(int steps) {
		current = Math.min(list.size() - 1, current + steps);
		return list.get(current);
	}

	public static void main(String[] args) {
		BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
		browserHistory.visit("google.com");
		browserHistory.visit("facebook.com");
		browserHistory.visit("youtube.com");
		System.out.println(browserHistory.back(1));
		System.out.println(browserHistory.back(1));
		System.out.println(browserHistory.forward(1));
		browserHistory.visit("linkedin.com");
		System.out.println(browserHistory.forward(2));
		System.out.println(browserHistory.back(2));
		System.out.println(browserHistory.back(7));
	}
}

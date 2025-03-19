package com.svetanis.datastructures.stack;

import java.util.ArrayList;
import java.util.List;

// 1472. Design Browser History

public final class BrowserHistory {

	private int current;
	private List<String> list;

	public BrowserHistory(String homepage) {
		this.current = 0;
		this.list = new ArrayList<>();
		this.list.add(homepage);
	}

	public void visit(String url) {
		while (list.size() > current + 1) {
			list.remove(list.size() - 1);
		}
		list.add(url);
		current++;
	}

	public String back(int steps) {
		current = Math.max(0, current - steps);
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

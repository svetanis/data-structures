package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 1472. Design Browser History

public final class BrowserHistoryStack {

	private Deque<String> back;
	private Deque<String> forward;

	public BrowserHistoryStack(String homepage) {
		this.back = new ArrayDeque<>();
		this.forward = new ArrayDeque<>();
		visit(homepage);
	}

	public void visit(String url) {
		back.push(url);
		forward.clear();
	}

	public String back(int steps) {
		while (steps > 0 && back.size() > 1) {
			forward.push(back.pop());
			steps--;
		}
		return back.peek();
	}

	public String forward(int steps) {
		while (steps > 0 && !forward.isEmpty()) {
			back.push(forward.pop());
			steps--;
		}
		return back.peek();
	}

	public static void main(String[] args) {
		BrowserHistoryStack browserHistory = new BrowserHistoryStack("leetcode.com");
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

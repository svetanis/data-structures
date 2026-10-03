package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 1472. Design Browser History
//
// A browser that starts on a home page, visits new pages, and moves back or forward a number
// of steps. back and forward return the page they land on.
//
// Two stacks meet at the page shown. 'back' holds the pages visited so far, the page shown on
// top and the home page at the bottom; 'forward' holds the pages stepped back over, the nearest
// one on top. A step back moves one page from 'back' to 'forward', a step forward moves one
// page the other way. Visiting a page pushes it on 'back' and empties 'forward'.

public final class BrowserHistoryStack {
	// Time Complexity: back, forward O(min(steps, n)), one page moved per step;
	//   visit O(1) amortized, every page it clears was put there by an earlier back step
	// Space Complexity: O(n) for the n pages visited

	private Deque<String> back;
	private Deque<String> forward;

	public BrowserHistoryStack(String homepage) {
		this.back = new ArrayDeque<>();
		this.forward = new ArrayDeque<>();
		visit(homepage);
	}

	public void visit(String url) {
		back.push(url);
		forward.clear(); // pages ahead are unreachable after a new visit
	}

	public String back(int steps) {
		while (steps > 0 && back.size() > 1) { // the home page is never moved off
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

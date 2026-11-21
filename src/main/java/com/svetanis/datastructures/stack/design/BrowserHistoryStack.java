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
//
// Siblings, same problem:
//   BrowserHistory  one list and an index; back and forward only move the index, O(1)

public final class BrowserHistoryStack {
	// Time Complexity: back, forward O(min(steps, n)), one page moved per step;
	//   visit O(1) amortized, every page it clears was put there by an earlier back step
	// Space Complexity: O(n) for the n pages visited

	private Deque<String> back; // BEHIND: pages up to the one shown, shown on top
	private Deque<String> forward; // AHEAD: pages stepped back over, nearest on top

	public BrowserHistoryStack(String homepage) {
		this.back = new ArrayDeque<>();
		this.forward = new ArrayDeque<>();
		visit(homepage); // HOME: the bottom of 'back'
	}

	public void visit(String url) {
		back.push(url); // SHOW: the new page goes on top
		forward.clear(); // DROP: pages ahead are unreachable after a new visit
	}

	public String back(int steps) {
		while (steps > 0 && back.size() > 1) { // STEP: the home page is never moved off
			forward.push(back.pop());
			steps--;
		}
		return back.peek(); // SHOWN: the top of 'back'
	}

	public String forward(int steps) {
		while (steps > 0 && !forward.isEmpty()) { // STEP: one page from ahead to behind
			back.push(forward.pop());
			steps--;
		}
		return back.peek(); // SHOWN: the top of 'back'
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

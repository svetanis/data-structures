package com.svetanis.datastructures.hashmap;

import java.util.ArrayDeque;
import java.util.Deque;

// 362. Design Hit Counter
//
// hit(t) records a hit at second t; getHits(t) returns how many hits happened in the last
// 300 seconds, so a hit at h counts at t if t - h < 300. Calls arrive in chronological
// order. On LeetCode: 1 <= timestamp <= 2 * 10^9, at most 300 calls.
//
// Hits arrive in time order and stop counting oldest first, so they live in a queue: hit
// joins the back, and getHits drops from the front while the front is 300 or more seconds
// old, then returns the size. The front is always the oldest, so DROP stops at the first
// hit that still counts. With timestamps out of order this breaks: a late hit sits behind
// newer ones, and a query back in time finds its hits already dropped.
//
// Siblings, same problem:
//   HitCounter          a map of second -> hits, scanned whole and never pruned
//   HitCounterBySecond  one { second, count } entry per second and a running total, for
//                       the follow-up where one second holds many hits

public final class HitCounterQueue {
	// Time complexity: hit O(1); getHits O(1) amortized, each hit is dropped at most once
	// Auxiliary Space: O(w), w = hits inside the last 300 seconds

	private Deque<Integer> dq = new ArrayDeque<>(); // HITS: hit times, oldest in front

	public void hit(int timestamp) {
		dq.offer(timestamp); // JOIN: at the back, the newest
	}

	public int getHits(int timestamp) {
		// DROP: from the front, every hit 300 or more seconds old
		while (!dq.isEmpty() && timestamp - dq.peekFirst() >= 300) {
			dq.pollFirst();
		}
		return dq.size(); // COUNT: everything left is inside the window
	}

	public static void main(String[] args) {
		HitCounterQueue hc = new HitCounterQueue();
		hc.hit(1);
		hc.hit(2);
		hc.hit(3);
		System.out.println(hc.getHits(4)); // 3
		hc.hit(300);
		System.out.println(hc.getHits(300)); // 4
		System.out.println(hc.getHits(301)); // 3
	}
}

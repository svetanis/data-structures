package com.svetanis.datastructures.hashmap;

import java.util.ArrayDeque;
import java.util.Deque;

// 362. Design Hit Counter -- the follow-up: what if one second holds a huge number of hits?
//
// hit(t) records a hit at second t; getHits(t) returns how many hits happened in the last
// 300 seconds, so a hit at h counts at t if t - h < 300. Calls arrive in chronological
// order. On LeetCode: 1 <= timestamp <= 2 * 10^9, at most 300 calls.
//
// One queue entry per SECOND, { second, count }, instead of one per hit, so the queue never
// holds more than 300 entries however many hits arrive. hit adds one to the back entry when
// it is already this second, else pushes { t, 1 }. Dropping an entry now removes a whole
// second's hits, so a running total is kept: each dropped entry's count is subtracted, and
// getHits returns the total, not the queue's size.
//
// Siblings, same problem:
//   HitCounter       a map of second -> hits, scanned whole and never pruned
//   HitCounterQueue  one queue entry per hit; the size is the answer

public final class HitCounterBySecond {
	// Time complexity: hit O(1); getHits O(1) amortized, each second is dropped at most once
	// Auxiliary Space: O(300), at most one entry per second of the window

	private Deque<int[]> dq = new ArrayDeque<>(); // SECONDS: { second, count }, oldest in front
	private int total; // TOTAL: hits still inside the window; int is enough for 300 calls

	public void hit(int timestamp) {
		if (!dq.isEmpty() && dq.peekLast()[0] == timestamp) {
			dq.peekLast()[1]++; // SAME SECOND: count + 1, in place
		} else {
			dq.offer(new int[] { timestamp, 1 }); // NEW SECOND: at the back
		}
		total += 1;
	}

	public int getHits(int timestamp) {
		// DROP: from the front, every second 300 or more old, and its hits from total
		while (!dq.isEmpty() && timestamp - dq.peekFirst()[0] >= 300) {
			int count = dq.pollFirst()[1];
			total -= count;
		}
		return total; // COUNT: the total, not the number of seconds
	}

	public static void main(String[] args) {
		HitCounterBySecond hc = new HitCounterBySecond();
		hc.hit(1);
		hc.hit(2);
		hc.hit(3);
		System.out.println(hc.getHits(4)); // 3
		hc.hit(300);
		System.out.println(hc.getHits(300)); // 4
		System.out.println(hc.getHits(301)); // 3

		// a million hits in one second take one entry
		HitCounterBySecond many = new HitCounterBySecond();
		for (int i = 0; i < 1_000_000; i++) {
			many.hit(5);
		}
		System.out.println(many.getHits(6)); // 1000000
	}
}

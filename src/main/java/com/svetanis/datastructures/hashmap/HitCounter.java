package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 362. Design Hit Counter
//
// hit(t) records a hit at second t; getHits(t) returns how many hits happened in the last
// 300 seconds, so a hit at h counts at t if t - h < 300. Calls arrive in chronological
// order. On LeetCode: 1 <= timestamp <= 2 * 10^9, at most 300 calls.
//
// One entry per second: a map from second to the hits in it. getHits walks every second the
// map holds and adds the ones still inside the window. Nothing is ever removed, so the
// answers are right but the map grows with the whole stream, and each getHits costs one
// step per second ever hit, not per second in the window.
//
// Siblings, same problem:
//   HitCounterQueue     a queue of hit times, dropped from the front as they age out;
//                       the size is the answer. Grows with the hits in the window
//   HitCounterBySecond  the follow-up for many hits per second: a queue of { second, count }
//                       and a running total, so it never holds more than 300 entries

public final class HitCounter {
	// Time complexity: hit O(1); getHits O(s), s = distinct seconds ever hit -- the map never
	// shrinks, so s is not bounded by 300
	// Auxiliary Space: O(s)

	private Map<Integer, Integer> map; // SECONDS: second -> hits in it, never pruned

	public HitCounter() {
		this.map = new HashMap<>();
	}

	public void hit(int timestamp) {
		map.put(timestamp, map.getOrDefault(timestamp, 0) + 1); // COUNT: one more in this second
	}

	public int getHits(int timestamp) {
		int hits = 0;
		for (int key : map.keySet()) { // WALK: every second ever hit
			if (timestamp - key < 300) { // WINDOW: < 300, a hit 300 seconds old is out
				hits += map.get(key);
			}
		}
		return hits;
	}

	public static void main(String[] args) {
		HitCounter hc = new HitCounter();
		hc.hit(1);
		hc.hit(2);
		hc.hit(3);
		System.out.println(hc.getHits(4)); // 3
		hc.hit(300);
		System.out.println(hc.getHits(300)); // 4
		System.out.println(hc.getHits(301)); // 3
	}
}
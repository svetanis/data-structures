package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// Set Counter (not a LeetCode problem)
//
// complete the class to support
// the following operations:
// 1. merge(x, y) merges the sets
// to which x and y belong
// 2. count(x) determines the
// size of the set that x belongs to
//
// The one idea: a set is a group in a union-find, and one element is one number. The root of a group
// is the one place that remembers how many numbers the group holds: count[root]. A number that was
// never merged is its own root and counts as 1, which is why count falls back to 1.
//
// Time: O(q * log n) -- q calls on n numbers; find compresses paths but merge does not pick the
// smaller tree, so a call is O(log n) amortised.
// Space: O(n) -- one map entry per number that has been merged.

public final class SetCounterSubmit {

	private Map<Integer, Integer> map;
	private Map<Integer, Integer> count;

	public SetCounterSubmit() {
		this.map = new HashMap<>();
		this.count = new HashMap<>();
	}

	public void merge(int x, int y) {
		if (find(x) != find(y)) { // SKIP when they are already one set
			int size = count(x) + count(y); // ADD: both head counts
			map.put(find(x), find(y)); // JOIN: x's root under y's root
			count.put(find(x), size); // RECORD: the count lives at the new root
		}
	}

	public int count(int x) {
		return count.getOrDefault(find(x), 1); // READ the root's count; unmerged means 1
	}

	private int find(int x) {
		int y = map.getOrDefault(x, x); // a missing key is a root
		if (y != x) {
			y = find(y); // WALK up to the root
			map.put(x, y); // COMPRESS: point straight at it
		}
		return y;
	}

	public static void main(String[] args) {
		SetCounterSubmit ss = new SetCounterSubmit();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.count(3)); // 3 -- the set {1, 2, 3}
		System.out.println(ss.count(4)); // 1 -- never merged
	}
}

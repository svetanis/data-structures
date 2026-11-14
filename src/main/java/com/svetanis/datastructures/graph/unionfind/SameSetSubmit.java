package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// Same Set (not a LeetCode problem)
//
// complete the class to support
// the following operations:
// 1. merge(x, y) merges the sets
// to which x and y belong
// 2. isSame(x, y) determines if
// x and y belong to the same set
//
// The one idea: a set is a group in a union-find, and one element is one number. map[x] is the
// number x points at; a number missing from the map points at itself and is a root. Two numbers are
// in the same set exactly when following the pointers from each ends at the same root.
//
// Time: O(q * log n) -- q calls on n numbers; find compresses paths but merge does not pick the
// smaller tree, so a call is O(log n) amortised.
// Space: O(n) -- one map entry per number that has been merged.
//
// Siblings: SameSet is the same on the shared UnionFind class.

public final class SameSetSubmit {

	private Map<Integer, Integer> map;

	public SameSetSubmit() {
		this.map = new HashMap<>();
	}

	public void merge(int x, int y) {
		map.put(find(x), find(y)); // JOIN: x's root now points at y's root
	}

	public boolean isSame(int x, int y) {
		return find(x) == find(y); // RETURN: same root, same set
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
		SameSetSubmit ss = new SameSetSubmit();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.isSame(1, 3)); // true -- 1, 2, 3 are one set
		System.out.println(ss.isSame(2, 4)); // false -- 4 was never merged
	}
}

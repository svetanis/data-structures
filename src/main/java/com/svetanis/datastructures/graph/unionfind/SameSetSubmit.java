package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// complete the class to support 
// the following operations:
// 1. merge(x, y) merges the sets 
// to which x and y belong
// 2. isSame(x, y) determines if
// x and y belong to the same set

public final class SameSetSubmit {
	// Time Complexity: O(q * log n)

	private Map<Integer, Integer> map;

	public SameSetSubmit() {
		this.map = new HashMap<>();
	}

	public void merge(int x, int y) {
		map.put(find(x), find(y));
	}

	public boolean isSame(int x, int y) {
		return find(x) == find(y);
	}

	private int find(int x) {
		int y = map.getOrDefault(x, x);
		if (y != x) {
			y = find(y);
			map.put(x, y);
		}
		return y;
	}

	public static void main(String[] args) {
		SameSetSubmit ss = new SameSetSubmit();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.isSame(1, 3)); // true
		System.out.println(ss.isSame(2, 4)); // false
	}
}

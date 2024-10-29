package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// complete the class to support 
// the following operations:
// 1. merge(x, y) merges the sets 
// to which x and y belong
// 2. count(x) determines the
// size of the set that x belongs to

public final class SetCounterSubmit {
	// Time Complexity: O(n * log n)

	private Map<Integer, Integer> map;
	private Map<Integer, Integer> count;

	public SetCounterSubmit() {
		this.map = new HashMap<>();
		this.count = new HashMap<>();
	}

	public void merge(int x, int y) {
		if (find(x) != find(y)) {
			int size = count(x) + count(y);
			map.put(find(x), find(y));
			count.put(find(x), size);
		}
	}

	public int count(int x) {
		return count.getOrDefault(find(x), 1);
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
		SetCounterSubmit ss = new SetCounterSubmit();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.count(3)); // 3
		System.out.println(ss.count(4)); // 1
	}
}

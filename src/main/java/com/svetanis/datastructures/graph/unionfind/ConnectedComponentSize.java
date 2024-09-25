package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// complete the class to support 
// the following operations:
// 1. merge(x, y) merges the sets 
// to which x and y belong
// 2. count(x) determines the
// size of the set that x belongs to

public final class ConnectedComponentSize {
	// Time Complexity: O(n * log n)

	private UnionFind<Integer> dsu;
	private Map<Integer, Integer> map;

	public ConnectedComponentSize() {
		this.map = new HashMap<>();
		this.dsu = new UnionFind<>();
	}

	public void merge(int x, int y) {
		if (dsu.find(x) != dsu.find(y)) {
			int size = count(x) + count(y);
			dsu.union(x, y);
			map.put(dsu.find(x), size);
		}
	}

	public int count(int x) {
		return map.getOrDefault(dsu.find(x), 1);
	}

	public static void main(String[] args) {
		ConnectedComponentSize ss = new ConnectedComponentSize();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.count(3)); // 3
		System.out.println(ss.count(4)); // 1
	}
}

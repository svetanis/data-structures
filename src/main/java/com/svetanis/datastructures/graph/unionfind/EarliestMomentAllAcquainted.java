package com.svetanis.datastructures.graph.unionfind;

import static com.svetanis.java.base.collect.Lists.sort;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// 1101. The Earliest Moment When Everyone Become Friends

public final class EarliestMomentAllAcquainted {
	// Time Complexity: O(m log n), m - number of edges (friendships)
	// Space Complexity: O(n)

	private UnionFind<Integer> dsu;

	public EarliestMomentAllAcquainted() {
		this.dsu = new UnionFind<>();
	}

	public int earliestAcquainted(int n, List<List<Integer>> logs) {
		List<List<Integer>> sorted = sort(logs, (a, b) -> a.get(0) - b.get(0));
		int components = n;
		for (List<Integer> log : sorted) {
			int timestamp = log.get(0);
			int x = log.get(1);
			int y = log.get(2);
			if (dsu.find(x) != dsu.find(y)) {
				dsu.union(x, y);
				components--;
			}
			if (components == 1) {
				return timestamp;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		EarliestMomentAllAcquainted ema = new EarliestMomentAllAcquainted();
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(0, 2, 0));
		list.add(asList(1, 0, 1));
		list.add(asList(3, 0, 3));
		list.add(asList(4, 1, 2));
		list.add(asList(7, 3, 1));
		System.out.println(ema.earliestAcquainted(4, list)); // 3
	}

	private static void test2() {
		EarliestMomentAllAcquainted ema = new EarliestMomentAllAcquainted();
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(20190101, 0, 1));
		list.add(asList(20190104, 3, 4));
		list.add(asList(20190107, 2, 3));
		list.add(asList(20190211, 1, 5));
		list.add(asList(20190224, 2, 4));
		list.add(asList(20190301, 0, 3));
		list.add(asList(20190312, 1, 2));
		list.add(asList(20190322, 4, 5));
		System.out.println(ema.earliestAcquainted(6, list)); // 20190301
	}
}

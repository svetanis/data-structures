package com.svetanis.datastructures.graph.unionfind;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.List;

// 1101. The Earliest Moment When Everyone Become Friends
//
// Input: n people numbered 0..n-1 and logs, each [timestamp, x, y]: at that time x and y become
// friends. Friendship is transitive (a friend of a friend counts). On LeetCode: 2 <= n <= 100,
// 1 <= logs.length <= 10^4, 0 <= timestamp <= 10^9, all timestamps distinct, x != y.
// Returns the earliest timestamp at which everyone is in one group of friends, or -1 if that
// never happens.
//
// The one idea: go through the logs in time order. Each log joins the groups of x and y. Start
// with n groups of one person each; a log that joins two DIFFERENT groups lowers the count by one,
// and a log between people already in one group changes nothing. The first time the count reaches
// 1, everyone is connected, and that log's timestamp is the answer. Union-find keeps the groups.
//
// Time: O(m log m) -- sorting the m logs; the union-find steps add close to O(m) after it.
// Space: O(n + m) -- the sorted copy of the logs and the union-find map.
//
// Siblings: EarliestMomentAllAcquaintedSubmit is the same method on an int[][] with array-based
// union by rank, the way it would be submitted.

public final class EarliestMomentAllAcquainted {

	private UnionFind<Integer> dsu;

	public int earliestAcquainted(int n, List<List<Integer>> logs) {
		// per call, not in the constructor -- unions from an earlier call would
		// otherwise still be joined for this one
		this.dsu = new UnionFind<>();
		List<List<Integer>> sorted = new ArrayList<>(logs);
		sorted.sort(comparingInt(a -> a.get(0))); // SORT: by timestamp, earliest first
		int components = n; // COUNT: groups still apart
		for (List<Integer> log : sorted) {
			int timestamp = log.get(0);
			int x = log.get(1);
			int y = log.get(2);
			if (!dsu.isSame(x, y)) { // TAKE: two different groups become one
				dsu.union(x, y);
				components--;
			}
			if (components == 1) {
				return timestamp; // RETURN: everyone is connected as of this log
			}
		}
		return -1; // RETURN: the logs ran out with groups still apart
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
		// the same instance answers the same question again
		System.out.println(ema.earliestAcquainted(6, list)); // 20190301
	}
}

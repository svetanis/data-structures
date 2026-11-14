package com.svetanis.datastructures.graph.unionfind;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;

// 1101. The Earliest Moment When Everyone Become Friends
//
// Input: logs, each [timestamp, x, y]: at that time people x and y become friends; and n, the
// number of people, numbered 0..n-1. Friendship is transitive (a friend of a friend counts). On
// LeetCode: 2 <= n <= 100, 1 <= logs.length <= 10^4, 0 <= timestamp <= 10^9, all timestamps
// distinct, x != y. The logs array is sorted in place.
// Returns the earliest timestamp at which everyone is in one group of friends, or -1 if that
// never happens.
//
// The one idea: go through the logs in time order. Each log joins the groups of x and y. Start
// with n groups of one person each; a log that joins two DIFFERENT groups lowers the count by one,
// and a log between people already in one group changes nothing. The first time the count reaches
// 1, everyone is connected, and that log's timestamp is the answer. Union-find keeps the groups.
//
// Time: O(m log m) -- sorting the m logs; the union-find steps add close to O(m) after it.
// Space: O(n) -- the parent and rank arrays.
//
// Siblings: EarliestMomentAllAcquainted is the same method on lists with the generic UnionFind.

public final class EarliestMomentAllAcquaintedSubmit {

	private int[] rank;
	private int[] parent;

	public int earliestAcquainted(int[][] logs, int n) {
		Arrays.sort(logs, comparingInt(a -> a[0])); // SORT: by timestamp, earliest first
		this.rank = new int[n];
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.rank[i] = 0;
			this.parent[i] = i;
		}
		int components = n; // COUNT: groups still apart
		for (int[] log : logs) {
			int timestamp = log[0];
			int x = log[1];
			int y = log[2];
			if (union(x, y)) { // TAKE: two different groups become one
				components -= 1;
			}
			if (components == 1) {
				return timestamp; // RETURN: everyone is connected as of this log
			}
		}
		return -1; // RETURN: the logs ran out with groups still apart
	}

	public int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]); // COMPRESS: point straight at the root
	}

	public boolean union(int x, int y) {
		int xp = find(x);
		int yp = find(y);
		if (xp == yp) {
			return false;
		}
		// JOIN: the lower tree goes under the higher one
		if (rank[xp] > rank[yp]) {
			parent[yp] = xp;
		} else if (rank[yp] > rank[xp]) {
			parent[xp] = yp;
		} else {
			parent[yp] = xp;
			rank[xp] += 1;
		}
		return true;
	}

	public static void main(String[] args) {
		EarliestMomentAllAcquaintedSubmit ema = new EarliestMomentAllAcquaintedSubmit();
		int[][] logs1 = { { 20190101, 0, 1 }, { 20190104, 3, 4 }, { 20190107, 2, 3 }, { 20190211, 1, 5 },
				{ 20190224, 2, 4 }, { 20190301, 0, 3 }, { 20190312, 1, 2 }, { 20190322, 4, 5 } };
		System.out.println(ema.earliestAcquainted(logs1, 6)); // 20190301
		int[][] logs2 = { { 0, 2, 0 }, { 1, 0, 1 }, { 3, 0, 3 }, { 4, 1, 2 }, { 7, 3, 1 } };
		System.out.println(ema.earliestAcquainted(logs2, 4)); // 3
		int[][] logs3 = { { 1, 0, 1 } };
		System.out.println(ema.earliestAcquainted(logs3, 3)); // -1
	}
}

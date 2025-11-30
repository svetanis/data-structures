package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 1101. The Earliest Moment When Everyone Become Friends

public final class EarliestMomentAllAcquaintedSubmit {
	// Time Complexity: O(m log n), m - number of edges (friendships)
	// Space Complexity: O(n)

	private int[] rank;
	private int[] parent;

	public int earliestAcquainted(int[][] logs, int n) {
		Arrays.sort(logs, (a, b) -> a[0] - b[0]);
		this.rank = new int[n];
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.rank[i] = 0;
			this.parent[i] = i;
		}
		int components = n;
		for (int[] log : logs) {
			int timestamp = log[0];
			int x = log[1];
			int y = log[2];
			if (union(x, y)) {
				components -= 1;
			}
			if (components == 1) {
				return timestamp;
			}
		}
		return -1;
	}

	public int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]);
	}

	public boolean union(int x, int y) {
		int xp = find(x);
		int yp = find(y);
		if (xp == yp) {
			return false;
		}
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

	public static void main(String[] args) {}
}

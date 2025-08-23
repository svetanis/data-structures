package com.svetanis.datastructures.graph.unionfind;

import com.svetanis.java.base.utils.Print;

// 685. Redundant Connection II

public final class RedundantConnectionII {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private UnionFind uf;
	private int[] parents;

	public int[] redundantConnections(int[][] edges) {
		int n = edges.length;
		init(n + 1);
		int cycle = -1;
		int conflict = -1;
		this.uf = new UnionFind(n + 1);
		for (int i = 0; i < n; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			if (parents[to] != to) {
				conflict = i;
			} else {
				parents[to] = from;
				if (!uf.union(from, to)) {
					cycle = i;
				}
			}
		}
		if (conflict == -1) {
			return edges[cycle];
		}
		int to = edges[conflict][1];
		if (cycle != -1) {
			return new int[] { parents[to], to };
		}
		return edges[conflict];
	}

	private void init(int n) {
		this.parents = new int[n + 1];
		for (int i = 0; i <= n; i++) {
			this.parents[i] = i;
		}
	}

	public static void main(String[] args) {
		RedundantConnectionII rc = new RedundantConnectionII();
		int[][] e1 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		Print.print(rc.redundantConnections(e1)); // [2,3]

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 1 }, { 1, 5 } };
		Print.print(rc.redundantConnections(e2)); // [4,1]
	}

	private static class UnionFind {

		private int count;
		private int[] parent;

		public UnionFind(int size) {
			this.count = size;
			this.parent = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
			}
		}

		public int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}

		public boolean union(int x, int y) {
			int parentX = find(x);
			int parentY = find(y);
			if (parentX == parentY) {
				return false;
			}
			parent[parentX] = parentY;
			count--;
			return true;
		}
	}
}

package com.svetanis.datastructures.graph.unionfind;

import com.svetanis.java.base.utils.Print;

// 684. Redundant Connection

public final class RedundantConnection {
	// Time Complexity: O(E)
	// Space Complexity: O(1)

	private int[] parent;

	public int[] redundantConnections(int[][] edges) {
		init(edges.length);
		for (int[] edge : edges) {
			int px = find(edge[0]);
			int py = find(edge[1]);
			if (px == py) {
				return edge;
			}
			parent[px] = py;
		}
		return null;
	}

	private void init(int n) {
		this.parent = new int[1010];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	public static void main(String[] args) {
		RedundantConnection rc = new RedundantConnection();
		int[][] e1 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		Print.print(rc.redundantConnections(e1)); // [2,3]

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } };
		Print.print(rc.redundantConnections(e2)); // [1,4]
	}
}

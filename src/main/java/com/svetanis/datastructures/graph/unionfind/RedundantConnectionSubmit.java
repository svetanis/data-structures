package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 684. Redundant Connection
//
// Input: edges -- n edges [a, b] over nodes labelled 1..n; the graph was a tree and one extra edge
// was added. Returns the edge to remove so a tree is left; if several answers exist, the one that
// occurs last in edges. On LeetCode: n == edges.length, 3 <= n <= 1000, 1 <= a < b <= n, no edge
// repeated, the graph is connected.
//
// The one idea: one element is one node. Walk the edges in order, joining the two ends of each. An
// edge whose two ends are already in one group closes a loop -- that is the extra edge.
//
// LC 684 gives n nodes labelled 1..n and exactly n edges, so edges.length is the node count and
// node n needs a seat: the parent array has n + 2 slots.
//
// Time: O(n log n) -- one union per edge; find only compresses paths, which is O(log n) amortised.
// Space: O(n) -- the parent array.
//
// Siblings: RedundantConnection is the same algorithm on the shared DisjointSet.

public final class RedundantConnectionSubmit {

	private int[] parent;

	public int[] redundantConnections(int[][] edges) {
		init(edges.length);
		for (int[] edge : edges) {
			int px = find(edge[0]);
			int py = find(edge[1]);
			if (px == py) {
				return edge; // RETURN: this edge closes a loop
			}
			parent[px] = py; // JOIN the two groups
		}
		return null;
	}

	private void init(int n) {
		this.parent = new int[n + 2];
		for (int i = 0; i <= n; i++) {
			this.parent[i] = i;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	public static void main(String[] args) {
		RedundantConnectionSubmit rc = new RedundantConnectionSubmit();
		int[][] e1 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(Arrays.toString(rc.redundantConnections(e1))); // [2, 3]

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } };
		System.out.println(Arrays.toString(rc.redundantConnections(e2))); // [1, 4]
	}
}

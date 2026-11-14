package com.svetanis.datastructures.graph.unionfind;

// 261. Graph Valid Tree
//
// Input: n -- nodes labelled 0..n-1; edges -- pairs [a, b], each a wire between two nodes. Returns
// true if the edges make a tree: connected, with no loop. On LeetCode: 1 <= n <= 2000,
// 0 <= edges.length <= 5000, a != b, no edge repeated.
//
// The one idea: one element is one node. Every edge must join two groups that were apart -- an edge
// inside a group closes a loop. `n` is reused as the group count: it starts at the number of nodes
// and drops by one for every edge, and a tree leaves exactly one group.
//
// Time: O(n + e * log n) -- n slots to set up, one union per edge; find only compresses paths, which
// is O(log n) amortised.
// Space: O(n) -- the parent array.
//
// Siblings: ValidTree is the same algorithm on the shared DisjointSet.

public final class ValidTreeSubmit {

	private int[] parent;

	public boolean validTree(int n, int[][] edges) {
		this.parent = init(n);
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			// if both nodes have the same root
			// then there is a cycle, not a tree
			int pFrom = find(from);
			int pTo = find(to);
			if (pFrom == pTo) {
				return false; // a loop
			}
			parent[pFrom] = pTo; // JOIN the two groups
			n--; // COUNT: one group fewer
		}
		return n == 1; // RETURN: loop-free, and one piece
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y); // WALK up to the root
			parent[x] = y; // COMPRESS: point straight at it
		}
		return y;
	}

	private int[] init(int n) {
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		return parent;
	}

	public static void main(String[] args) {
		ValidTreeSubmit vt = new ValidTreeSubmit();
		int[][] g = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, g)); // true -- a chain of four nodes
	}
}

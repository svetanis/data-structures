package com.svetanis.datastructures.graph.unionfind;

// 323. Number of Connected Components in an Undirected Graph
//
// Input: n -- the number of nodes, labelled 0..n-1; edges -- pairs [a, b], each a wire between two
// nodes. Returns the number of connected components: groups of nodes reachable from one another.
// On LeetCode: 1 <= n <= 2000, 1 <= edges.length <= 5000, 0 <= a <= b < n, a != b, no edge repeated.
//
// The one idea: one element is one node. Every node starts as its own component, and each edge that
// joins two DIFFERENT components destroys one. So the count needs no pass over the parent array and
// no field on the structure -- it is what union's boolean already tells you: true means the two
// ends were apart, so there is one component fewer.
//
// Time: O(n + e * alpha) -- n slots to set up, then one union per edge, each near-constant.
// Space: O(n) -- the DisjointSet.
//
// Siblings: NumberOfConnectedComponents323Dfs solves it by walking instead.
// Paste DisjointSet above it for a submission -- one editor box takes both classes.

public final class NumberOfConnectedComponents323 {

	public int countComponents(int n, int[][] edges) {
		DisjointSet ds = new DisjointSet(n);
		int components = n; // BASE: every node alone
		for (int[] edge : edges) {
			// true means they were apart; the two components just became one
			if (ds.union(edge[0], edge[1])) { // JOIN
				components -= 1; // COUNT: one component fewer
			}
		}
		return components; // RETURN
	}

	public static void main(String[] args) {
		NumberOfConnectedComponents323 cc = new NumberOfConnectedComponents323();
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
		System.out.println(cc.countComponents(5, edges)); // 2 -- {0, 1, 2} and {3, 4}

		// an edge inside a component changes nothing: union returns false
		int[][] cycle = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		System.out.println(cc.countComponents(3, cycle)); // 1 -- the triangle is one piece

		System.out.println(cc.countComponents(4, new int[0][2])); // 4 -- no edges
	}
}

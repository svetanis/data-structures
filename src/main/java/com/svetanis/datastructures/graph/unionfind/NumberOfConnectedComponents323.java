package com.svetanis.datastructures.graph.unionfind;

// 323. Number of Connected Components in an Undirected Graph
// Every node starts as its own component, and each edge that joins two DIFFERENT
// components destroys one. So the count needs no pass over the parent array and no
// field on the structure -- it is what union's boolean already tells you.
// NumberOfConnectedComponents323Dfs solves it by walking instead.
// Paste DisjointSet above it for a submission -- one editor box takes both classes.

public final class NumberOfConnectedComponents323 {
	// Time Complexity: O(n + e)
	// Space Complexity: O(n)

	public int countComponents(int n, int[][] edges) {
		DisjointSet ds = new DisjointSet(n);
		int components = n;
		for (int[] edge : edges) {
			// true means they were apart; the two components just became one
			if (ds.union(edge[0], edge[1])) {
				components -= 1;
			}
		}
		return components;
	}

	public static void main(String[] args) {
		NumberOfConnectedComponents323 cc = new NumberOfConnectedComponents323();
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
		System.out.println(cc.countComponents(5, edges)); // 2

		// an edge inside a component changes nothing: union returns false
		int[][] cycle = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		System.out.println(cc.countComponents(3, cycle)); // 1

		System.out.println(cc.countComponents(4, new int[0][2])); // 4 -- no edges
	}
}

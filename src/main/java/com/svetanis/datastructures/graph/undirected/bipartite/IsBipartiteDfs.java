package com.svetanis.datastructures.graph.undirected.bipartite;

// 785. Is Graph Bipartite?

public final class IsBipartiteDfs {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V)

	public static boolean isBipartite(int[][] g) {
		int n = g.length;
		int[] colors = new int[n];
		for (int node = 0; node < n; node++) {
			if (colors[node] == 0 && !dfs(g, colors, node, 1)) {
				return false;
			}
		}
		return true;
	}

	private static boolean dfs(int[][] g, int[] colors, int node, int color) {
		colors[node] = color;
		for (int neighbor : g[node]) {
			if (colors[neighbor] == 0) {
				if (!dfs(g, colors, neighbor, 3 - color)) {
					return false;
				}
			} else if (colors[neighbor] == color) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2, 3 }, { 0, 2 }, { 0, 1, 3 }, { 0, 2 } };
		System.out.println(isBipartite(g1)); // false
		int[][] g2 = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };
		System.out.println(isBipartite(g2)); // true
	}
}

package com.svetanis.datastructures.graph.undirected.bipartite;

// 785. Is Graph Bipartite?

public final class IsBipartite {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V)

	public static boolean isBipartite(int[][] g) {
		int n = g.length;
		boolean[] visited = new boolean[n + 1];
		int[] colors = new int[n + 1];
		for (int node = 0; node < n; node++) {
			visited[node] = true;
			if (colors[node] == 0 && !dfs(g, node, visited, colors)) {
				return false;
			}
		}
		return true;
	}

	private static boolean dfs(int[][] g, int src, boolean[] visited, int[] colors) {
		for (int neighbor : g[src]) {
			if (!visited[neighbor]) {
				visited[neighbor] = true;
				colors[neighbor] = 1 - colors[src];
				if (!dfs(g, neighbor, visited, colors)) {
					return false;
				}
			} else if (colors[neighbor] == colors[src]) {
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

package com.svetanis.datastructures.graph.dfs;

// 547. Number of Provinces

public final class NumberOfProvinces {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)

	public static int countProvinces(int[][] grid) {
		int scc = 0;
		int n = grid.length;
		boolean[] visited = new boolean[n];
		for (int src = 0; src < n; src++) {
			if (!visited[src]) {
				dfs(grid, src, visited);
				scc++;
			}
		}
		return scc;
	}

	private static void dfs(int[][] g, int src, boolean[] visited) {
		visited[src] = true;
		for (int dst = 0; dst < g.length; dst++) {
			if (!visited[dst] && g[src][dst] == 1) {
				dfs(g, dst, visited);
			}
		}
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
		System.out.println(countProvinces(g1)); // 2
		int[][] g2 = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
		System.out.println(countProvinces(g2)); // 3
	}
}

package com.svetanis.datastructures.graph.dfs;

// 547. Number of Provinces
//
// Input: grid -- an n by n matrix of cities; grid[i][j] is 1 if city i and city j are directly
// connected and 0 if not. A province is a group of cities connected to each other directly or
// through other cities, and not connected to any city outside the group. Returns the number of
// provinces. On LeetCode: 1 <= n <= 200, grid[i][j] is 0 or 1, grid[i][i] == 1, and
// grid[i][j] == grid[j][i].
//
// The one idea: count how many times a fresh walk has to start. One entry of visited says whether
// that city has already been reached by a walk. Go through the cities in order; a city not yet
// reached must belong to a province no earlier walk has touched, so count one province and walk
// from that city to every city reachable from it, marking each as reached. Every city of that
// province is then marked, and none of them starts another count. The matrix holds no list of a
// city's neighbours, so finding them means reading the whole row of that city.
//
// Time: O(n^2) -- every city is walked once, and each walk reads its whole row of n cells.
// Space: O(n) -- the marks, and the walk is at most n calls deep.
//
// Siblings: ZombieClusters in this package asks the same question of rows of '0' and '1'
// characters.

public final class NumberOfProvinces {

	public static int countProvinces(int[][] grid) {
		int scc = 0;
		int n = grid.length;
		boolean[] visited = new boolean[n];
		for (int src = 0; src < n; src++) {
			if (!visited[src]) { // a city no walk has reached: a new province
				dfs(grid, src, visited); // WALK: mark the whole province
				scc++; // COUNT
			}
		}
		return scc; // RETURN
	}

	private static void dfs(int[][] g, int src, boolean[] visited) {
		visited[src] = true; // MARK
		for (int dst = 0; dst < g.length; dst++) { // SCAN: the whole row of src
			if (!visited[dst] && g[src][dst] == 1) { // SKIP: reached already, or not connected
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

package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.List;

// 802. Find Eventual Safe States

public final class EventualSafeStates {
	// Time Complexity: O(N + E)
	// Space Complexity: O(N)

	public static List<Integer> countProvinces(int[][] grid) {
		int n = grid.length;
		int[] colors = new int[n];
		List<Integer> list = new ArrayList<>();
		for (int src = 0; src < n; src++) {
			if (dfs(grid, src, colors)) {
				list.add(src);
			}
		}
		return list;
	}

	private static boolean dfs(int[][] g, int src, int[] colors) {
		// node already visited
		if (colors[src] > 0) {
			return colors[src] == 2;
		}
		// mark node as visited (color 1)
		colors[src] = 1;
		for (int neighbor : g[src]) {
			if (!dfs(g, neighbor, colors)) {
				return false;
			}
		}
		// all connected nodes are safe
		// mark node as safe (color 2)
		colors[src] = 2;
		return true;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 2, 3 }, { 5 }, { 0 }, { 5 }, {}, {} };
		System.out.println(countProvinces(g1)); // 2,4,5,6
		int[][] g2 = { { 1, 2, 3, 4 }, { 1, 2 }, { 3, 4 }, { 0, 4 }, {} };
		System.out.println(countProvinces(g2)); // 4
	}
}

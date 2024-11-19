package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

// 2101. Detonate the Maximum Bombs

public final class MaximumDetonation {
	// Time Complexity: O(n^3)
	// Space Complexity: O(n^2)

	public static int maxDetonation(int[][] bombs) {
		int n = bombs.length;
		boolean[][] graph = graph(bombs);
		int max = 0;
		for (int bomb = 0; bomb < n; bomb++) {
			Set<Integer> set = new HashSet<>();
			Queue<Integer> queue = new ArrayDeque<>();
			queue.offer(bomb);
			set.add(bomb);
			int count = 0;
			while (!queue.isEmpty()) {
				int curr = queue.poll();
				count++;
				for (int next = 0; next < n; next++) {
					if (graph[curr][next] && !set.contains(next)) {
						set.add(next);
						queue.offer(next);
					}
				}
			}
			max = Math.max(max, count);
		}
		return max;
	}

	private static boolean[][] graph(int[][] bombs) {
		int n = bombs.length;
		boolean[][] graph = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				graph[i][j] = reachable(bombs, i, j);
			}
		}
		return graph;
	}

	private static boolean reachable(int[][] bombs, int i, int j) {
		if (i == j) {
			return false;
		}
		long dx = bombs[i][0] - bombs[j][0];
		long dy = bombs[i][1] - bombs[j][1];
		long rs = (long) bombs[i][2] * bombs[i][2];
		return rs >= dx * dx + dy * dy;
	}

	public static void main(String[] args) {
		int[][] grid = { { 2, 1, 3 }, { 6, 1, 4 } };
		System.out.println(maxDetonation(grid)); // 2

		int[][] grid1 = { { 1, 1, 5 }, { 10, 10, 5 } };
		System.out.println(maxDetonation(grid1)); // 1

		int[][] grid2 = { { 1, 2, 3 }, { 2, 3, 1 }, { 3, 4, 2 }, { 4, 5, 3 }, { 5, 6, 4 } };
		System.out.println(maxDetonation(grid2)); // 5
	}
}

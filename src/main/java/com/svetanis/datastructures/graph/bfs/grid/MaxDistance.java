package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1162. As Far from Land as Possible

public final class MaxDistance {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n^2)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int maxDistance(int[][] grid) {
		int n = grid.length;
		Deque<int[]> dq = init(grid);
		int max = -1;
		if (dq.isEmpty() || dq.size() == n * n) {
			return max;
		}
		while (!dq.isEmpty()) {
			int size = dq.size();
			for (int i = 0; i < size; i++) {
				int[] point = dq.poll();
				for (int k = 0; k < 4; k++) {
					int x = point[0] + dx[k];
					int y = point[1] + dy[k];
					if (safe(grid, x, y)) {
						grid[x][y] = 1;
						dq.offer(new int[] { x, y });
					}
				}
			}
			max++;
		}
		return max;
	}

	private static boolean safe(int[][] grid, int x, int y) {
		int n = grid.length;
		boolean row = x >= 0 && x < n;
		boolean col = y >= 0 && y < n;
		return row && col && grid[x][y] == 0;
	}

	private static Deque<int[]> init(int[][] grid) {
		int n = grid.length;
		Deque<int[]> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					dq.offer(new int[] { i, j });
				}
			}
		}
		return dq;
	}

	public static void main(String[] args) {
		int[][] grid1 = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } };
		System.out.println(maxDistance(grid1)); // 2

		int[][] grid2 = { { 1, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
		System.out.println(maxDistance(grid2)); // 4
	}
}

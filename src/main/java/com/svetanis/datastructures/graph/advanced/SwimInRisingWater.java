package com.svetanis.datastructures.graph.advanced;

import java.util.ArrayDeque;
import java.util.Deque;

// 778. Swim in Rising Water
// BFS + Binary search

public final class SwimInRisingWater {
	// Time Complexity: O(n^2 * log n);
	// Space Complexity: O(n^2)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int swimInWater(int[][] grid) {
		int left = grid[0][0];
		int right = highest(grid);
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (canSwim(grid, mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}

	private static int highest(int[][] grid) {
		// the answer can never exceed the deepest cell on the board. n * n - 1
		// is that same bound spelled from LC 778's promise that the grid holds
		// each of 0 .. n*n-1 exactly once, and it is too small the moment a
		// value repeats -- {{3,5},{5,5}} needs 5 and n * n - 1 caps it at 3
		int max = grid[0][0];
		for (int[] row : grid) {
			for (int cell : row) {
				max = Math.max(max, cell);
			}
		}
		return max;
	}

	private static boolean canSwim(int[][] grid, int target) {
		if (grid[0][0] > target) {
			return false;
		}
		int n = grid.length;
		boolean[][] visited = new boolean[n][n];
		visited[0][0] = true;
		Deque<int[]> dq = new ArrayDeque<>();
		dq.add(new int[] { 0, 0 });
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int row = node[0];
			int col = node[1];
			if (row == n - 1 && col == n - 1) {
				return true;
			}
			for (int i = 0; i < 4; i++) {
				int x = row + dx[i];
				int y = col + dy[i];
				if (!valid(n, x, y)) {
					continue;
				}
				if (visited[x][y]) {
					continue;
				}
				if (grid[x][y] > target) {
					continue;
				}
				visited[x][y] = true;
				dq.add(new int[] { x, y });
			}
		}
		return false;
	}

	private static boolean valid(int n, int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 2 }, { 1, 3 } };
		System.out.println(swimInWater(g1)); // 3

		int[][] g2 = { { 0, 1, 2, 3, 4 }, { 24, 23, 22, 21, 5 }, { 12, 13, 14, 15, 16 }, { 11, 17, 18, 19, 20 },
				{ 10, 9, 8, 7, 6 } };
		System.out.println(swimInWater(g2)); // 16

		// two cells hold 5 and the deepest is 5, but n * n - 1 is 3, so a
		// search that stops at 3 answers 3 and calls a board unswimmable
		// that is not. LC 778 promises every value 0 .. n*n-1 appears once,
		// which is the only reason the old bound was ever right
		int[][] g3 = { { 3, 5 }, { 5, 5 } };
		System.out.println(swimInWater(g3)); // 5
	}
}

package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1162. As Far from Land as Possible
//
// Input: an n x n grid of 0 (water) and 1 (land). On LeetCode: 1 <= n <= 100.
// Returns the largest, over all water cells, of the distance to the nearest land cell, where the
// distance between two cells is |row difference| + |column difference|. Returns -1 when the grid
// has no water or no land. The grid is changed: reached water cells are overwritten with 1.
//
// The one idea: one queue entry is one cell that has just been reached. Instead of asking, for
// every water cell, which land is nearest, start from ALL land cells at once. Each pass over the
// queue spreads the reached area one step outward, so the cells added in pass r are exactly the
// water cells whose nearest land is r steps away. The number of passes that still reach new water
// is therefore the largest nearest-land distance.
//
// Time: O(n^2) -- each cell is queued at most once, with 4 neighbours looked at.
// Space: O(n^2) -- the queue can hold a whole ring of cells, and in the worst case all of them.
//
// Siblings: none -- this is the only file for this problem.

public final class MaxDistance {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int maxDistance(int[][] grid) {
		int n = grid.length;
		Deque<int[]> dq = init(grid); // SEED: every land cell, all at distance 0
		int max = -1;
		if (dq.isEmpty() || dq.size() == n * n) {
			return max; // RETURN: all water or all land, no answer
		}
		while (!dq.isEmpty()) {
			int size = dq.size(); // LEVEL: only the cells reached by the previous pass
			for (int i = 0; i < size; i++) {
				int[] point = dq.poll(); // TAKE
				for (int k = 0; k < 4; k++) {
					int x = point[0] + dx[k];
					int y = point[1] + dy[k];
					if (safe(grid, x, y)) {
						grid[x][y] = 1; // RECORD: seen -- first arrival is the nearest land
						dq.offer(new int[] { x, y });
					}
				}
			}
			max++; // COUNT: starts at -1, so the final pass (which reaches nothing new) is not counted
		}
		return max; // RETURN: the number of passes that reached new water
	}

	private static boolean safe(int[][] grid, int x, int y) {
		int n = grid.length;
		boolean row = x >= 0 && x < n;
		boolean col = y >= 0 && y < n;
		return row && col && grid[x][y] == 0; // SKIP: off the grid or already land / seen
	}

	private static Deque<int[]> init(int[][] grid) {
		int n = grid.length;
		Deque<int[]> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					dq.offer(new int[] { i, j }); // SEED
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

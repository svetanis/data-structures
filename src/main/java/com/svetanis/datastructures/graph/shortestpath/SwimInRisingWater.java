package com.svetanis.datastructures.graph.shortestpath;

import java.util.ArrayDeque;
import java.util.Deque;

// 778. Swim in Rising Water
//
// Input: grid -- an n x n grid; grid[i][j] is the elevation of square (i, j). At time t the water
// is t deep everywhere, and you can swim between two neighbouring squares (up, down, left or
// right) only if both have elevation at most t. You start at the top-left square. Returns the
// least time at which you can reach the bottom-right square. On LeetCode: 1 <= n <= 50,
// 0 <= grid[i][j] < n * n, every value is different.
//
// The one idea: ask a yes/no question instead of searching for the best time. "Can the bottom-right
// square be reached when the water is t deep?" is a plain flood fill that refuses any square higher
// than t. It is monotone: if t works, every larger t works. So the answer is the smallest t that
// works, found by binary search between the start's own elevation and the highest square on the
// board. The highest square is read from the grid itself rather than taken to be n * n - 1,
// because LC's promise of distinct values is the only reason those two agree: {{3,5},{5,5}} needs
// 5, and a bound of n * n - 1 = 3 would stop the search at 3.
//
// Time: O(n^2 * log(n^2)) = O(n^2 * log n) -- about log(n^2) rounds, each one flood fill of n^2
// squares.
// Space: O(n^2) -- the visited board and the queue.
//
// Siblings: SwimInRisingWaterDijkstra finds the same answer in one pass, always stepping onto the
// lowest square next to the area already reached.

public final class SwimInRisingWater {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int swimInWater(int[][] grid) {
		int left = grid[0][0];
		int right = highest(grid);
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (canSwim(grid, mid)) {
				right = mid; // KEEP: mid works, so the answer is mid or less
			} else {
				left = mid + 1; // SKIP: mid fails, so the answer is above it
			}
		}
		return left; // RETURN: the smallest depth that works
	}

	private static int highest(int[][] grid) {
		// the answer can never exceed the highest square on the board
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
			return false; // BASE: the start itself is under water too deep to stand in
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
				return true; // RETURN: the far corner is reachable
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
					continue; // SKIP: too high to swim onto at this depth
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

		// repeated values are outside LC 778; the highest square is 5, so the answer is 5
		int[][] g3 = { { 3, 5 }, { 5, 5 } };
		System.out.println(swimInWater(g3)); // 5
	}
}

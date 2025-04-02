package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1293. Shortest Path in a Grid with Obstacles Elimination

public final class ShortestPathWithObstaclesElimination {
	// Time Complexity: O(n * m * k)
	// Space Complexity: O(n * m * k)

	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };
	private static final int[] dir = { -1, 0, 1, 0, -1 };

	public static int shortestPath(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		Deque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] { 0, 0, 0, 0 });
		boolean[][][] visited = new boolean[n][m][k + 1];
		visited[0][0][0] = true;
		while (!queue.isEmpty()) {
			for (int size = 0; size < queue.size(); size++) {
				int[] p = queue.poll();
				int x = p[0], y = p[1], obstacles = p[2], dist = p[3];
				if (x == n - 1 && y == m - 1) {
					return dist;
				}
				for (int d = 0; d < 4; d++) {
					int row = x + dir[d];
					int col = y + dir[d + 1];
					if (!valid(grid, row, col)) {
						continue;
					}
					int eliminations = obstacles + grid[row][col];
					if (eliminations > k || visited[row][col][eliminations]) {
						continue;
					}
					queue.offer(new int[] { row, col, eliminations, dist + 1 });
					visited[row][col][eliminations] = true;
				}
			}
		}
		return -1;
	}

	private static boolean valid(int[][] grid, int x, int y) {
		boolean row = x >= 0 && x < grid.length;
		boolean col = y >= 0 && y < grid[0].length;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] grid = { //
				{ 0, 0, 0 }, //
				{ 1, 1, 0 }, //
				{ 0, 0, 0 }, //
				{ 0, 1, 1 }, //
				{ 0, 0, 0 } };//
		System.out.println(shortestPath(grid, 1)); // 6

		int[][] grid1 = { //
				{ 0, 1, 1 }, //
				{ 1, 1, 1 }, //
				{ 1, 0, 0 } };//
		System.out.println(shortestPath(grid1, 1)); // -1

		int[][] grid2 = { { 0 } };
		System.out.println(shortestPath(grid2, 1)); // 0

		int[][] grid3 = { { 0, 0, 0 } };
		System.out.println(shortestPath(grid3, 1)); // 2
	}
}
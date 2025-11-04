package com.svetanis.datastructures.graph.advanced;

import java.util.Arrays;
import java.util.PriorityQueue;

// 2577. Minimum Time to Visit a Cell In a Grid

public final class MinTimeToVisitCell {
	// Time Complexity: O(n * m log (n * m))
	// Space Complexity: O(n * m)

	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int minTime(int[][] grid) {
		if (grid[0][1] > 1 && grid[1][0] > 1) {
			return -1;
		}
		int n = grid.length;
		int m = grid[0].length;
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		dist[0][0] = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		pq.offer(new int[] { 0, 0, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int row = node[0];
			int col = node[1];
			int time = node[2];
			if (row == n - 1 && col == m - 1) {
				return time;
			}
			for (int k = 0; k < 4; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				if (valid(grid, x, y)) {
					int nextTime = time + 1;
					if (nextTime < grid[x][y]) {
						nextTime = grid[x][y] + (grid[x][y] - nextTime) % 2;
					}
					if (nextTime < dist[x][y]) {
						dist[x][y] = nextTime;
						pq.offer(new int[] { x, y, nextTime });
					}
				}
			}
		}
		return -1;
	}

	private static boolean valid(int[][] grid, int x, int y) {
		int n = grid.length;
		int m = grid[0].length;
		return x >= 0 && x < n && y >= 0 && y < m;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1, 3, 2 }, { 5, 1, 2, 5 }, { 4, 3, 8, 6 } };
		System.out.println(minTime(g1)); // 7
		int[][] g2 = { { 0, 2, 4 }, { 3, 2, 1 }, { 1, 0, 4 } };
		System.out.println(minTime(g2)); // -1
	}
}

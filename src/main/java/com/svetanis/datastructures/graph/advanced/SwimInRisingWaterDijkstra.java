package com.svetanis.datastructures.graph.advanced;

import java.util.PriorityQueue;

// 778. Swim in Rising Water
// Dijkstra's Algorithm with Min-Heap

public final class SwimInRisingWaterDijkstra {
	// Time Complexity: O(n^2 * log n);
	// Space Complexity: O(n^2)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int swimInWater(int[][] grid) {
		int n = grid.length;
		boolean[][] visited = new boolean[n][n];
		visited[0][0] = true;
		// Priority Queue: row, col, time
		PriorityQueue<int[]> dq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		dq.add(new int[] { 0, 0, grid[0][0] });
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int row = node[0];
			int col = node[1];
			int time = node[2];
			if (row == n - 1 && col == n - 1) {
				return time;
			}
			for (int i = 0; i < 4; i++) {
				int x = row + dx[i];
				int y = col + dy[i];
				if (valid(n, x, y) && !visited[x][y]) {
					visited[x][y] = true;
					int max = Math.max(time, grid[x][y]);
					dq.add(new int[] { x, y, max });
				}
			}
		}
		return -1;
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
	}
}
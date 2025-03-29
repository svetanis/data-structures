package com.svetanis.datastructures.graph.bfs.grid;

import java.util.PriorityQueue;

// 407. Trapping Rain Water II

public final class TrappingRainWaterII {
	// Time Complexity: O(n * m * log (n + m))
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, 1, 0, -1 };

	public static int trw(int[][] heights) {
		int n = heights.length;
		int m = heights[0].length;
		boolean[][] visited = new boolean[n][m];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		init(heights, visited, pq);
		int total = 0;
		while (!pq.isEmpty()) {
			int[] current = pq.poll();
			int h = current[0];
			int x = current[1];
			int y = current[2];
			for (int k = 0; k < dx.length; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				if (row >= 0 && row < n && col >= 0 && col < m && !visited[row][col]) {
					int diff = h - heights[row][col];
					total += Math.max(0, diff);
					visited[row][col] = true;
					int max = Math.max(h, heights[row][col]);
					pq.offer(new int[] { max, row, col });
				}
			}
		}
		return total;
	}

	private static void init(int[][] heights, boolean[][] visited, PriorityQueue<int[]> pq) {
		int n = heights.length;
		int m = heights[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
					pq.offer(new int[] { heights[i][j], i, j });
					visited[i][j] = true;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[][] heights1 = { { 1, 4, 3, 1, 3, 2 }, { 3, 2, 1, 3, 2, 4 }, { 2, 3, 3, 2, 3, 1 } };
		System.out.println(trw(heights1)); // 4

		int[][] heights2 = { { 3, 3, 3, 3, 3 }, { 3, 2, 2, 2, 3 }, { 3, 2, 1, 2, 3 }, { 3, 2, 2, 2, 3 },
				{ 3, 3, 3, 3, 3 } };
		System.out.println(trw(heights2)); // 10
	}
}

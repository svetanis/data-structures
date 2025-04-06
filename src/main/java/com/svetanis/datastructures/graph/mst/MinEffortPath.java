package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1631. Path With Minimum Effort
// Dijkstra's Algorithm with Min-Heap

public final class MinEffortPath {
	// Time & Space Complexity: O(n * m * log(n*m))

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };
	private static final int INF = (int) 1e6;

	public static int mep(int[][] heights) {
		int n = heights.length;
		int m = heights[0].length;
		// Priority Queue: row, col, effort
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		pq.offer(new int[] { 0, 0, 0 });
		int[][] efforts = efforts(n, m);
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int x = node[0];
			int y = node[1];
			int effort = node[2];
			if (x == n - 1 && y == m - 1) {
				return effort;
			}
			for (int i = 0; i < 4; i++) {
				int row = x + dx[i];
				int col = y + dy[i];
				if (safe(row, col, n, m)) {
					int diff = Math.abs(heights[row][col] - heights[x][y]);
					int max = Math.max(effort, diff);
					if (max < efforts[row][col]) {
						efforts[row][col] = max;
						pq.offer(new int[] { row, col, max });
					}
				}
			}
		}
		return 0;
	}

	private static int[][] efforts(int n, int m) {
		int[][] grid = new int[n][m];
		for (int[] row : grid) {
			Arrays.fill(row, INF);
		}
		return grid;
	}

	private static boolean safe(int x, int y, int n, int m) {
		boolean row = x >= 0 && x < n;
		boolean col = y >= 0 && y < m;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] heights1 = { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } };
		System.out.println(mep(heights1)); // 2
		int[][] heights2 = { { 1, 2, 3 }, { 3, 8, 4 }, { 5, 3, 5 } };
		System.out.println(mep(heights2)); // 1
		int[][] heights3 = { { 1, 2, 1, 1, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 },
				{ 1, 1, 1, 2, 1 } };
		System.out.println(mep(heights3)); // 0

	}
}

package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.java.base.utils.Print;

// 542. 01 Matrix

public final class Matrix01 {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] dist(int[][] grid) {
		int[][] dist = initDist(grid);
		bfs(dist);
		return dist;
	}

	private static void bfs(int[][] dist) {
		Queue<Point> queue = initQueue(dist);
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			int x = curr.x;
			int y = curr.y;
			for (int k = 0; k < dx.length; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				if (valid(dist, row, col) && dist[row][col] == -1) {
					queue.add(new Point(row, col));
					dist[row][col] = dist[x][y] + 1;
				}
			}
		}
	}

	private static Queue<Point> initQueue(int[][] dist) {
		Queue<Point> queue = new ArrayDeque<>();
		int m = dist.length;
		int n = dist[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (dist[i][j] == 0) {
					queue.offer(new Point(i, j));
				}
			}
		}
		return queue;
	}

	private static boolean valid(int[][] dist, int row, int col) {
		boolean isRow = row >= 0 && row < dist.length;
		boolean isCol = col >= 0 && col < dist[0].length;
		return isRow && isCol;
	}

	private static int[][] initDist(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] dist = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = grid[i][j] == 0 ? 0 : -1;
			}
		}
		return dist;
	}

	public static void main(String[] args) {
		int[][] grid1 = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		Print.print(dist(grid1)); // [0,0,0], [0,1,0], [0,0,0]
		int[][] grid2 = { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } };
		Print.print(dist(grid2)); // [0,0,0], [0,1,0], [1,2,1]
	}

	private static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

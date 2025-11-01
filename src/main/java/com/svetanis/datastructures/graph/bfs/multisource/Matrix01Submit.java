package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

// 542. 01 Matrix

public final class Matrix01Submit {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] dist(int[][] g) {
		if (g == null || g.length == 0) {
			return g;
		}
		int rows = g.length;
		int cols = g[0].length;
		int[][] dist = new int[rows][cols];
		Deque<int[]> dq = new ArrayDeque<>();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				boolean zero = g[row][col] == 0;
				dist[row][col] = zero ? 0 : -1;
				if (zero) {
					dq.offer(new int[] { row, col });
				}
			}
		}
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int row = cell[0], col = cell[1];
			for (int k = 0; k < dx.length; k++) {
				int x = row + dx[k], y = col + dy[k];
				if (x >= 0 && x < rows && y >= 0 && y < cols && dist[x][y] == -1) {
					dq.offer(new int[] { x, y });
					dist[x][y] = dist[row][col] + 1;
				}
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
}

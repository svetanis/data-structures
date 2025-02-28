package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

// 490. The Maze

public final class Maze {
	// Time & Space Complexity: O(n*m).

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static boolean hasPath(int[][] g, int[] src, int[] dst) {
		int n = g.length;
		int m = g[0].length;
		boolean[][] visited = new boolean[n][m];
		int x = src[0];
		int y = src[1];
		// mark as visited
		visited[x][y] = true;
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		while (!queue.isEmpty()) {
			int[] position = queue.poll();
			for (int k = 0; k < dx.length; k++) {
				int row = position[0] + dx[k];
				int col = position[1] + dy[k];
				if (safe(g, row, col)) {
					if (row == dst[0] && col == dst[1]) {
						return true;
					}
					if (!visited[row][col]) {
						visited[row][col] = true;
						queue.offer(new int[] { row, col });
					}
				}
			}
		}
		return false;
	}

	public static boolean hasPath2(int[][] g, int[] src, int[] dst) {
		int x = src[0];
		int y = src[1];
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		// mark as visited
		g[x][y] = 2;
		while (!queue.isEmpty()) {
			int[] position = queue.poll();
			for (int k = 0; k < dx.length; k++) {
				int row = position[0] + dx[k];
				int col = position[1] + dy[k];
				if (safe(g, row, col)) {
					if (row == dst[0] && col == dst[1]) {
						return true;
					}
					queue.offer(new int[] { row, col });
					g[row][col] = 2;
				}
			}
		}
		return false;
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0;
	}

	public static void main(String[] args) {
		int[] src = { 3, 1 };
		int[] dst = { 3, 3 };
		int[][] m1 = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } };
		System.out.println(hasPath(m1, src, dst)); // true
	}
}

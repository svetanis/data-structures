package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 490. The Maze

public final class MazeBfs {
	// Time & Space Complexity: O(n*m).

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static boolean hasPath(int[][] g, int[] src, int[] dst) {
		int rows = g.length;
		int cols = g[0].length;
		boolean[][] visited = new boolean[rows][cols];
		int x = src[0];
		int y = src[1];
		// mark as visited
		visited[x][y] = true;
		Deque<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		while (!queue.isEmpty()) {
			int[] position = queue.poll();
			for (int k = 0; k < dx.length; k++) {
				int row = position[0];
				int col = position[1];
				while (safe(g, row + dx[k], col + dy[k])) {
					row += dx[k];
					col += dy[k];
				}
				if (row == dst[0] && col == dst[1]) {
					return true;
				}
				if (!visited[row][col]) {
					visited[row][col] = true;
					queue.offer(new int[] { row, col });
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
		int[][] m1 = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } };
		System.out.println(hasPath(m1, new int[] { 3, 1 }, new int[] { 3, 3 })); // true

		int[][] m2 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(hasPath(m2, new int[] { 0, 4 }, new int[] { 4, 4 })); // true
		System.out.println(hasPath(m2, new int[] { 0, 4 }, new int[] { 3, 2 })); // false

		int[][] m3 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0 } };
		System.out.println(hasPath(m3, new int[] { 4, 3 }, new int[] { 0, 1 })); // false
	}
}

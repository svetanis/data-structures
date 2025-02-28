package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// 505. The Maze II

public final class MazeII {
	// Time & Space Complexity: O(n*m).

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };
	private static final int INF = Integer.MAX_VALUE;

	public static int shortestPath(int[][] g, int[] src, int[] dst) {
		int n = g.length;
		int m = g[0].length;
		int srcX = src[0];
		int srcY = src[1];
		int dstX = dst[0];
		int dstY = dst[1];
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, INF);
		}
		dist[srcX][srcY] = 0;
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		while (!queue.isEmpty()) {
			int[] position = queue.poll();
			int x = position[0];
			int y = position[1];
			for (int k = 0; k < dx.length; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				if (safe(g, row, col)) {
					queue.offer(new int[] { row, col });
					dist[row][col] = dist[x][y] + 1;
					if (row == dstX && col == dstY) {
						return dist[row][col];
					}
				}
			}
		}
		return dist[dstX][dstY] == INF ? -1 : dist[dstX][dstY];
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0;
	}

	public static void main(String[] args) {
		int[] src = { 0, 0 };
		int[] dst = { 4, 4 };
		int[][] m1 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(shortestPath(m1, src, dst)); // 8
	}
}

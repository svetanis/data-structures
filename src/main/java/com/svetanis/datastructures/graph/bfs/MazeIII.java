package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// 499. The Maze III

public final class MazeIII {
	// Time & Space Complexity: O(n*m).

	private static final int INF = Integer.MAX_VALUE;
	private static final int[][] DIR = { { -1, 0, 'u' }, { 1, 0, 'd' }, { 0, -1, 'l' }, { 0, 1, 'r' } };

	public static String shortestPath(int[][] maze, int[] ball, int[] hole) {
		int n = maze.length;
		int m = maze[0].length;
		int srcX = ball[0];
		int srcY = ball[1];
		int dstX = hole[0];
		int dstY = hole[1];
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, INF);
		}
		dist[srcX][srcY] = 0;
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(ball);
		String[][] path = new String[n][m];
		path[srcX][srcY] = "";

		while (!queue.isEmpty()) {
			int[] point = queue.poll();
			int x = point[0];
			int y = point[1];
			for (int[] direction : DIR) {
				int dy = direction[0];
				int dx = direction[1];
				String dc = String.valueOf((char) direction[2]);
				int steps = dist[x][y];
				int row = x;
				int col = y;
				while (safe(maze, row + dy, col + dx) && !(row == dstX && col == dstY)) {
					row += dy;
					col += dx;
					steps++;
				}
				boolean one = dist[row][col] > steps;
				boolean two = dist[row][col] == steps;
				boolean three = two && (path[x][y] + dc).compareTo(path[row][col]) < 0;
				if (one || three) {
					dist[row][col] = steps;
					path[row][col] = path[x][y] + dc;
					if (row != dstX || col != dstY) {
						queue.offer(new int[] { row, col });
					}
				}
			}
		}
		return path[dstX][dstY] == null ? "impossible" : path[dstX][dstY];
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0;
	}

	public static void main(String[] args) {
		int[] src = { 1, 1 };
		int[] dst = { 3, 2 };
		int[][] m1 = { { 1, 1, 1, 1 }, { 1, 0, 0, 1 }, { 1, 0, 1, 1 }, { 1, 0, 0, 0 }, { 1, 1, 1, 1 } };
		System.out.println(shortestPath(m1, src, dst)); // dr
	}
}

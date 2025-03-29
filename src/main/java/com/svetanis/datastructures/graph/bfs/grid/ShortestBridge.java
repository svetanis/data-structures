package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 934. Shortest Bridge

public final class ShortestBridge {
	// Time & Space Complexity: O(n*m).

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int shortestBridge(int[][] g) {
		Queue<Node> queue = new ArrayDeque<>();
		enqueFirstIsland(g, queue);
		int steps = 0;
		while (!queue.isEmpty()) {
			for (int count = queue.size(); count > 0; count--) {
				Node node = queue.poll();
				for (int k = 0; k < dx.length; k++) {
					int x = node.x + dx[k];
					int y = node.y + dy[k];
					if (safe(g, x, y)) {
						if (g[x][y] == 1) {
							return steps;
						}
						if (g[x][y] == 0) {
							g[x][y] = 2;
							queue.offer(new Node(x, y));
						}

					}
				}
			}
			steps++;
		}
		return -1;
	}

	private static void enqueFirstIsland(int[][] g, Queue<Node> queue) {
		int n = g.length;
		int m = g[0].length;
		boolean firstIsland = false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == 1) {
					dfs(g, i, j, queue);
					firstIsland = true;
					break;
				}
			}
			if (firstIsland) {
				break;
			}
		}
	}

	private static void dfs(int[][] g, int row, int col, Queue<Node> queue) {
		g[row][col] = 2;
		queue.offer(new Node(row, col));
		for (int k = 0; k < dx.length; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y) && g[x][y] == 1) {
				dfs(g, x, y, queue);
			}
		}
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] m1 = { { 0, 1 }, { 1, 0 } };
		System.out.println(shortestBridge(m1)); // 1

		int[][] m2 = { { 0, 1, 0 }, { 0, 0, 0 }, { 0, 0, 1 } };
		System.out.println(shortestBridge(m2)); // 2

		int[][] m3 = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } };
		System.out.println(shortestBridge(m3)); // 1

	}

	private static class Node {
		private int x;
		private int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}

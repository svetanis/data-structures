package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

// 1091. Shortest Path in Binary Matrix

public final class ShortestPathBinaryMatrixSubmit {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int dx[] = { 1, 1, 1, 0, -1, -1, -1, 0 };
	private static final int dy[] = { -1, 0, 1, 1, 1, 0, -1, -1 };

	public static int shortestPath(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		if (g[0][0] == 1) {
			return -1;
		}
		// mark as visited
		g[0][0] = 1;
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(new Node(0, 0));
		for (int step = 1; !queue.isEmpty(); step++) {
			for (int count = queue.size(); count > 0; count--) {
				Node node = queue.poll();
				int x = node.x;
				int y = node.y;
				if (x == n - 1 && y == m - 1) {
					return step;
				}
				for (int k = 0; k < dx.length; k++) {
					int row = x + dx[k];
					int col = y + dy[k];
					if (safe(g, row, col)) {
						g[row][col] = 1;
						queue.offer(new Node(row, col));
					}
				}
			}
		}
		return -1;
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1 }, { 1, 0 } };
		System.out.println(shortestPath(g1)); // 2
		int[][] g2 = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 1, 0 } };
		System.out.println(shortestPath(g2)); // 4
		int[][] g3 = { { 1, 0, 0 }, { 1, 1, 0 }, { 1, 1, 0 } };
		System.out.println(shortestPath(g3)); // -1
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
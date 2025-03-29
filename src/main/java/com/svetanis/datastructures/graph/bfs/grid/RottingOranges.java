package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 994. Rotting Oranges

public final class RottingOranges {
	// Time & Space Complexity: O(n*m).

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int orangesRotting(int[][] g) {
		Queue<Node> queue = new ArrayDeque<>();
		int fresh = countAndEnque(g, queue);
		int minutes = 0;
		while (!queue.isEmpty() && fresh > 0) {
			for (int count = queue.size(); count > 0; count--) {
				Node node = queue.poll();
				for (int k = 0; k < dx.length; k++) {
					int x = node.x + dx[k];
					int y = node.y + dy[k];
					if (safe(g, x, y) && g[x][y] == 1) {
						g[x][y] = 2;
						fresh--;
						queue.offer(new Node(x, y));
					}
				}
			}
			minutes++;
		}
		return fresh > 0 ? -1 : minutes;
	}

	private static int countAndEnque(int[][] g, Queue<Node> queue) {
		int n = g.length;
		int m = g[0].length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == 1) {
					count++;
				}
				if (g[i][j] == 2) {
					queue.offer(new Node(i, j));
				}
			}
		}
		return count;
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] m1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		System.out.println(orangesRotting(m1)); // 4

		int[][] m2 = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
		System.out.println(orangesRotting(m2)); // -1

		int[][] m3 = { { 0, 2 } };
		System.out.println(orangesRotting(m3)); // 0
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

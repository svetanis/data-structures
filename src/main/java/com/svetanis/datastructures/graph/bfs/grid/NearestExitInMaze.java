package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 1926. Nearest Exit from Entrance in Maze

public final class NearestExitInMaze {
	// Time & Space Complexity: O(n*m).

	private static final char EMPTY = '.';
	private static final char WALL = '+';

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int shortestPath(char[][] g, int[] entrance) {
		int x = entrance[0];
		int y = entrance[1];
		Node src = new Node(x, y);
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(src);
		// mark as visited
		g[x][y] = WALL;
		int steps = 0;
		while (!queue.isEmpty()) {
			steps++;
			for (int count = queue.size(); count > 0; count--) {
				Node node = queue.poll();
				for (int k = 0; k < dx.length; k++) {
					int row = node.x + dx[k];
					int col = node.y + dy[k];
					if (safe(g, row, col)) {
						if (boundary(g, row, col)) {
							return steps;
						}
						queue.offer(new Node(row, col));
						g[row][col] = WALL;
					}
				}
			}
		}
		return -1;
	}

	private static boolean safe(char[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == EMPTY;
	}

	private static boolean boundary(char[][] g, int x, int y) {
		boolean row = x == 0 || x == g.length - 1;
		boolean col = y == 0 || y == g[0].length - 1;
		return row || col;
	}

	public static void main(String[] args) {
		int[] e1 = { 1, 2 };
		char[][] m1 = { { '+', '+', '.', '+' }, { '.', '.', '.', '+' }, { '+', '+', '+', '.' } };
		System.out.println(shortestPath(m1, e1)); // 1

		int[] e2 = { 1, 0 };
		char[][] m2 = { { '+', '+', '+' }, { '.', '.', '.' }, { '+', '+', '+' } };
		System.out.println(shortestPath(m2, e2)); // 2

		int[] e3 = { 0, 0 };
		char[][] m3 = { { '.', '+' } };
		System.out.println(shortestPath(m3, e3)); // -1

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

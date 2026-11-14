package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 934. Shortest Bridge
//
// Input: an n x n grid of 0 (water) and 1 (land) holding exactly two islands, where an island is
// a group of land cells joined up, down, left or right. On LeetCode: 2 <= n <= 100.
// Returns the fewest water cells that must be turned into land to join the two islands. The grid
// is changed: the cells of the first island and the water reached are overwritten with 2.
//
// The one idea: first find one island and put ALL of its cells into the queue (a depth-first walk
// from the first land cell found, marking its cells 2). Then spread outward from the whole island
// at once: one queue entry is a cell that has just been reached, and taking the queue out one
// batch at a time is one more water cell of bridge. Water reached gets marked 2 so it is
// not reached twice. The first time the spread touches a cell still marked 1, it has hit the
// other island, and the number of batches taken so far is the number of water cells crossed.
//
// Time: O(n * m) -- the walk marks each land cell once and the spread queues each cell once.
// Space: O(n * m) -- the queue; the recursive walk can go as deep as the first island is large.
//
// Siblings: none -- this is the only file for this problem.

public final class ShortestBridge {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int shortestBridge(int[][] g) {
		Queue<Node> queue = new ArrayDeque<>();
		enqueFirstIsland(g, queue); // SEED: every cell of one island
		int steps = 0;
		while (!queue.isEmpty()) {
			for (int count = queue.size(); count > 0; count--) { // LEVEL: one batch = one more water cell
				Node node = queue.poll(); // TAKE
				for (int k = 0; k < dx.length; k++) {
					int x = node.x + dx[k];
					int y = node.y + dy[k];
					if (safe(g, x, y)) {
						if (g[x][y] == 1) {
							return steps; // RETURN: touched the other island
						}
						if (g[x][y] == 0) {
							g[x][y] = 2; // RECORD: water reached, mark so it is not queued twice
							queue.offer(new Node(x, y));
						}

					}
				}
			}
			steps++; // COUNT: the next batch is one more water cell away
		}
		return -1; // RETURN: not reached for a grid with two islands
	}

	private static void enqueFirstIsland(int[][] g, Queue<Node> queue) {
		int n = g.length;
		int m = g[0].length;
		boolean firstIsland = false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == 1) {
					dfs(g, i, j, queue); // WALK: the whole island of the first land cell found
					firstIsland = true;
					break;
				}
			}
			if (firstIsland) {
				break; // STOP: only the first island is seeded
			}
		}
	}

	private static void dfs(int[][] g, int row, int col, Queue<Node> queue) {
		g[row][col] = 2; // RECORD: marked 2, so the other island (still 1) stays recognisable
		queue.offer(new Node(row, col)); // SEED
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
		return row && col; // SKIP: off the grid
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

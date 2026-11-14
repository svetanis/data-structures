package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 1091. Shortest Path in Binary Matrix
//
// Input: an n x n grid of 0 (open) and 1 (blocked). A path goes from the top-left to the
// bottom-right cell through open cells, each step to one of the 8 neighbours (sides and
// corners). On LeetCode: 1 <= n <= 100.
// Returns the number of cells on the shortest such path (both ends counted), or -1 if there is
// none. The grid is changed: every cell reached is overwritten with 1.
//
// The one idea: one queue entry is a cell that has just been reached. All cells that are the same
// number of steps from the start wait in the queue together, so taking the queue out one batch at
// a time counts the cells on the path: batch 1 is the start alone, batch 2 is everything one
// step away, and so on. The first batch that holds the bottom-right cell gives the answer. A cell
// is turned into a 1 the moment it is queued, which marks it as seen, so it is queued once.
//
// Time: O(n * m) -- each cell is queued at most once, with 8 neighbours looked at.
// Space: O(n * m) -- the queue.
//
// Siblings: ShortestPathBinary is the 4-direction search with a chosen source and destination
// and a set instead of marking the grid.

public final class ShortestPathBinaryMatrixSubmit {

	private static final int[] dx = { 1, 1, 1, 0, -1, -1, -1, 0 };
	private static final int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

	public static int shortestPath(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		if (g[0][0] == 1) {
			return -1; // RETURN: the start is blocked
		}
		// RECORD: mark as visited
		g[0][0] = 1;
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(new Node(0, 0));
		for (int step = 1; !queue.isEmpty(); step++) { // COUNT: step = cells on the path so far
			for (int count = queue.size(); count > 0; count--) { // LEVEL: only the cells of this batch
				Node node = queue.poll(); // TAKE
				int x = node.x;
				int y = node.y;
				if (x == n - 1 && y == m - 1) {
					return step; // RETURN: the first batch holding the corner
				}
				for (int k = 0; k < dx.length; k++) {
					int row = x + dx[k];
					int col = y + dy[k];
					if (safe(g, row, col)) {
						g[row][col] = 1; // RECORD: mark when queued
						queue.offer(new Node(row, col));
					}
				}
			}
		}
		return -1; // RETURN: the corner was never reached
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0; // SKIP: off the grid, blocked, or seen
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
package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// 542. 01 Matrix
//
// Input: a grid of 0 and 1. Moves are one step up, down, left or right. Returns a grid of the
// same size where each cell holds the number of steps to the nearest 0. On LeetCode:
// 1 <= m, n <= 10^4, 1 <= m * n <= 10^4, grid[i][j] is 0 or 1, at least one 0.
//
// The one idea: dist[i][j] holds the steps from cell (i, j) to the nearest 0, and -1 means "not
// reached yet". Every 0 is put in the queue at the start with dist 0, so the search spreads from
// all the zeros at once, one ring of steps at a time. A queue entry is one cell whose dist is
// final; its unreached neighbours get dist + 1 and join the queue. The first time a cell is
// reached is by the nearest zero, because cells leave the queue in order of their distance, so
// a cell is set once and never changed. The dist grid doubles as the visited mark.
//
// Time: O(m * n) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(m * n) -- the dist grid and the queue.
//
// Siblings: Matrix01Submit is the same walk written in one method with int[] pairs.

public final class Matrix01 {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] dist(int[][] grid) {
		int[][] dist = initDist(grid);
		bfs(dist);
		return dist;
	}

	private static void bfs(int[][] dist) {
		Queue<Point> queue = initQueue(dist);
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			int x = curr.x;
			int y = curr.y;
			for (int k = 0; k < dx.length; k++) { // WALK: the four neighbours
				int row = x + dx[k];
				int col = y + dy[k];
				if (valid(dist, row, col) && dist[row][col] == -1) { // SKIP: off the grid or already reached
					queue.add(new Point(row, col));
					dist[row][col] = dist[x][y] + 1; // RECORD: one step farther than curr
				}
			}
		}
	}

	private static Queue<Point> initQueue(int[][] dist) {
		Queue<Point> queue = new ArrayDeque<>();
		int m = dist.length;
		int n = dist[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (dist[i][j] == 0) { // SEED: every zero starts the search
					queue.offer(new Point(i, j));
				}
			}
		}
		return queue;
	}

	private static boolean valid(int[][] dist, int row, int col) {
		boolean isRow = row >= 0 && row < dist.length;
		boolean isCol = col >= 0 && col < dist[0].length;
		return isRow && isCol;
	}

	private static int[][] initDist(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] dist = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = grid[i][j] == 0 ? 0 : -1; // BASE: a zero is 0 steps from itself, a one is unreached
			}
		}
		return dist;
	}

	public static void main(String[] args) {
		int[][] grid1 = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		System.out.println(Arrays.deepToString(dist(grid1))); // [[0, 0, 0], [0, 1, 0], [0, 0, 0]]
		int[][] grid2 = { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } };
		System.out.println(Arrays.deepToString(dist(grid2))); // [[0, 0, 0], [0, 1, 0], [1, 2, 1]]
	}

	private static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 542. 01 Matrix
//
// Input: a grid of 0 and 1. Moves are one step up, down, left or right. Returns a grid of the
// same size where each cell holds the number of steps to the nearest 0. On LeetCode:
// 1 <= m, n <= 10^4, 1 <= m * n <= 10^4, grid[i][j] is 0 or 1, at least one 0.
//
// The one idea: dist[row][col] holds the steps from that cell to the nearest 0, and -1 means "not
// reached yet". Every 0 is put in the queue at the start with dist 0, so the search spreads from
// all the zeros at once, one ring of steps at a time. A queue entry is one cell { row, col }
// whose dist is final; its unreached neighbours get dist + 1 and join the queue. The first time a
// cell is reached is by the nearest zero, because cells leave the queue in order of their
// distance, so a cell is set once and never changed.
//
// Time: O(m * n) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(m * n) -- the dist grid and the queue.
//
// Siblings: Matrix01 is the same walk split into small methods with a Point class.

public final class Matrix01Submit {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] dist(int[][] g) {
		if (g == null || g.length == 0) {
			return g;
		}
		int rows = g.length;
		int cols = g[0].length;
		int[][] dist = new int[rows][cols];
		Deque<int[]> dq = new ArrayDeque<>();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				boolean zero = g[row][col] == 0;
				dist[row][col] = zero ? 0 : -1; // BASE: a zero is 0 steps from itself, a one is unreached
				if (zero) {
					dq.offer(new int[] { row, col }); // SEED: every zero starts the search
				}
			}
		}
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int row = cell[0], col = cell[1];
			for (int k = 0; k < dx.length; k++) { // WALK: the four neighbours
				int x = row + dx[k], y = col + dy[k];
				if (x >= 0 && x < rows && y >= 0 && y < cols && dist[x][y] == -1) { // SKIP: off the grid or reached
					dq.offer(new int[] { x, y });
					dist[x][y] = dist[row][col] + 1; // RECORD: one step farther than the cell
				}
			}
		}
		return dist; // RETURN: the steps grid
	}

	public static void main(String[] args) {
		int[][] grid1 = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		System.out.println(Arrays.deepToString(dist(grid1))); // [[0, 0, 0], [0, 1, 0], [0, 0, 0]]
		int[][] grid2 = { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } };
		System.out.println(Arrays.deepToString(dist(grid2))); // [[0, 0, 0], [0, 1, 0], [1, 2, 1]]
	}
}

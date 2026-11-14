package com.svetanis.datastructures.graph.bfs.multisource;

import static java.lang.Integer.MAX_VALUE;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// Distance of the nearest 1 for each cell of a binary matrix (not a LeetCode problem; it is
// 542 01 Matrix with the roles of 0 and 1 swapped).
//
// Input: a binary matrix. Moves are one step up, down, left or right. Returns a matrix of the
// same size where each cell holds the number of steps to the nearest 1; a 1 holds 0. If the
// matrix holds no 1, every cell stays Integer.MAX_VALUE.
//
// The one idea: dist[i][j] holds the steps from cell (i, j) to the nearest 1, and MAX_VALUE
// means "not reached yet". Every 1 is put in the queue at the start with dist 0, so the search
// spreads from all the ones at once, one ring of steps at a time. A queue entry is one cell whose
// dist is final; a neighbour whose dist is larger than that cell's dist + 1 gets dist + 1 and joins
// the queue. Cells leave the queue in order of their distance, so the first time a cell is reached
// is by the nearest one, and the test "larger than dist + 1" is true only for a cell not yet reached.
//
// Time: O(n * m) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(n * m) -- the dist matrix and the queue.
//
// Siblings: Matrix01 is the same walk for the nearest 0, with -1 as the "not reached" mark.

public final class NearestOneBinaryMatrix {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] nearestOne(int[][] grid) {
		int[][] dist = init(grid);
		bfs(dist);
		return dist;
	}

	private static int[][] init(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int[][] dist = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dist[i][j] = MAX_VALUE; // BASE: not reached yet
				if (grid[i][j] == 1) {
					dist[i][j] = 0; // BASE: a one is 0 steps from itself
				}
			}
		}
		return dist;
	}

	private static void bfs(int[][] dist) {
		Queue<Point> queue = initQueue(dist);
		while (!queue.isEmpty()) {
			Point p = queue.poll();
			for (int i = 0; i < 4; i++) { // WALK: the four neighbours
				int r = p.x + dx[i];
				int c = p.y + dy[i];
				if (valid(dist, r, c) && dist[r][c] > dist[p.x][p.y] + 1) { // SKIP: off the grid or reached
					dist[r][c] = dist[p.x][p.y] + 1; // RECORD: one step farther than p
					queue.offer(new Point(r, c));
				}
			}
		}
	}

	private static Queue<Point> initQueue(int[][] dist) {
		Queue<Point> queue = new ArrayDeque<>();
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[0].length; j++) {
				if (dist[i][j] == 0) { // SEED: every one starts the search
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

	public static void main(String[] args) {
		int[][] matrix = { //
				{ 0, 0, 0, 1 }, //
				{ 0, 0, 1, 1 }, //
				{ 0, 1, 1, 0 }, //
		};//
		System.out.println(Arrays.deepToString(nearestOne(matrix))); // [[3, 2, 1, 0], [2, 1, 0, 0], [1, 0, 0, 1]]

		int[][] noOne = { { 0, 0 } };
		System.out.println(Arrays.deepToString(nearestOne(noOne))); // [[2147483647, 2147483647]]
	}
}

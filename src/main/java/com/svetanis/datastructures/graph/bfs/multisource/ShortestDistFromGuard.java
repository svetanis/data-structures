package com.svetanis.datastructures.graph.bfs.multisource;

import static java.util.Arrays.fill;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

// Shortest distance of every open cell from the nearest guard (not a LeetCode problem; it is
// 286 Walls and Gates with guards, open cells and walls written as characters).
//
// Input: a character matrix; 'G' is a guard, 'O' is an open cell, 'W' is a wall. Moves are one
// step up, down, left or right, only over open cells. Returns an int matrix: a guard holds 0,
// an open cell holds the steps to the nearest guard, and a wall or an open cell no guard can
// reach holds -1.
//
// The one idea: grid[i][j] holds the steps from cell (i, j) to the nearest guard, and -1 means
// "not reached yet". Every guard is put in the queue at the start with 0, so the search spreads
// from all the guards at once, one ring of steps at a time. A queue entry is one cell whose
// distance is final; its unreached open neighbours get that distance + 1 and join the queue.
// Cells leave the queue in order of their distance, so the first time a cell is reached is by
// the nearest guard. grid doubles as the visited mark, so the queue entry carries no distance.
//
// Time: O(n * m) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(n * m) -- the result matrix and the queue.
//
// Siblings: WallsAndGates, WallsAndGatesSubmit and ZombieInDungeon are the same search with
// integers in the grid instead of characters; 0 is a gate there and -1 is a wall.

public final class ShortestDistFromGuard {

	private static int dx[] = { -1, 0, 1, 0 };
	private static int dy[] = { 0, 1, 0, -1 };

	public static int[][] shortestDist(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[][] grid = initGrid(matrix);
		Queue<Cell> queue = initQueue(matrix);
		while (!queue.isEmpty()) {
			Cell node = queue.poll();

			// the distance is already recorded in grid, which doubles as
			// the visited mark -- there is nothing for the node to carry
			int dist = grid[node.getX()][node.getY()] + 1;
			for (int i = 0; i < dx.length; i++) { // WALK: the four neighbours
				int x = node.getX() + dx[i];
				int y = node.getY() + dy[i];
				if (valid(x, y, n, m) && safe(matrix, grid, x, y)) { // SKIP: off the grid, a wall, or reached
					grid[x][y] = dist; // RECORD: one step farther than node
					queue.offer(new Cell(x, y));
				}
			}
		}
		return grid; // RETURN: -1 stays on walls and on unreachable cells
	}

	private static boolean safe(char[][] matrix, int[][] grid, int r, int c) {
		if (matrix[r][c] != 'O' || grid[r][c] != -1) {
			return false;
		}
		return true;
	}

	private static boolean valid(int r, int c, int n, int m) {
		return r >= 0 && r < n && c >= 0 && c < m;
	}

	private static Queue<Cell> initQueue(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		Queue<Cell> queue = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j] == 'G') { // SEED: every guard starts the search
					queue.offer(new Cell(i, j));
				}
			}
		}
		return queue;
	}

	private static int[][] initGrid(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[][] grid = new int[n][m];
		for (int i = 0; i < n; i++) {
			fill(grid[i], -1); // BASE: not reached yet
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j] == 'G') {
					grid[i][j] = 0; // BASE: a guard is 0 steps from itself
				}
			}
		}
		return grid;
	}

	public static void main(String[] args) {
		char matrix[][] = { //
				{ 'O', 'O', 'O', 'O', 'G' }, //
				{ 'O', 'W', 'W', 'O', 'O' }, //
				{ 'O', 'O', 'O', 'W', 'O' }, //
				{ 'G', 'W', 'W', 'W', 'O' }, //
				{ 'O', 'O', 'O', 'O', 'G' } };//
		int[][] grid = shortestDist(matrix);
		System.out.println(Arrays.deepToString(grid)); // [[3, 3, 2, 1, 0], [2, -1, -1, 2, 1], [1, 2, 3, -1, 2], [0, -1, -1, -1, 1], [1, 2, 2, 1, 0]]
	}
}

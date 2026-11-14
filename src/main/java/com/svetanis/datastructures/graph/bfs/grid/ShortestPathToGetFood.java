package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1730. Shortest Path to Get Food
//
// Input: a grid of characters: '*' your start (exactly one), 'O' a free cell, 'X' an obstacle and
// '#' a food cell (at least one). You move up, down, left or right onto any cell that is not 'X'.
// On LeetCode: 1 <= rows, cols <= 200.
// Returns the fewest steps from the start to any food cell, or -1 if none can be reached. The
// grid is changed: every free cell reached is overwritten with 'X'.
//
// The one idea: one queue entry is a cell that has just been reached. All cells that are the same
// number of steps from the start wait in the queue together, so taking the queue out one batch at
// a time counts the steps: batch 1 is everything one step away, batch 2 two steps away, and so
// on. The first food cell found is therefore a nearest one, whichever food it is. A cell is
// turned into an obstacle the moment it is queued, which marks it as seen, so it is queued once
// (only the start cell, which keeps its '*', can be queued a second time, by a neighbour).
//
// Time: O(n * m) -- each cell is queued at most once, with 4 neighbours looked at.
// Space: O(n * m) -- the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class ShortestPathToGetFood {

	private static final int[] dir = { -1, 0, 1, 0, -1 };

	public static int shortestPath(char[][] grid) {
		Deque<int[]> queue = init(grid); // SEED: the start cell
		int steps = 0;
		while (!queue.isEmpty()) {
			++steps; // COUNT: every cell of this batch is one step further out
			int size = queue.size(); // LEVEL: only the cells of this batch
			for (int i = 0; i < size; i++) {
				int[] p = queue.poll(); // TAKE
				for (int k = 0; k < 4; k++) {
					int x = p[0] + dir[k]; // dir read in overlapping pairs: (dir[k], dir[k + 1])
					int y = p[1] + dir[k + 1];
					if (valid(grid, x, y) && grid[x][y] != 'X') {
						if (grid[x][y] == '#') {
							return steps; // RETURN: the first food cell found is nearest
						}
						grid[x][y] = 'X'; // RECORD: mark when queued
						queue.offer(new int[] { x, y });
					}
				}
			}
		}
		return -1; // RETURN: no food can be reached
	}

	private static boolean valid(char[][] grid, int x, int y) {
		boolean row = x >= 0 && x < grid.length;
		boolean col = y >= 0 && y < grid[0].length;
		return row && col; // SKIP: off the grid
	}

	private static Deque<int[]> init(char[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		Deque<int[]> queue = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == '*') {
					queue.offer(new int[] { i, j }); // SEED
					break;
				}
			}
		}
		return queue;
	}

	public static void main(String[] args) {
		char[][] grid = { //
				{ '*', 'O', 'O', 'X' }, //
				{ 'O', 'X', 'O', '#' }, //
				{ 'O', 'O', 'X', 'O' } };//
		System.out.println(shortestPath(grid)); // 4

		char[][] grid1 = { //
				{ 'X', 'X', 'X', 'X', 'X', 'X' }, //
				{ 'X', '*', 'O', 'O', 'O', 'X' }, //
				{ 'X', 'O', 'O', '#', 'O', 'X' }, //
				{ 'X', 'X', 'X', 'X', 'X', 'X' } };//
		System.out.println(shortestPath(grid1)); // 3

		char[][] grid2 = { //
				{ 'X', 'X', 'X', 'X', 'X' }, //
				{ 'X', '*', 'X', 'O', 'X' }, //
				{ 'X', 'O', 'X', '#', 'X' }, //
				{ 'X', 'X', 'X', 'X', 'X' } };//
		System.out.println(shortestPath(grid2)); // -1

	}
}
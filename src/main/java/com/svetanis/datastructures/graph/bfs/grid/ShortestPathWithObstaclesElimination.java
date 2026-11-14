package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1293. Shortest Path in a Grid with Obstacles Elimination
//
// Input: a grid of 0 (empty) and 1 (obstacle), and an integer k. You start at the top-left cell
// and want the bottom-right cell, moving up, down, left or right; you may walk through at most k
// obstacles (each one you step on is "eliminated"). On LeetCode: 1 <= rows, cols <= 40,
// 1 <= k <= rows * cols, and both corner cells are 0.
// Returns the fewest steps from the top-left to the bottom-right cell, or -1 if it cannot be done.
//
// The one idea: standing on the same cell having eliminated 0 obstacles or 3 obstacles are two
// different situations (the second has less left to spend), so one queue entry is
// {row, col, obstacles used so far, steps so far}. A cell is not marked as seen; the pair (cell,
// obstacles used) is: visited[row][col][used]. Every step costs 1, so taking the queue out one
// batch at a time keeps the entries in order of steps, and the first entry that stands on the
// bottom-right cell has the fewest steps. A pair that is queued a second time could only have more
// steps, so it is never queued again.
//
// Time: O(n * m * k) -- n * m * (k + 1) pairs, each queued once with 4 neighbours looked at.
// Space: O(n * m * k) -- the visited table and the queue.
//
// Siblings: ShortestPathWithObstaclesEliminationPQ takes the entries from a priority queue ordered
// by steps plus the up/down/left/right distance still to go (ignoring obstacles), instead of
// batch by batch.

public final class ShortestPathWithObstaclesElimination {

	// read as OVERLAPPING pairs: (-1,0) (0,1) (1,0) (0,-1). Five entries
	// give four moves, because each one is reused as the next one's second
	// half -- the same four offsets a dx/dy pair would spell out separately
	private static final int[] dir = { -1, 0, 1, 0, -1 };

	public static int shortestPath(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		Deque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] { 0, 0, 0, 0 }); // START: {row, col, obstacles used, steps}
		boolean[][][] visited = new boolean[n][m][k + 1];
		visited[0][0][0] = true;
		while (!queue.isEmpty()) {
			// LEVEL: queue.size() is read ONCE, before the loop, so the loop covers exactly the
			// entries already queued -- the current level -- and not the children added meanwhile
			for (int size = queue.size(); size > 0; size--) {
				int[] p = queue.poll(); // TAKE
				int x = p[0], y = p[1], obstacles = p[2], dist = p[3];
				if (x == n - 1 && y == m - 1) {
					return dist; // RETURN: the first entry on the corner has the fewest steps
				}
				for (int d = 0; d < 4; d++) {
					int row = x + dir[d];
					int col = y + dir[d + 1];
					if (!valid(grid, row, col)) {
						continue; // SKIP: off the grid
					}
					int eliminations = obstacles + grid[row][col]; // COUNT: stepping on a 1 spends one
					if (eliminations > k || visited[row][col][eliminations]) {
						continue; // SKIP: over budget, or this (cell, used) pair was queued before
					}
					queue.offer(new int[] { row, col, eliminations, dist + 1 });
					visited[row][col][eliminations] = true; // RECORD: mark when queued
				}
			}
		}
		return -1; // RETURN: the corner cannot be reached within k eliminations
	}

	private static boolean valid(int[][] grid, int x, int y) {
		boolean row = x >= 0 && x < grid.length;
		boolean col = y >= 0 && y < grid[0].length;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] grid = { //
				{ 0, 0, 0 }, //
				{ 1, 1, 0 }, //
				{ 0, 0, 0 }, //
				{ 0, 1, 1 }, //
				{ 0, 0, 0 } };//
		System.out.println(shortestPath(grid, 1)); // 6

		int[][] grid1 = { //
				{ 0, 1, 1 }, //
				{ 1, 1, 1 }, //
				{ 1, 0, 0 } };//
		System.out.println(shortestPath(grid1, 1)); // -1

		int[][] grid2 = { { 0 } };
		System.out.println(shortestPath(grid2, 1)); // 0

		int[][] grid3 = { { 0, 0, 0 } };
		System.out.println(shortestPath(grid3, 1)); // 2
	}
}
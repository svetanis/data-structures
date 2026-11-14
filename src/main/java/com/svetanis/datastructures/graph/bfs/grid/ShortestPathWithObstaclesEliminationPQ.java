package com.svetanis.datastructures.graph.bfs.grid;

import java.util.Comparator;
import java.util.PriorityQueue;

// 1293. Shortest Path in a Grid with Obstacles Elimination
//
// Input: a grid of 0 (empty) and 1 (obstacle), and an integer k. You start at the top-left cell
// and want the bottom-right cell, moving up, down, left or right; you may walk through at most k
// obstacles (each one you step on is "eliminated"). On LeetCode: 1 <= rows, cols <= 40,
// 1 <= k <= rows * cols, and both corner cells are 0.
// Returns the fewest steps from the top-left to the bottom-right cell, or -1 if it cannot be done.
//
// The one idea: one queue entry is {row, col, obstacles used so far, steps so far}, and the queue
// always hands out the entry with the smallest "steps so far + distance still to go", where the
// distance still to go is the up/down/left/right distance to the corner ignoring obstacles (hd).
// That guess never overstates the real remaining distance, and it drops by at most 1 for every
// step taken, so the first time the corner is handed out, its steps are the fewest. The pair
// (cell, obstacles used) is marked as seen when it is HANDED OUT, not when it is queued: an entry
// queued early can still be beaten by a shorter way to the same pair, and only the entry handed
// out first for a pair is the shortest one.
//
// Time: O(n * m * k * log(n * m * k)) -- n * m * (k + 1) pairs, each handed out once but queued
// up to 4 times, each queue operation O(log of the queue size).
// Space: O(n * m * k) -- the visited table and the queue.
//
// Siblings: ShortestPathWithObstaclesElimination takes the entries batch by batch from a plain
// queue; every step costs 1, so it needs no ordering and no distance guess.

public final class ShortestPathWithObstaclesEliminationPQ {

	private static final int[] dir = { -1, 0, 1, 0, -1 };

	public static int shortestPath(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[3] + hd(a[0], a[1], n, m)));
		pq.offer(new int[] { 0, 0, 0, 0 }); // START: {row, col, obstacles used, steps}
		boolean[][][] visited = new boolean[n][m][k + 1];
		while (!pq.isEmpty()) {
			// the inner loop is not a level: a priority queue has no levels. It stops early or late
			// without effect, because the outer loop starts it again until the queue is empty.
			for (int size = 0; size < pq.size(); size++) {
				int[] p = pq.poll(); // TAKE: the smallest steps + distance still to go
				int x = p[0], y = p[1], obstacles = p[2], dist = p[3];
				if (visited[x][y][obstacles]) {
					continue; // SKIP: this (cell, used) pair was handed out before, with fewer steps
				}
				visited[x][y][obstacles] = true; // RECORD: mark when handed out
				if (x == n - 1 && y == m - 1) {
					return dist; // RETURN: the first time the corner is handed out, its steps are the fewest
				}
				for (int d = 0; d < 4; d++) {
					int row = x + dir[d];
					int col = y + dir[d + 1];
					if (!valid(grid, row, col)) {
						continue; // SKIP: off the grid
					}
					int eliminations = obstacles + grid[row][col]; // COUNT: stepping on a 1 spends one
					if (eliminations > k || visited[row][col][eliminations]) {
						continue; // SKIP: over budget, or this pair is already settled
					}
					pq.offer(new int[] { row, col, eliminations, dist + 1 });
				}
			}
		}
		return -1; // RETURN: the corner cannot be reached within k eliminations
	}

	private static int hd(int x, int y, int n, int m) {
		return (n - 1 - x) + (m - 1 - y); // GUESS: steps still needed if there were no obstacles
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
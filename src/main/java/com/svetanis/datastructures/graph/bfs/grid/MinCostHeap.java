package com.svetanis.datastructures.graph.bfs.grid;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1368. Minimum Cost to Make at Least One Valid Path in a Grid
//
// Input: a grid where each cell holds a sign: 1 right, 2 left, 3 down, 4 up. You start at the
// top-left cell and want the bottom-right cell, always moving the way the sign of the cell you
// stand on points. You may change the sign of any cell once, at a cost of 1 each. On LeetCode:
// 1 <= rows, cols <= 100, 1 <= grid[i][j] <= 4.
// Returns the smallest total cost of changes that gives a path from the top-left to the
// bottom-right cell.
//
// The one idea: a move that follows the sign of the cell you stand on costs 0, any other move
// costs 1 -- so this is a shortest path with costs on the moves, and Dijkstra answers it: a heap
// hands out the smallest total first. dirs is listed in sign order, so direction k is the one
// sign k + 1 points. dist[r][c] holds the fewest changes found so far on the way to a cell, and a
// cell is placed again only when a move gives it a smaller total. The start costs 0: a cost is
// paid when a move leaves a cell, and nothing has moved yet. "Once" is never checked: the
// cheapest path never visits a cell twice, so it changes each sign at most once.
//
// Time: O(m * n * log(m * n)) -- each placement is a heap operation.
// Space: O(m * n) -- the dist table and the heap.
// Known limit: Dijkstra needs every move to cost 0 or more; with a negative cost two signs that
// point at each other become a loop that lowers the total for ever.
//
// Siblings: MinCost keeps the same order with a deque (free moves to the front, paid ones to the
// back), which drops the log factor because every move costs 0 or 1.

public final class MinCostHeap {

	public static int minCost(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		int[][] dist = new int[m][n]; // DIST: fewest changes to reach (r, c), found so far
		for (int[] row : dist) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		// SIGN ORDER: dirs[k] is where sign k + 1 points -- 1 right, 2 left, 3 down, 4 up
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		// HEAP: entries {row, col, total}, smallest total first
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
		heap.offer(new int[] { 0, 0, 0 }); // START: nothing changed yet -- 0, whatever (0,0)'s sign
		dist[0][0] = 0;
		while (!heap.isEmpty()) {
			int[] cell = heap.poll(); // NEXT: the smallest total waiting
			int x = cell[0], y = cell[1];
			for (int k = 0; k < dirs.length; k++) {
				int r = x + dirs[k][0], c = y + dirs[k][1];
				if (r < 0 || r >= m || c < 0 || c >= n) {
					continue; // INSIDE: skip a move off the grid
				}
				// COST: 0 if the move follows the sign of the cell stood ON, 1 if it changes it
				int cost = k + 1 == grid[x][y] ? 0 : 1;
				int total = dist[x][y] + cost; // TOTAL: the total if this move is made
				if (total < dist[r][c]) { // BETTER: only an improvement is placed
					dist[r][c] = total;
					heap.offer(new int[] { r, c, total });
				}
			}
		}
		return dist[m - 1][n - 1];
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 1, 1, 1, 1 }, { 2, 2, 2, 2 } };
		System.out.println(minCost(g1)); // 3

		int[][] g2 = { { 1, 1, 3 }, { 3, 2, 2 }, { 1, 1, 4 } };
		System.out.println(minCost(g2)); // 0

		int[][] g3 = { { 1, 2 }, { 4, 3 } };
		System.out.println(minCost(g3)); // 1

		int[][] g4 = { { 3 }, { 1 } };
		System.out.println(minCost(g4)); // 0 -- the start is not charged for its own sign
	}
}

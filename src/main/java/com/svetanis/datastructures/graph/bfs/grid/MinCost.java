package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1368. Minimum Cost to Make at Least One Valid Path in a Grid
//
// Input: a grid where each cell holds a sign: 1 right, 2 left, 3 down, 4 up. You start at the
// top-left cell and want the bottom-right cell, always moving the way the sign of the cell you
// stand on points. You may change the sign of any cell once, at a cost of 1 each. On LeetCode:
// 1 <= rows, cols <= 100, 1 <= grid[i][j] <= 4.
// Returns the smallest total cost of changes that gives a path from the top-left to the
// bottom-right cell.
//
// The one idea: the price is on the move, not on the cell entered. From a cell there are four
// moves: the one its own sign points is free, each of the other three costs 1 (it means changing
// that sign). dirs is listed in sign order, so direction k is the one sign k + 1 points. A free
// move goes to the FRONT of the deque and a cost-1 move to the BACK, so the deque stays in order
// of total, smallest at the front -- Dijkstra's order with no heap. One deque entry is
// {row, col, total}. Every move is placed; the first time a cell comes out its total is final, so
// it is marked then and later copies are skipped; the first time the corner comes out, its total
// is the answer. "Once" is never checked: the cheapest path never visits a cell twice (cutting
// out the loop between two visits costs nothing), so it changes each sign at most once.
//
// Time: O(m * n) -- each cell is expanded once (4 moves); at most 4 deque entries per cell.
// Space: O(m * n) -- the visited table and the deque.
//
// Siblings: MinCostHeap is the same search with a heap ordered by total and a dist table -- one
// fewer decision, a log factor slower. MinObstacleRemovalToReachCorner2290 is the same
// free-front / paid-back deque with the price on entering an obstacle instead of on leaving
// against a sign.

public final class MinCost {

	public static int minCost(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		// SIGN ORDER: dirs[k] is where sign k + 1 points -- 1 right, 2 left, 3 down, 4 up
		int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		boolean[][] visited = new boolean[m][n]; // TAKEN: true once a cell has come out
		Deque<int[]> deque = new ArrayDeque<>(); // ENTRY: {row, col, total changes so far}
		deque.offerFirst(new int[] { 0, 0, 0 }); // START: nothing changed yet
		while (!deque.isEmpty()) {
			int[] cell = deque.pollFirst(); // NEXT: the smallest total waiting
			int x = cell[0], y = cell[1], total = cell[2];
			if (x == m - 1 && y == n - 1) {
				return total; // RETURN: the first time the corner comes out, its total is final
			}
			if (visited[x][y]) {
				continue; // SKIP: this cell already came out with a total no larger
			}
			visited[x][y] = true; // MARK: when it comes out, not when it is placed
			for (int k = 0; k < dirs.length; k++) {
				int r = x + dirs[k][0], c = y + dirs[k][1];
				if (r < 0 || r >= m || c < 0 || c >= n) {
					continue; // INSIDE: skip a move off the grid
				}
				if (k + 1 == grid[x][y]) {
					deque.offerFirst(new int[] { r, c, total }); // FREE: follows the sign, front
				} else {
					deque.offerLast(new int[] { r, c, total + 1 }); // PAID: changes the sign, back
				}
			}
		}
		return -1; // RETURN: not reached -- every cell can be entered after enough changes
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 1, 1, 1, 1 }, { 2, 2, 2, 2 } };
		System.out.println(minCost(g1)); // 3

		int[][] g2 = { { 1, 1, 3 }, { 3, 2, 2 }, { 1, 1, 4 } };
		System.out.println(minCost(g2)); // 0

		int[][] g3 = { { 1, 2 }, { 4, 3 } };
		System.out.println(minCost(g3)); // 1
	}
}

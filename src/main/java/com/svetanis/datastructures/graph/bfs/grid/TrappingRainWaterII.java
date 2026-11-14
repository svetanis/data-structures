package com.svetanis.datastructures.graph.bfs.grid;

import static java.util.Comparator.comparingInt;

import java.util.PriorityQueue;

// 407. Trapping Rain Water II
//
// Input: an m x n grid of heights, each cell a block of that height. Rain falls on all of it; water
// cannot run off the outer edge of the grid unless a cell is lower than what holds it in, and it
// spreads up, down, left and right. On LeetCode: 1 <= m, n <= 200, 0 <= heights[i][j] <= 2 * 10^4.
// Returns the total volume of water that stays trapped.
//
// The one idea: one heap entry is {level, row, col}: a cell already known to be reachable from the
// outside, and the water level around it -- the height the water must rise to before it can get
// out through this cell (the taller of the cell and the level that brought us here). Start with the
// whole border in the heap, at its own heights: water at a border cell runs off at that height.
// Always take the entry with the LOWEST level. Its unvisited neighbours can only be reached by water
// that has risen to at least that level, so a neighbour lower than the level holds
// (level - its height) of water, and a neighbour higher than the level holds none. The neighbour
// goes into the heap at the larger of the two. Taking the lowest level first is what makes each
// cell's level final the first time it is reached: the weakest point of the wall is always
// settled before anything behind it.
//
// Time: O(n * m * log(n * m)) -- each cell goes into the heap once, at O(log(n * m)).
// Space: O(n * m) -- the visited table and the heap.
//
// Siblings: none -- this is the only file for this problem.

public final class TrappingRainWaterII {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, 1, 0, -1 };

	public static int trw(int[][] heights) {
		int n = heights.length;
		int m = heights[0].length;
		boolean[][] visited = new boolean[n][m];
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[0]));
		init(heights, visited, pq); // SEED: every border cell at its own height
		int total = 0;
		while (!pq.isEmpty()) {
			int[] current = pq.poll(); // TAKE: the lowest level on the edge of the known area
			int h = current[0];
			int x = current[1];
			int y = current[2];
			for (int k = 0; k < dx.length; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				if (row >= 0 && row < n && col >= 0 && col < m && !visited[row][col]) { // SKIP: off the grid or settled
					int diff = h - heights[row][col];
					total += Math.max(0, diff); // COUNT: water held above this cell, none if it is higher
					visited[row][col] = true; // RECORD: mark when queued; its level is final
					int max = Math.max(h, heights[row][col]); // LEVEL: the taller of the water and the cell
					pq.offer(new int[] { max, row, col });
				}
			}
		}
		return total;
	}

	private static void init(int[][] heights, boolean[][] visited, PriorityQueue<int[]> pq) {
		int n = heights.length;
		int m = heights[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i == 0 || i == n - 1 || j == 0 || j == m - 1) { // BORDER: water here runs off
					pq.offer(new int[] { heights[i][j], i, j });
					visited[i][j] = true;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[][] heights1 = { { 1, 4, 3, 1, 3, 2 }, { 3, 2, 1, 3, 2, 4 }, { 2, 3, 3, 2, 3, 1 } };
		System.out.println(trw(heights1)); // 4

		int[][] heights2 = { { 3, 3, 3, 3, 3 }, { 3, 2, 2, 2, 3 }, { 3, 2, 1, 2, 3 }, { 3, 2, 2, 2, 3 },
				{ 3, 3, 3, 3, 3 } };
		System.out.println(trw(heights2)); // 10
	}
}

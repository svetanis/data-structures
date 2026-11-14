package com.svetanis.datastructures.graph.islands;

import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicInteger;

// Largest region of 1s in a Boolean matrix -- not a LeetCode problem (LC 695 Max Area of
// Island is the same with sides only).
//
// Input: g -- a matrix of 0 and 1. Two filled cells (non-zero) are connected when they are next
// to each other horizontally, vertically or diagonally; a group of connected filled cells is a
// region. Returns the number of cells in the largest region, 0 when no cell is filled.
//
// The one idea: one call of dfs stands for one filled cell the walk has just reached; it marks the
// cell visited and tries all eight neighbours. A neighbour that is inside the matrix, filled and
// not yet visited is another cell of the same region, so it is counted and walked into. The count
// is bumped just before the call, and the neighbour is marked at the top of that call, so no cell
// is counted twice. The outer loop starts one walk at every filled cell no earlier walk reached;
// each walk measures one whole region and the biggest is kept.
//
// Known limit: the walk is one call deeper per cell of the region, so a filled 200 x 200 matrix
// overflows a default JVM stack (100 x 100 is fine).
//
// Time: O(ROW x COL) -- each cell is visited once and looks at eight neighbours.
// Space: O(ROW x COL) -- the visited matrix, and a recursion as deep as the region is large.
//
// Siblings: MaxLenRegionIterative is the same walk with a stack of cells, for very large regions.

public final class MaxLenRegion {

	// horizontal + vertical moves
	// private static int[] dx = { -1, 0, 0, 1 };
	// private static int[] dy = { 0, -1, 1, 0 };

	// horizontal + vertical + diagonal moves
	private static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int maxLen(int[][] g) {
		int row = g.length;
		int col = g[0].length;
		int max = 0;

		boolean[][] visited = new boolean[row][col];

		// traverse through all cells of given matrix
		// if a cell with value 1 is not visited yet,
		// then new island found.
		// visit all cells in this island
		// and increment island count
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (g[i][j] != 0 && !visited[i][j]) { // WALK: a filled cell no earlier walk reached
					AtomicInteger count = new AtomicInteger(1); // this cell
					dfs(g, i, j, visited, count);
					max = max(max, count.get()); // RECORD: keep the biggest region
				}
			}
		}
		return max; // RETURN
	}

	private static void dfs(int[][] g, int row, int col, boolean[][] visited,
			AtomicInteger count) {
		// mark this cell as visited
		visited[row][col] = true; // MARK
		// considers only the neighbors as adjacent vertices
		// recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int nextRow = row + dx[k];
			int nextCol = col + dy[k];
			if (isSafe(g, nextRow, nextCol, visited)) {
				count.getAndIncrement(); // TAKE: one more cell of the region
				dfs(g, nextRow, nextCol, visited, count); // WALK
			}
		}
	}

	private static boolean isSafe(int[][] g, int row, int col, boolean[][] visited) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != 0 && !visited[row][col];
	}

	public static void main(String[] args) {
		int[][] g = { //
				{ 0, 0, 1, 1, 0 }, //
				{ 1, 0, 1, 1, 0 }, //
				{ 0, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 1 } };//
		System.out.println(maxLen(g)); // 6

		int[][] g1 = { //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 1, 0, 0, 1 }, //
				{ 1, 0, 0, 1, 1 }, //
				{ 0, 0, 0, 0, 0 }, //
				{ 1, 0, 1, 0, 1 } };//
		System.out.println(maxLen(g1)); // 4

		int[][] g2 = { //
				{ 1, 1, 1, 1, 0 }, //
				{ 1, 1, 0, 1, 0 }, //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0 } };//
		System.out.println(maxLen(g2)); // 9

		int[][] g3 = { //
				{ 1, 1, 0, 0, 0 }, //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 0, 1, 0, 0 }, //
				{ 0, 0, 0, 1, 1 } };//
		System.out.println(maxLen(g3)); // 7

		int[][] g4 = { { 0, 0 }, { 0, 0 } };
		System.out.println(maxLen(g4)); // 0
	}
}

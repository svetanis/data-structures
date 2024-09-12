package com.svetanis.datastructures.graph.dfs;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicInteger;

// given a matrix with rows and columns
// where each cell contains either 0 or 1
// two cells are connected if they are 
// adjacent to each other horizontally,
// vertically or diagonally. if one or more
// filled cells are connected, they form 
// a region. find the length of the 
// largest region

public final class MaxLenRegion {

	// horizontal + vertical moves
	// private static int[] dx = { -1, 0, 0, 1 };
	// private static int[] dy = { 0, -1, 1, 0 };

	// horizontal + vertical + diagonal moves
	private static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int maxLen(int[][] g) {
		// Time complexity: O(ROW x COL)

		int row = g.length;
		int col = g[0].length;
		int max = MIN_VALUE;

		boolean[][] visited = new boolean[row][col];

		// traverse through all cells of given matrix
		// if a cell with value 1 is not visited yet,
		// then new island found.
		// visit all cells in this island
		// and increment island count
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (g[i][j] != 0 && !visited[i][j]) {
					AtomicInteger count = new AtomicInteger(1);
					dfs(g, i, j, visited, count);
					max = max(max, count.get());
				}
			}
		}
		return max;
	}

	private static void dfs(int[][] g, int row, int col, boolean[][] visited, 
			AtomicInteger count) {
		// mark this cell as visited
		visited[row][col] = true;
		// considers only the neighbors as adjacent vertices
		// recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int nextRow = row + dx[k];
			int nextCol = col + dy[k];
			if (isSafe(g, nextRow, nextCol, visited)) {
				count.getAndIncrement();
				dfs(g, nextRow, nextCol, visited, count);
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
	}
}

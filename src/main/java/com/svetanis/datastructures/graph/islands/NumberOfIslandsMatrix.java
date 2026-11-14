package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands
//
// Input: g -- an m x n map held as an int[][], 1 for land and 0 for water. An island is a group of
// land cells joined by sides (up, down, left or right), and everything off the edge of the grid
// is water. Returns the number of islands. LeetCode gives the grid as a char[][] of '1' and '0';
// see NumberOfIslandsMatrixSubmit for that form. On LeetCode: 1 <= m, n <= 300.
//
// The one idea: sweep the grid cell by cell. A land cell that no earlier walk has marked is the
// first cell of an island nobody has counted, so the count goes up by one and a depth-first walk
// marks every land cell joined to it, so the same island is never counted again. visited[r][c]
// says whether a walk has already been to cell (r, c).
//
// Time: O(r * c) -- every cell is looked at once by the sweep, and the walks enter each land cell
// once between them.
// Space: O(r * c) -- the boolean[r][c] visited array, and separately the recursion depth: one
// island can snake through every cell, so the stack is r * c frames deep in the worst case, not
// r + c.
//
// Known limit: the recursion overflows the default Java stack on a very large island. LC allows
// 300 x 300, i.e. 90,000 frames, and a grid of all land that size throws StackOverflowError. It is
// left as it is; NumberOfIslandsBfsSubmit does the same count on a char[][] with a queue, so it
// has no such limit.
//
// Siblings: NumberOfIslandsMatrixSubmit is the same walk on LeetCode's char[][]; NumberOfIslandsDfs
// is the same walk on a List grid.

public final class NumberOfIslandsMatrix {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(int[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		boolean[][] visited = new boolean[n][m];
		// traverse through all cells of given matrix
		// if a cell with value 1 is not visited yet,
		// then new island found.
		// visit all cells in this island
		// and increment island count
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] != 0 && !visited[r][c]) {
					dfs(g, r, c, visited); // WALK: mark the whole island
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	private static void dfs(int[][] g, int row, int col, boolean[][] visited) {
		// mark this cell as visited
		visited[row][col] = true;
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isLand(g, x, y) && !visited[x][y]) {
				dfs(g, x, y, visited);
			}
		}
	}

	private static boolean isLand(int[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != 0;
	}

	public static void main(String[] args) {
		int[][] g1 = { //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 1, 0, 0, 1 }, //
				{ 1, 0, 0, 1, 1 }, //
				{ 0, 0, 0, 0, 0 }, //
				{ 1, 0, 1, 0, 1 } };//
		System.out.println(count(g1)); // 6

		int[][] g2 = { //
				{ 1, 1, 1, 1, 0 }, //
				{ 1, 1, 0, 1, 0 }, //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0 } };//
		System.out.println(count(g2)); // 1

		int[][] g3 = { //
				{ 1, 1, 0, 0, 0 }, //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 0, 1, 0, 0 }, //
				{ 0, 0, 0, 1, 1 } };//
		System.out.println(count(g3)); // 3
	}
}

package com.svetanis.datastructures.graph.dfs;

// given an n x m 2D binary grid 
// which represents a map of 1s (land)
// and 0s (water), return the number
// of islands

// an island is surrounded by water 
// and is formed by connecting adjacent
// lands horizontally or vertically.
// all four edges of the grid are 
// surrounded by water

public final class NumberOfIslands {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(int[][] g) {
		// Time complexity: O(ROW x COL)

		int count = 0;
		int row = g.length;
		int col = g[0].length;

		boolean[][] visited = new boolean[row][col];

		// traverse through all cells of given matrix
		// if a cell with value 1 is not visited yet,
		// then new island found.
		// visit all cells in this island
		// and increment island count
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (g[i][j] != 0 && !visited[i][j]) {
					dfs(g, i, j, visited);
					count++;
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
			if (isSafe(g, x, y, visited)) {
				dfs(g, x, y, visited);
			}
		}
	}

	private static boolean isSafe(int[][] g, int row, int col, boolean[][] visited) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == 1 && !visited[row][col];
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

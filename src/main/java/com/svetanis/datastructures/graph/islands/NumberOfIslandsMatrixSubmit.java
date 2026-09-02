package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands 

public final class NumberOfIslandsMatrixSubmit {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c) -- the boolean[r][c] visited array, and
	// separately the recursion depth: one island can snake through every
	// cell. LC 200 allows 300x300, i.e. 90,000 stack frames, and a fully
	// land grid runs out of default stack long before that: 100x100 (10,000
	// cells) throws StackOverflowError on a cold JVM, and 133x133 (17,689)
	// is the ceiling once this method has been JIT-compiled. the threshold
	// moves because a compiled frame is smaller than an interpreted one --
	// there is no single number here, only an order of magnitude, and it is
	// 5x short. NumberOfIslandsBfsSubmit has no such ceiling.

	private static final char WATER = '0';

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(char[][] g) {
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
				if (g[r][c] != WATER && !visited[r][c]) {
					dfs(g, r, c, visited);
					count++;
				}
			}
		}
		return count;
	}

	private static void dfs(char[][] g, int row, int col, boolean[][] visited) {
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

	private static boolean isLand(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != WATER;
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '1', '0', '0', '1' }, //
				{ '1', '0', '0', '1', '1' }, //
				{ '0', '0', '0', '0', '0' }, //
				{ '1', '0', '1', '0', '1' } };//
		System.out.println(count(g1)); // 6

		char[][] g2 = { //
				{ '1', '1', '1', '1', '0' }, //
				{ '1', '1', '0', '1', '0' }, //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '0', '0', '0', '0' } };//
		System.out.println(count(g2)); // 1

		char[][] g3 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '0', '1', '0', '0' }, //
				{ '0', '0', '0', '1', '1' } };//
		System.out.println(count(g3)); // 3
	}
}

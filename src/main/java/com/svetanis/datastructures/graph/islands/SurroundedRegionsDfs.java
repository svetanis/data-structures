package com.svetanis.datastructures.graph.islands;

// 130. Surrounded Regions
//
// Input: g -- an m x n board of 'X' and 'O'. A region is a group of 'O' cells joined by sides (up,
// down, left or right); it is surrounded if none of its cells lies on the edge of the board.
// Changes the board in place: every 'O' of every surrounded region becomes 'X', and the other 'O'
// cells stay. Returns nothing. The method is called closedIslands, after the sibling problem
// NumberOfClosedIslands, but it solves this one. On LeetCode: 1 <= m, n <= 200, every cell is
// 'X' or 'O'.
//
// The one idea: find the 'O' cells that are NOT surrounded and keep them. An 'O' is safe exactly
// when it is joined, through 'O' cells, to an 'O' on the edge of the board. So walk from every
// 'O' on the edge and write '.' over every cell reached: '.' means "safe". Every 'O' still on the
// board afterwards is joined to no edge 'O', so it is turned to 'X', and the '.' cells go back to
// 'O'.
//
// Time: O(r * c) -- each cell is walked at most once, and the final pass looks at each cell once.
// Space: O(r * c) -- the recursion depth: a safe region can snake through every cell.
//
// Known limit: the recursion overflows the default Java stack on a very large region. LC allows
// 200 x 200, and a board of all 'O' that size throws StackOverflowError. It is left as it is;
// SurroundedRegionsBfs is the same algorithm with a queue in place of the recursion.
//
// Siblings: SurroundedRegionsBfs.

public final class SurroundedRegionsDfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static void closedIslands(char[][] g) {
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (boarder(g, r, c) && g[r][c] == 'O') {
					dfs(g, r, c); // WALK: mark everything joined to this edge 'O' as safe
				}
			}
		}
		flip(g);
	}

	private static void flip(char[][] g) {
		int n = g.length;
		int m = g[0].length;
		// flip all remaining 'O' to x and back all '.' to 'O'
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == '.') {
					g[i][j] = 'O'; // RESTORE: a safe cell
				} else if (g[i][j] == 'O') {
					g[i][j] = 'X'; // CAPTURE: nothing joined it to the edge
				}
			}
		}
	}

	private static void dfs(char[][] g, int row, int col) {
		g[row][col] = '.'; // MARK: safe
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y)) {
				dfs(g, x, y);
			}
		}
	}

	private static boolean boarder(char[][] g, int row, int col) {
		boolean one = row == 0 || row == g.length - 1;
		boolean two = col == 0 || col == g[0].length - 1;
		return one || two;
	}

	private static boolean safe(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == 'O';
	}

	private static void print(char[][] g) {
		for (char[] row : g) {
			StringBuilder sb = new StringBuilder();
			for (char ch : row) {
				sb.append(ch).append(' ');
			}
			System.out.println(sb);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ 'X', 'X', 'X', 'X' }, //
				{ 'X', 'O', 'O', 'X' }, //
				{ 'X', 'X', 'O', 'X' }, //
				{ 'X', 'O', 'X', 'X' } };//
		closedIslands(g1);
		print(g1); // rows: X X X X / X X X X / X X X X / X O X X

		char[][] g2 = { { 'X' } };//
		closedIslands(g2);
		print(g2); // rows: X
	}
}

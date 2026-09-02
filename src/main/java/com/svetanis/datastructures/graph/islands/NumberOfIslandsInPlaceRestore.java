package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands

// no visited array and no damage to the caller's grid.
// a walked cell is written with a THIRD value, SEEN,
// which is neither land nor water. one sweep at the end
// puts every SEEN cell back to land.

// the third value is what makes this work. overwriting
// land with water, as NumberOfIslandsInPlaceGuarded does,
// loses the difference between "water all along" and
// "land I have already counted", so there is nothing left
// to restore from.

public final class NumberOfIslandsInPlaceRestore {
	// Time complexity: O(r * c) -- two sweeps of the grid, not one
	// Space Complexity: O(r * c) -- the recursion depth alone, one frame per
	// land cell. restoring the grid costs a second pass, not extra memory.

	private static final char LAND = '1';
	private static final char SEEN = '2';

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int count(char[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] == LAND) {
					mark(g, r, c);
					count++;
				}
			}
		}
		// AFTER the count, never inside the walk. a mark put
		// back on the way out of a call is a mark the sweep
		// walks into again, and the same island then gets
		// counted once per cell -- 9 instead of 1 on a solid
		// 3x3 grid
		restore(g);
		return count;
	}

	private static void mark(char[][] g, int row, int col) {
		if (!isLand(g, row, col)) {
			return;
		}
		g[row][col] = SEEN;
		for (int k = 0; k < dx.length; ++k) {
			mark(g, row + dx[k], col + dy[k]);
		}
	}

	private static void restore(char[][] g) {
		for (int r = 0; r < g.length; r++) {
			for (int c = 0; c < g[0].length; c++) {
				if (g[r][c] == SEEN) {
					g[r][c] = LAND;
				}
			}
		}
	}

	private static boolean isLand(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == LAND;
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '1', '0', '0', '1' }, //
				{ '1', '0', '0', '1', '1' }, //
				{ '0', '0', '0', '0', '0' }, //
				{ '1', '0', '1', '0', '1' } };//
		System.out.println(count(g1)); // 6
		System.out.println(count(g1)); // 6 again -- the grid survived

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

		char[][] g4 = { //
				{ '1', '1', '1' }, //
				{ '1', '1', '1' }, //
				{ '1', '1', '1' } };//
		System.out.println(count(g4)); // 1
		System.out.println(g4[0][0]); // 1 -- the grid came back unchanged
	}
}

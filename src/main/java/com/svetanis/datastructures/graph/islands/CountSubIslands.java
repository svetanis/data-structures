package com.svetanis.datastructures.graph.islands;

// 1905. Count Sub Islands
//
// Input: grid1 and grid2 -- two m x n grids of 0 (water) and 1 (land). An island is a group of
// land cells joined by sides (up, down, left, right). Returns how many islands of grid2 are a
// sub-island: every cell of the island is also land in grid1. On LeetCode: m == grid1.length ==
// grid2.length, n == grid1[i].length == grid2[i].length, 1 <= m, n <= 500, every cell is 0 or 1.
//
// The one idea: isSubIsland(row, col) stands for one land cell of grid2 and answers "is every
// cell of the island this cell belongs to also land in grid1?". The cell is good if grid1 has
// land there; the answer is also false if any neighbouring cell of the same island answers
// false. The walk sets each visited grid2 cell to 0 so no cell is looked at twice, and it never
// stops early on a false: the whole island must be wiped to 0, or its remaining cells would be
// found later and counted as a second island. grid2 is changed by the call.
//
// Known limit: the walk goes one call deeper per cell of the island, so a 500 x 500 island of
// land runs far past the depth a default JVM stack holds (a 100 x 100 one is fine). The code
// stays recursive; LeetCode's own stack is larger.
//
// Time: O(rows * cols) -- each land cell of grid2 is visited once and looks at four sides.
// Space: O(rows * cols) -- the recursion, one call per cell of the island in the worst case.
//
// Siblings: CountSubIslandsIterative is the same walk with a stack of cells, for very large
// islands.

public final class CountSubIslands {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int[][] g1;
	private int[][] g2;

	public int countSubIslands(int[][] grid1, int[][] grid2) {
		this.g1 = grid1;
		this.g2 = grid2;
		this.rows = grid1.length;
		this.cols = grid1[0].length;
		int count = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (g2[row][col] == 1 && isSubIsland(row, col)) { // WALK: a new island of grid2
					count++; // TAKE: the whole island is on land of grid1
				}
			}
		}
		return count;
	}

	private boolean isSubIsland(int row, int col) {
		boolean subIsland = g1[row][col] == 1; // this cell needs land in grid1
		g2[row][col] = 0; // MARK: visited
		for (int k = 0; k < 4; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (valid(x, y) && g2[x][y] == 1 && !isSubIsland(x, y)) { // WALK: always finish the island
				subIsland = false; // SKIP: one cell off grid1's land spoils the island
			}
		}
		return subIsland;
	}

	private boolean valid(int x, int y) {
		return x >= 0 && x < rows && y >= 0 && y < cols;
	}

	public static void main(String[] args) {
		CountSubIslands csi = new CountSubIslands();
		int[][] g1 = { { 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 1, 1, 0, 1, 1 } };
		int[][] g2 = { { 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1 }, { 0, 1, 0, 0, 0 }, { 1, 0, 1, 1, 0 }, { 0, 1, 0, 1, 0 } };
		System.out.println(csi.countSubIslands(g1, g2)); // 3

		int[][] g3 = { { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 } };
		int[][] g4 = { { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0 }, { 1, 0, 0, 0, 1 } };
		System.out.println(csi.countSubIslands(g3, g4)); // 2
	}
}

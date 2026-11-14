package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 1905. Count Sub Islands
//
// Input: grid1 and grid2 -- two m x n grids of 0 (water) and 1 (land). An island is a group of
// land cells joined by sides (up, down, left, right). Returns how many islands of grid2 are a
// sub-island: every cell of the island is also land in grid1. On LeetCode: m == grid1.length ==
// grid2.length, n == grid1[i].length == grid2[i].length, 1 <= m, n <= 500, every cell is 0 or 1.
//
// The one idea: the same walk as CountSubIslands, with a stack of cells in place of the
// recursion. One stack entry is one land cell of grid2 that belongs to the island being walked
// and whose neighbours are not looked at yet. A cell is set to 0 in grid2 when it is pushed, so
// no cell is pushed twice. The island is a sub-island only if every cell taken off the stack is
// land in grid1; one that is not spoils the island, but the walk still runs until the stack is
// empty, so the whole island is wiped to 0 and none of it is found again as a second island.
// grid2 is changed by the call.
//
// Time: O(rows * cols) -- each land cell of grid2 is pushed once and looks at four sides.
// Space: O(rows * cols) -- the stack, up to one entry per cell of the island.
//
// Siblings: CountSubIslands is the recursive walk, which overflows a default Java stack on a very
// large island.

public final class CountSubIslandsIterative {

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
		boolean subIsland = true;
		Deque<int[]> stack = new ArrayDeque<>();
		g2[row][col] = 0; // MARK: visited when pushed
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop(); // TAKE: one cell of the island
			if (g1[cell[0]][cell[1]] != 1) {
				subIsland = false; // SKIP: one cell off grid1's land spoils the island; keep walking
			}
			for (int k = 0; k < 4; k++) {
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (valid(x, y) && g2[x][y] == 1) {
					g2[x][y] = 0; // MARK
					stack.push(new int[] { x, y }); // WALK: same island
				}
			}
		}
		return subIsland; // RETURN: true only if no cell was off grid1's land
	}

	private boolean valid(int x, int y) {
		return x >= 0 && x < rows && y >= 0 && y < cols;
	}

	public static void main(String[] args) {
		CountSubIslandsIterative csi = new CountSubIslandsIterative();
		int[][] g1 = { { 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 1, 1, 0, 1, 1 } };
		int[][] g2 = { { 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1 }, { 0, 1, 0, 0, 0 }, { 1, 0, 1, 1, 0 }, { 0, 1, 0, 1, 0 } };
		System.out.println(csi.countSubIslands(g1, g2)); // 3

		int[][] g3 = { { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 } };
		int[][] g4 = { { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0 }, { 1, 0, 0, 0, 1 } };
		System.out.println(csi.countSubIslands(g3, g4)); // 2
	}
}

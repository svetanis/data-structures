package com.svetanis.datastructures.graph.unionfind;

// 1970. Last Day Where You Can Still Cross
//
// Input: row, col and cells, where cells[i] = [r, c] (1-based) is the cell that floods with water
// on day i + 1. On day 0 the whole row x col grid is land. On LeetCode: 2 <= row, col <= 2 * 10^4,
// 4 <= row * col <= 2 * 10^4, cells.length == row * col, all cells distinct.
// Returns the last day on which you can still walk from the top row to the bottom row over land
// cells, moving up, down, left or right.
//
// The one idea: run time BACKWARDS. After the last day every cell is water; add the land back one
// cell at a time, starting with cells[last]. When cells[day] has just been added, the land is
// exactly the cells that are still dry on day `day` (cells[day..], since cells[0..day-1] flooded
// on days 1..day). Two virtual nodes stand for "above the top row" and "below the bottom row":
// a land cell in row 0 is joined to top, one in the last row to bottom, and every new land cell
// is joined to its land neighbours. Union-find keeps the groups. The first time top and bottom
// share a group, a walk exists on day `day`, and since days are tried from the latest, that is
// the last such day. Walking the days forward would need a fresh search each day.
//
// Time: O(R * C * log(R * C)) -- one cell added per step, each with up to four unions and two
// finds; paths are compressed but trees are not balanced, so a find is O(log) amortised.
// Space: O(R * C) -- the parent array (R * C + 2 nodes) and the land grid.

public final class LastDayToCross {

	private final int[][] directions = new int[][] { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };

	private int rows;
	private int cols;
	private int[] parent; // CARDS: parent[i] -- the node above node i; nodes R * C and R * C + 1 are top and bottom
	private boolean[][] grid; // LAND: grid[r][c] is true once that cell has been added back

	public int lastDay(int row, int col, int[][] cells) {
		int n = row * col;
		init(row, col, n);
		int top = n, bottom = n + 1;
		for (int day = cells.length - 1; day >= 0; day--) { // BACKWARDS: add the land back, latest flood first
			int r = cells[day][0] - 1;
			int c = cells[day][1] - 1;
			grid[r][c] = true; // ADD: this cell is dry on day `day`
			int start = index(r, c);
			for (int[] direction : directions) {
				int x = r + direction[0];
				int y = c + direction[1];
				if (safe(x, y)) { // LINK: to each land neighbour
					union(start, index(x, y));
				}
			}
			if (r == 0) {
				union(start, top); // TOP: a land cell in the first row touches the top
			}
			if (r == rows - 1) {
				union(start, bottom); // BOTTOM: a land cell in the last row touches the bottom
			}
			if (find(top) == find(bottom)) {
				return day; // RETURN: a walk exists on this day, and later days were tried first
			}
		}
		return 0; // not reached: with every cell back, the top and bottom rows are joined
	}

	private void init(int row, int col, int n) {
		this.rows = row;
		this.cols = col;
		this.parent = new int[n + 2];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}
		this.grid = new boolean[row][col];
	}

	private int index(int r, int c) {
		return r * cols + c;
	}

	private boolean safe(int r, int c) {
		boolean row = r >= 0 && r < rows;
		boolean col = c >= 0 && c < cols;
		return row && col && grid[r][c];
	}

	private void union(int x, int y) {
		parent[find(x)] = find(y); // JOIN
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y; // COMPRESS
		}
		return y;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } };
		LastDayToCross ldc = new LastDayToCross();
		System.out.println(ldc.lastDay(2, 2, g1)); // 2

		int[][] g2 = { { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		System.out.println(ldc.lastDay(2, 2, g2)); // 1

		int[][] g3 = { { 1, 2 }, { 2, 1 }, { 3, 3 }, { 2, 2 }, { 1, 1 }, { 1, 3 }, { 2, 3 }, { 3, 2 }, { 3, 1 } };
		System.out.println(ldc.lastDay(3, 3, g3)); // 3
	}
}

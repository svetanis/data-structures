package com.svetanis.datastructures.graph.floodfill;

import java.util.Arrays;

// 1034. Coloring A Border
//
// Input: grid -- an m x n grid of colours; (row, col) -- one cell; color -- a new colour. The
// connected component of (row, col) is every cell reachable from it by steps up, down, left and
// right over cells of the same colour as grid[row][col]. A cell of the component is on its
// border when it is on the edge of the grid or a side of it touches a cell of another colour.
// Returns the grid with the border of that component painted color. On LeetCode: 1 <= m, n <= 50,
// 1 <= grid[i][j], color <= 1000, (row, col) is inside the grid.
//
// The one idea: one call of dfs stands for one cell of the component. Its job is to look at the
// four sides of that cell: a side that leaves the grid, or lands on a cell of another colour,
// makes the cell a border cell; a side that lands on an unvisited cell of the same colour is
// another cell of the component, so the walk goes there. Painting happens after the cell's loop,
// never before: a neighbour that has not been visited yet still compares its colour with the
// original, and a painted cell is never compared again because visited[][] already skips it.
// Painting with the colour it already has changes nothing, so color equal to the old colour is
// safe.
//
// Time: O(rows * cols) -- each cell is visited once and looks at four sides.
// Space: O(rows * cols) -- the visited grid, and the walk can be as deep as the component is
// large (a default JVM stack holds a few thousand nested calls; 50 x 50 cells is fine).

public final class ColoringBorder {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int color;
	private int[][] grid;
	private boolean[][] visited;

	public int[][] colorBorder(int[][] grid, int row, int col, int color) {
		this.color = color;
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.grid = grid;
		this.visited = new boolean[rows][cols];
		dfs(row, col, grid[row][col]);
		return grid;
	}

	private void dfs(int row, int col, int original) {
		visited[row][col] = true; // MARK: this cell is part of the component
		boolean boarder = !isSafe(row, col);
		for (int k = 0; k < 4; k++) { // WALK: the four sides
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(x, y)) {
				if (!visited[x][y]) {
					if (grid[x][y] == original) {
						dfs(x, y, original); // WALK: same colour -- the next cell of the component
					} else {
						boarder = true; // TAKE: a side touching another colour
					}
				}
			} else {
				boarder = true; // TAKE: a side leaving the grid
			}
		}
		if (boarder) {
			grid[row][col] = color; // RECORD: paint after the loop, so neighbours still see the old colour
		}
	}

	private boolean isSafe(int row, int col) {
		boolean one = row >= 0 && row < rows; // row number is in range
		boolean two = col >= 0 && col < cols; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		ColoringBorder ups = new ColoringBorder();
		int[][] g = { { 1, 1 }, { 1, 2 } };
		System.out.println(Arrays.deepToString(ups.colorBorder(g, 0, 0, 3))); // [[3, 3], [3, 2]]

		ColoringBorder ups2 = new ColoringBorder();
		int[][] g1 = { //
				{ 1, 2, 2 }, //
				{ 2, 3, 2 } };//
		System.out.println(Arrays.deepToString(ups2.colorBorder(g1, 0, 1, 3))); // [[1, 3, 3], [2, 3, 3]]

		int[][] g2 = { //
				{ 1, 1, 1 }, //
				{ 1, 1, 1 }, //
				{ 1, 1, 1 } };//
		System.out.println(Arrays.deepToString(ups.colorBorder(g2, 1, 1, 2))); // [[2, 2, 2], [2, 1, 2], [2, 2, 2]]
	}
}

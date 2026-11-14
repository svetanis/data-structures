package com.svetanis.datastructures.graph.islands;

// 463. Island Perimeter
//
// Input: g -- a grid of 1 (land) and 0 (water), cells joined by sides only (up, down, left,
// right). Returns the length of the edge of the island: the number of cell sides that have
// land on one side and water, or the outside of the grid, on the other. On LeetCode:
// 1 <= row, col <= 100, every cell is 0 or 1, there is exactly one island and it has no lakes.
//
// The one idea: one land cell has four sides, so it brings 4 to the perimeter on its own. Two land
// cells that touch share a side, and that side is no longer an edge of the island, so it takes
// 1 away from each of the two cells: 2 in all. Looking only down and right from each cell meets
// every touching pair exactly once, so no pair is subtracted twice. No walk is needed; the grid
// is read cell by cell.
//
// Time: O(rows * cols) -- each cell is read once and looks at two neighbours.
// Space: O(1) -- a few counters.

public final class IslandPerimeter {

	public static int perimeter(int[][] g) {
		int perimeter = 0;
		int rows = g.length;
		int cols = g[0].length;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (g[i][j] == 1) {
					perimeter += 4; // TAKE: all four sides of a land cell
					if (i < rows - 1 && g[i + 1][j] == 1) {
						perimeter -= 2; // SKIP: the side shared with the land cell below
					}
					if (j < cols - 1 && g[i][j + 1] == 1) {
						perimeter -= 2; // SKIP: the side shared with the land cell to the right
					}
				}
			}
		}
		return perimeter; // RETURN
	}

	public static void main(String[] args) {
		int[][] g = { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } };
		System.out.println(perimeter(g)); // 16

		int[][] g2 = { { 1 } };
		System.out.println(perimeter(g2)); // 4

		int[][] g3 = { { 1, 0 } };
		System.out.println(perimeter(g3)); // 4

		int[][] g4 = { { 1, 1 }, { 1, 1 } };
		System.out.println(perimeter(g4)); // 8
	}
}

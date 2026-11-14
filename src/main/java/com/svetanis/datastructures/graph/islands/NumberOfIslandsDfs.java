package com.svetanis.datastructures.graph.islands;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// 200. Number of Islands
//
// Input: grid -- an m x n map held as a List of Lists of Integers, 1 for land and 0 for water. An
// island is a group of land cells joined by sides (up, down, left or right), and everything off
// the edge of the grid is water. Returns the number of islands. LeetCode gives the grid as a
// char[][] of '1' and '0'; see NumberOfIslandsMatrixSubmit for that form. On LeetCode:
// 1 <= m, n <= 300.
//
// The one idea: sweep the grid cell by cell. A land cell that no earlier walk has marked is the
// first cell of an island nobody has counted, so the count goes up by one and a depth-first walk
// marks every land cell joined to it, so the same island is never counted again. visited[r][c]
// says whether a walk has already been to cell (r, c).
//
// Time: O(r * c) -- every cell is looked at once by the sweep, and the walks enter each land cell
// once between them.
// Space: O(r * c) -- the boolean[r][c] visited array, and separately the recursion depth: one
// island can snake through every cell, so the stack can be r * c frames deep.
//
// Known limit: the recursion overflows the default Java stack on a very large island. LC allows
// 300 x 300, i.e. 90,000 frames, and a grid of all land that size throws StackOverflowError. It is
// left as it is; NumberOfIslandsBfs and NumberOfIslandsSubmit take the same List grid and use a
// queue, so they have no such limit.
//
// Siblings: NumberOfIslandsMatrix is the same walk on an int[][]; NumberOfIslandsMatrixSubmit on
// LeetCode's char[][].

public final class NumberOfIslandsDfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(List<List<Integer>> grid) {
		int count = 0;
		int n = grid.size();
		int m = grid.get(0).size();
		boolean[][] visited = new boolean[n][m];
		// traverse through all cells of given matrix
		// if a cell with value 1 is not visited yet,
		// then new island found.
		// visit all cells in this island
		// and increment island count
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				int curr = grid.get(r).get(c);
				if (curr != 0 && !visited[r][c]) {
					dfs(grid, r, c, visited); // WALK: mark the whole island
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	private static void dfs(List<List<Integer>> grid, int row, int col, boolean[][] visited) {
		// mark this cell as visited
		visited[row][col] = true;
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = row + dx[dist];
			int y = col + dy[dist];
			if (isLand(grid, x, y) && !visited[x][y]) {
				dfs(grid, x, y, visited);
			}
		}
	}

	private static boolean isLand(List<List<Integer>> grid, int row, int col) {
		boolean one = row >= 0 && row < grid.size(); // row number is in range
		boolean two = col >= 0 && col < grid.get(0).size(); // col number is in range
		return one && two && grid.get(row).get(col) != 0;
	}

	public static void main(String[] args) {
		List<List<Integer>> grid1 = new ArrayList<>();
		grid1.add(asList(1, 1, 0, 0, 0));
		grid1.add(asList(0, 1, 0, 0, 1));
		grid1.add(asList(1, 0, 0, 1, 1));
		grid1.add(asList(0, 0, 0, 0, 0));
		grid1.add(asList(1, 0, 1, 0, 1));
		System.out.println(count(grid1)); // 6

		List<List<Integer>> grid2 = new ArrayList<>();
		grid2.add(asList(1, 1, 1, 1, 0));
		grid2.add(asList(1, 1, 0, 1, 0));
		grid2.add(asList(1, 1, 0, 0, 0));
		grid2.add(asList(0, 0, 0, 0, 0));
		System.out.println(count(grid2)); // 1

		List<List<Integer>> grid3 = new ArrayList<>();
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(0, 0, 1, 0, 0));
		grid3.add(asList(0, 0, 0, 1, 1));
		System.out.println(count(grid3)); // 3

		List<List<Integer>> grid4 = new ArrayList<>();
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(1, 1, 1, 1, 0, 0));
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(0, 1, 0, 0, 0, 0));
		grid4.add(asList(0, 0, 0, 0, 1, 0));
		grid4.add(asList(0, 0, 0, 0, 0, 0));
		System.out.println(count(grid4)); // 2
	}
}

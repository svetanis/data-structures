package com.svetanis.datastructures.graph.islands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Black Shapes -- not a LeetCode problem
//
// Input: g -- a grid of characters in which 'O' is a white cell and any other character, here 'X',
// is a black cell. A shape is a group of black cells joined by sides (up, down, left or right).
// Returns how many shapes the grid holds. White is the LETTER O, not the digit zero: with zeros
// written for white, every cell counts as black and the whole grid is one shape. No size limit is
// given for this problem.
//
// The one idea: sweep the grid cell by cell. A black cell that no earlier walk has marked is the
// first cell of a shape nobody has counted, so the count goes up by one and a depth-first walk
// marks every black cell joined to it, so the same shape is never counted again. visited[r][c]
// says whether the walk has already been to cell (r, c).
//
// Time: O(r * c) -- every cell is looked at once by the sweep, and the walks enter each black cell
// once between them.
// Space: O(r * c) -- the visited array, and separately the recursion depth: one shape can snake
// through every cell, so the stack can be r * c frames deep.
//
// Known limit: the recursion overflows the default Java stack on a very large shape, for example
// a solid 300 x 300 block of black cells. It is left as it is; BlackShapesIterative is the same
// algorithm with an explicit stack.
//
// Siblings: BlackShapesIterative.

public final class BlackShapes {

	private static final char WHITE = 'O';
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int count(char[][] g) {
		int count = 0;
		int row = g.length;
		int col = g[0].length;

		boolean[][] visited = new boolean[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (g[i][j] != WHITE && !visited[i][j]) { // an uncounted shape starts here
					dfs(g, i, j, visited);
					++count; // TAKE: one more shape
				}
			}
		}
		return count;
	}

	private static void dfs(char[][] g, int row, int col, boolean[][] visited) {
		// MARK: this cell is part of the shape being walked
		visited[row][col] = true;
		// WALK: considers only the neighbors as adjacent vertices
		// and recurs for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(g, x, y, visited)) {
				dfs(g, x, y, visited);
			}
		}
	}

	private static boolean isSafe(char[][] g, int r, int c, boolean[][] visited) {
		int n = g.length;
		int m = g[0].length;
		boolean one = r >= 0 && r < n; // row number is in range
		boolean two = c >= 0 && c < m; // col number is in range
		return one && two && g[r][c] != WHITE && !visited[r][c];
	}

	private static char[][] toMatrix(List<String> list) {
		int n = list.size();
		int m = list.get(0).length();
		char[][] chars = new char[n][m];
		for (int i = 0; i < n; i++) {
			String str = list.get(i);
			for (int j = 0; j < m; j++) {
				chars[i][j] = str.charAt(j); // BUILD: one character per cell
			}
		}
		return chars;
	}

	public static void main(String[] args) {
		char[][] g = { //
				{ 'O', 'O', 'O', 'X', 'O', 'O', 'O' }, //
				{ 'O', 'O', 'X', 'X', 'O', 'X', 'O' }, //
				{ 'O', 'X', 'O', 'O', 'O', 'X', 'O' } };//
		System.out.println(count(g)); // 3

		List<String> list = new ArrayList<>(Arrays.asList("OOOXOOO", "OOXXOXO", "OXOOOXO"));
		char[][] m = toMatrix(list);
		System.out.println(count(m)); // 3

		char[][] digits = { //
				{ '0', '0', '0', 'X', '0', '0', '0' } };//
		System.out.println(count(digits)); // 1 -- the digit is not white
	}
}

package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// Black Shapes -- not a LeetCode problem
//
// Input: g -- a grid of characters in which 'O' is a white cell and any other character, here 'X',
// is a black cell. A shape is a group of black cells joined by sides (up, down, left or right).
// Returns how many shapes the grid holds. White is the LETTER O, not the digit zero. No size limit
// is given for this problem.
//
// The one idea: the same sweep as BlackShapes, with a stack of cells in place of the recursion.
// One stack entry is one black cell of the shape being walked whose neighbours have not been
// looked at yet. visited[r][c] is set when a cell is pushed, not when it is popped, so no cell is
// ever on the stack twice. The sweep counts a shape when it meets an unvisited black cell; the
// walk never counts.
//
// Time: O(r * c) -- every black cell is pushed once and looks at its four sides once.
// Space: O(r * c) -- the visited array, and the stack, which holds at most one entry per black
// cell.
//
// Siblings: BlackShapes is the recursive walk, which overflows a default Java stack on a very
// large shape.

public final class BlackShapesIterative {

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
					walk(g, i, j, visited);
					++count; // TAKE: one more shape
				}
			}
		}
		return count;
	}

	private static void walk(char[][] g, int row, int col, boolean[][] visited) {
		Deque<int[]> stack = new ArrayDeque<>();
		visited[row][col] = true; // MARK: when pushed, so no cell is pushed twice
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			for (int k = 0; k < dx.length; ++k) { // WALK: the four sides of this cell
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (isSafe(g, x, y, visited)) {
					visited[x][y] = true;
					stack.push(new int[] { x, y });
				}
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

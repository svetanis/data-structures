package com.svetanis.datastructures.graph.islands;

import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Deque;

// Largest region of 1s in a Boolean matrix -- not a LeetCode problem (LC 695 Max Area of
// Island is the same with sides only).
//
// Input: g -- a matrix of 0 and 1. Two filled cells (non-zero) are connected when they are next
// to each other horizontally, vertically or diagonally; a group of connected filled cells is a
// region. Returns the number of cells in the largest region, 0 when no cell is filled.
//
// The one idea: the same walk as MaxLenRegion, with a stack of cells in place of the recursion.
// One stack entry is one filled cell of the region being measured whose eight neighbours are not
// looked at yet. A cell is marked visited when it is pushed and counted when it is popped, so no
// cell is counted twice. The outer loop starts one walk at every filled cell no earlier walk
// reached; each walk measures one whole region and the biggest is kept.
//
// Time: O(ROW x COL) -- each cell is pushed at most once and looks at eight neighbours.
// Space: O(ROW x COL) -- the visited matrix and the stack.
//
// Siblings: MaxLenRegion is the recursive walk, which overflows a default Java stack on a very
// large region.

public final class MaxLenRegionIterative {

	// horizontal + vertical + diagonal moves
	private static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int maxLen(int[][] g) {
		int row = g.length;
		int col = g[0].length;
		int max = 0;
		boolean[][] visited = new boolean[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (g[i][j] != 0 && !visited[i][j]) { // WALK: a filled cell no earlier walk reached
					max = max(max, size(g, i, j, visited)); // RECORD: keep the biggest region
				}
			}
		}
		return max; // RETURN
	}

	private static int size(int[][] g, int row, int col, boolean[][] visited) {
		Deque<int[]> stack = new ArrayDeque<>();
		visited[row][col] = true; // MARK: when pushed
		stack.push(new int[] { row, col });
		int count = 0;
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			count++; // TAKE: one more cell of the region
			for (int k = 0; k < dx.length; ++k) {
				int nextRow = cell[0] + dx[k];
				int nextCol = cell[1] + dy[k];
				if (isSafe(g, nextRow, nextCol, visited)) {
					visited[nextRow][nextCol] = true; // MARK
					stack.push(new int[] { nextRow, nextCol }); // WALK: same region
				}
			}
		}
		return count;
	}

	private static boolean isSafe(int[][] g, int row, int col, boolean[][] visited) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != 0 && !visited[row][col];
	}

	public static void main(String[] args) {
		int[][] g = { //
				{ 0, 0, 1, 1, 0 }, //
				{ 1, 0, 1, 1, 0 }, //
				{ 0, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 1 } };//
		System.out.println(maxLen(g)); // 6

		int[][] g1 = { //
				{ 1, 1, 0, 0, 0 }, //
				{ 0, 1, 0, 0, 1 }, //
				{ 1, 0, 0, 1, 1 }, //
				{ 0, 0, 0, 0, 0 }, //
				{ 1, 0, 1, 0, 1 } };//
		System.out.println(maxLen(g1)); // 4

		int[][] g4 = { { 0, 0 }, { 0, 0 } };
		System.out.println(maxLen(g4)); // 0
	}
}

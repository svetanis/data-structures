package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 490. The Maze
//
// Input: a grid g of 0 (empty) and 1 (wall), a start cell src and a destination cell dst, both
// empty and different. A ball placed on src rolls in one of the four directions and does not stop
// until it hits a wall or the edge of the grid; only then may it be rolled again. On LeetCode:
// 1 <= rows, cols <= 100.
// Returns true if the ball can come to rest exactly on dst. Passing over dst while rolling does
// not count.
//
// The one idea: one queue entry is a place where the ball has come to REST, not any cell it
// passed over. From a resting place the ball has at most four moves, one per direction, and each
// move ends at the next resting place in that direction. So the search is over resting places,
// and a roll is just the edge between two of them. A resting place is queued once, when first
// found, so every roll is tried from each place at most once.
//
// Time: O(n * m * (n + m)) -- at most n * m resting places, each tries 4 rolls, and a roll crosses
// at most max(n, m) cells.
// Space: O(n * m) -- the visited table and the queue.
//
// Siblings: MazeDfs solves the same problem depth first, with a recursion instead of a queue.

public final class MazeBfs {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static boolean hasPath(int[][] g, int[] src, int[] dst) {
		int rows = g.length;
		int cols = g[0].length;
		boolean[][] visited = new boolean[rows][cols];
		// RECORD: mark as visited
		visited[src[0]][src[1]] = true;
		Deque<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		while (!queue.isEmpty()) {
			int[] position = queue.poll(); // TAKE: a resting place
			int x = position[0];
			int y = position[1];
			if (x == dst[0] && y == dst[1]) {
				return true; // RETURN: the ball rests on dst
			}
			for (int k = 0; k < dx.length; k++) {
				int row = x;
				int col = y;
				while (safe(g, row, col)) { // ROLL: keep going until the next cell is a wall or off the grid
					row += dx[k];
					col += dy[k];
				}
				row -= dx[k]; // BACK: undo the step that left the free cells
				col -= dy[k];
				if (!visited[row][col]) {
					visited[row][col] = true; // RECORD: mark when queued
					queue.offer(new int[] { row, col });
				}
			}
		}
		return false; // RETURN: every resting place tried, dst is not one of them
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0; // SKIP: off the grid or a wall
	}

	public static void main(String[] args) {
		int[][] m1 = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } };
		System.out.println(hasPath(m1, new int[] { 3, 1 }, new int[] { 3, 3 })); // true

		int[][] m2 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(hasPath(m2, new int[] { 0, 4 }, new int[] { 4, 4 })); // true
		System.out.println(hasPath(m2, new int[] { 0, 4 }, new int[] { 3, 2 })); // false

		int[][] m3 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0 } };
		System.out.println(hasPath(m3, new int[] { 4, 3 }, new int[] { 0, 1 })); // false
	}
}

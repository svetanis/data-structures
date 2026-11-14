package com.svetanis.datastructures.graph.bfs.grid;

// 490. The Maze
//
// Input: a grid of 0 (empty) and 1 (wall), a start cell src and a destination cell dst, both
// empty and different. A ball placed on src rolls in one of the four directions and does not stop
// until it hits a wall or the edge of the grid; only then may it be rolled again. On LeetCode:
// 1 <= rows, cols <= 100.
// Returns true if the ball can come to rest exactly on dst. Passing over dst while rolling does
// not count.
//
// The one idea: one call of dfs is one place where the ball has come to REST, not any cell it
// passed over. From a resting place the ball has at most four moves, one per direction, and each
// move ends at the next resting place in that direction, which is the next call. A resting place
// is marked on its first visit, so no place is explored twice and the search ends. The first
// branch that reaches dst makes every call above it return true at once.
//
// Time: O(n * m * (n + m)) -- at most n * m resting places, each tries 4 rolls, and a roll crosses
// at most max(n, m) cells.
// Space: O(n * m) -- the visited table; the recursion can be as deep as the number of resting
// places.
//
// Siblings: MazeBfs solves the same problem breadth first, with a queue instead of a recursion.

public final class MazeDfs {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	private int rows;
	private int cols;
	private int[][] g;
	private int[] dst;
	private boolean[][] visited;

	public boolean hasPath(int[][] maze, int[] src, int[] dst) {
		this.g = maze;
		this.dst = dst;
		this.rows = g.length;
		this.cols = g[0].length;
		this.visited = new boolean[rows][cols];
		return dfs(src);
	}

	private boolean dfs(int[] curr) {
		int x = curr[0], y = curr[1];
		if (visited[x][y]) {
			return false; // SKIP: this resting place was explored already
		}
		if (x == dst[0] && y == dst[1]) {
			return true; // RETURN: the ball rests on dst
		}
		visited[x][y] = true; // RECORD: mark on entry
		for (int k = 0; k < 4; k++) {
			int row = x, col = y;
			// ROLL: move ball in the chosen dir until it can't
			while (safe(row, col)) {
				row += dx[k];
				col += dy[k];
			}
			// BACK: revert last move; that cell is where the ball rests
			if (dfs(new int[] { row - dx[k], col - dy[k] })) {
				return true;
			}
		}
		return false; // RETURN: no direction led to dst
	}

	private boolean safe(int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0; // SKIP: off the grid or a wall
	}

	public static void main(String[] args) {
		MazeDfs mb = new MazeDfs();
		int[][] m1 = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } };
		System.out.println(mb.hasPath(m1, new int[] { 3, 1 }, new int[] { 3, 3 })); // true

		int[][] m2 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(mb.hasPath(m2, new int[] { 0, 4 }, new int[] { 4, 4 })); // true
		System.out.println(mb.hasPath(m2, new int[] { 0, 4 }, new int[] { 3, 2 })); // false

		int[][] m3 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0 } };
		System.out.println(mb.hasPath(m3, new int[] { 4, 3 }, new int[] { 0, 1 })); // false
	}
}

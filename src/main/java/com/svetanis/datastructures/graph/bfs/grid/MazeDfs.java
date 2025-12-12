package com.svetanis.datastructures.graph.bfs.grid;

// 490. The Maze

public final class MazeDfs {
	// Time & Space Complexity: O(n*m).

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
			return false;
		}
		if (x == dst[0] && y == dst[1]) {
			return true;
		}
		visited[x][y] = true;
		for (int k = 0; k < 4; k++) {
			int row = x, col = y;
			// move ball in the chosen dir until it can
			while (safe(row, col)) {
				row += dx[k];
				col += dy[k];
			}
			// revert last move
			if (dfs(new int[] { row - dx[k], col - dy[k] })) {
				return true;
			}
		}
		return false;
	}

	private boolean safe(int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0;
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

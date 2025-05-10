package com.svetanis.datastructures.graph.dfs;

// 980. Unique Paths III

public final class UniquePaths {
	// Time complexity: O(3^(ROW x COL))
	// Space complexity: O(ROW x COL)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int empty;
	private int[][] g;
	private boolean[][] visited;

	public int uniquePaths(int[][] g) {
		this.rows = g.length;
		this.cols = g[0].length;
		this.g = g;

		int[] start = init();
		int startX = start[0];
		int startY = start[1];
		this.visited = new boolean[rows][cols];
		visited[startX][startY] = true;
		return dfs(startX, startY, 0);
	}

	private int[] init() {
		int x = -1;
		int y = -1;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (g[i][j] == 0) {
					this.empty++;
				} else if (g[i][j] == 1) {
					x = i;
					y = j;
				}
			}
		}
		return new int[] { x, y };
	}

	private int dfs(int row, int col, int count) {
		if (g[row][col] == 2) {
			return count == empty + 1 ? 1 : 0;
		}
		int pathCount = 0;
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(x, y)) {
				visited[x][y] = true;
				pathCount += dfs(x, y, count + 1);
				// backtrack
				visited[x][y] = false;
			}
		}
		return pathCount;
	}

	private boolean isSafe(int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != -1 && !visited[row][col];
	}

	public static void main(String[] args) {
		UniquePaths ups = new UniquePaths();
		int[][] g = { //
				{ 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0 }, //
				{ 0, 0, 2, -1 } };//
		System.out.println(ups.uniquePaths(g)); // 2
		
		UniquePaths ups2 = new UniquePaths();
		int[][] g1 = { //
				{ 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0 }, //
				{ 0, 0, 0, 2 } };//
		System.out.println(ups2.uniquePaths(g1)); // 4

		int[][] g2 = { //
				{ 0, 1 }, //
				{ 2, 0 } };//
		System.out.println(ups.uniquePaths(g2)); // 0
	}
}

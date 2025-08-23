package com.svetanis.datastructures.graph.unionfind;

// 1970. Last Day Where You Can Still Cross

public final class LastDayToCross {

	private final int[][] directions = new int[][] { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };

	private int rows;
	private int cols;
	private int[] parent;
	private boolean[][] grid;

	public int lastDay(int row, int col, int[][] cells) {
		int n = row * col;
		init(row, col, n);
		int top = n, bottom = n + 1;
		for (int day = cells.length - 1; day >= 0; day--) {
			int r = cells[day][0] - 1;
			int c = cells[day][1] - 1;
			grid[r][c] = true;
			int start = index(r, c);
			for (int[] direction : directions) {
				int x = r + direction[0];
				int y = c + direction[1];
				if (safe(x, y)) {
					union(start, index(x, y));
				}
			}
			if (r == 0) {
				union(start, top);
			}
			if (r == rows - 1) {
				union(start, bottom);
			}
			if (find(top) == find(bottom)) {
				return day;
			}
		}
		return 0;
	}

	private void init(int row, int col, int n) {
		this.rows = row;
		this.cols = col;
		this.parent = new int[n + 2];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}
		this.grid = new boolean[row][col];
	}

	private int index(int r, int c) {
		return r * cols + c;
	}

	private boolean safe(int r, int c) {
		boolean row = r >= 0 && r < rows;
		boolean col = c >= 0 && c < cols;
		return row && col && grid[r][c];
	}

	private void union(int x, int y) {
		parent[find(x)] = find(y);
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y;
		}
		return y;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } };
		LastDayToCross ldc = new LastDayToCross();
		System.out.println(ldc.lastDay(2, 2, g1)); // 2

		int[][] g2 = { { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		System.out.println(ldc.lastDay(2, 2, g2)); // 1

		int[][] g3 = { { 1, 2 }, { 2, 1 }, { 3, 3 }, { 2, 2 }, { 1, 1 }, { 1, 3 }, { 2, 3 }, { 3, 2 }, { 3, 1 } };
		System.out.println(ldc.lastDay(3, 3, g3)); // 3
	}
}

package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 417. Pacific Atlantic Water Flow

public final class WaterFlowDfs {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private boolean[][] atlantic;
	private boolean[][] pacific;

	public List<List<Integer>> shortestPath(int[][] grid) {
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.atlantic = new boolean[rows][cols];
		this.pacific = new boolean[rows][cols];
		for (int i = 0; i < rows; i++) {
			dfs(grid, pacific, i, 0);
			dfs(grid, atlantic, i, cols - 1);
		}
		for (int j = 0; j < cols; j++) {
			dfs(grid, pacific, 0, j);
			dfs(grid, atlantic, rows - 1, j);
		}
		return reachable();
	}

	private List<List<Integer>> reachable() {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (atlantic[i][j] && pacific[i][j]) {
					list.add(Arrays.asList(i, j));
				}
			}
		}
		return list;
	}

	private void dfs(int[][] grid, boolean[][] reachable, int row, int col) {
		reachable[row][col] = true;
		for (int k = 0; k < 4; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (!valid(x, y) || reachable[x][y] || grid[x][y] < grid[row][col]) {
				continue;
			}
			dfs(grid, reachable, x, y);
		}
	}

	private boolean valid(int row, int col) {
		boolean isRow = row >= 0 && row < rows;
		boolean isCol = col >= 0 && col < cols;
		return isRow && isCol;
	}

	public static void main(String[] args) {
		int[][] grid = { //
				{ 1, 2, 2, 3, 5 }, //
				{ 3, 2, 3, 4, 4 }, //
				{ 2, 4, 5, 3, 1 }, //
				{ 6, 7, 1, 4, 5 }, //
				{ 5, 1, 1, 2, 4 } };//
		// [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
		WaterFlowDfs wf = new WaterFlowDfs();
		System.out.println(wf.shortestPath(grid));
	}
}

package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 417. Pacific Atlantic Water Flow
//
// Input: grid -- an m x n island where grid[r][c] is the height of cell (r, c). The Pacific
// Ocean touches the top and left edges, the Atlantic Ocean the bottom and right edges. Rain water
// flows from a cell to a neighbour (north, south, east, west) of the same height or lower, and
// from an edge cell into the ocean it borders. Returns every cell [r, c] from which water can
// reach both oceans, in row-major order. On LeetCode: 1 <= m, n <= 200, 0 <= grid[r][c] <= 10^5.
//
// The one idea: pacific[r][c] is true when water from cell (r, c) can run downhill into the
// Pacific, and atlantic[r][c] the same for the Atlantic. Testing every cell on its own repeats
// work, so run it backwards: from every cell touching one ocean, step UPHILL (to a neighbour of
// the same height or higher) as far as possible. Each cell reached can pour back down the same
// steps into that ocean, so it is marked. One climb per ocean; the cells marked in both grids
// are the answer.
//
// The recursion goes as deep as the longest climb. On a 200 x 200 island where every cell has
// the same height, the climb can be tens of thousands of cells deep and the default Java stack is
// not deep enough; WaterFlowBfs has no such limit.
//
// Time: O(m * n) -- a cell is marked once, and each call looks at 4 neighbours
// Space: O(m * n) -- the two boolean grids, and the recursion stack up to m * n deep
//
// Siblings: WaterFlowBfs is the same climb with a queue instead of recursion; WaterFlow keeps
// the visited cells in sets of Cell objects; WaterFlowSubmit is the LeetCode submission.
public final class WaterFlowDfs {

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
			dfs(grid, pacific, i, 0); // WALK: climb from the left column
			dfs(grid, atlantic, i, cols - 1); // WALK: climb from the right column
		}
		for (int j = 0; j < cols; j++) {
			dfs(grid, pacific, 0, j); // WALK: climb from the top row
			dfs(grid, atlantic, rows - 1, j); // WALK: climb from the bottom row
		}
		return reachable();
	}

	private List<List<Integer>> reachable() {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (atlantic[i][j] && pacific[i][j]) { // TAKE: both oceans reach it
					list.add(Arrays.asList(i, j));
				}
			}
		}
		return list;
	}

	private void dfs(int[][] grid, boolean[][] reachable, int row, int col) {
		reachable[row][col] = true; // RECORD: this ocean's water can be traced back to it
		for (int k = 0; k < 4; k++) { // WALK: 4 neighbours
			int x = row + dx[k];
			int y = col + dy[k];
			if (!valid(x, y) || reachable[x][y] || grid[x][y] < grid[row][col]) {
				continue; // SKIP: off the island, already marked, or downhill
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
		WaterFlowDfs wf = new WaterFlowDfs();
		System.out.println(wf.shortestPath(grid)); // [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]

		int[][] one = { { 1 } };
		System.out.println(wf.shortestPath(one)); // [[0, 0]]
	}
}

package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 417. Pacific Atlantic Water Flow

public final class WaterFlowBfs {
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
		Deque<int[]> pq = new ArrayDeque<>();
		Deque<int[]> aq = new ArrayDeque<>();
		init(grid, pq, aq);
		bfs(grid, pq, pacific);
		bfs(grid, aq, atlantic);
		return reachable();
	}

	private void init(int[][] grid, Deque<int[]> pq, Deque<int[]> aq) {
		for (int i = 0; i < rows; i++) {
			pq.offer(new int[] { i, 0 });
			aq.offer(new int[] { i, cols - 1 });
		}
		for (int j = 0; j < cols; j++) {
			pq.offer(new int[] { 0, j });
			aq.offer(new int[] { rows - 1, j });
		}
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

	private void bfs(int[][] grid, Deque<int[]> dq, boolean[][] reachable) {
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int row = cell[0];
			int col = cell[1];
			reachable[row][col] = true;
			for (int k = 0; k < 4; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				if (!valid(x, y) || reachable[x][y] || grid[x][y] < grid[row][col]) {
					continue;
				}
				dq.offer(new int[] { x, y });
			}
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
		WaterFlowBfs wf = new WaterFlowBfs();
		System.out.println(wf.shortestPath(grid));
	}
}

package com.svetanis.datastructures.graph.bfs.multisource;

import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Integer.MAX_VALUE;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

// 286. Walls and Gates

public final class WallsAndGates {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static int INF = MAX_VALUE;
	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	private int rows;
	private int cols;
	private int[][] grid;

	public void gateDists(int[][] grid) {
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.grid = grid;
		Queue<int[]> queue = init(grid);
		bfs(queue);
	}

	private Queue<int[]> init(int[][] grid) {
		Deque<int[]> dq = new ArrayDeque<>();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == 0) {
					dq.add(new int[] { r, c });
				}
			}
		}
		return dq;
	}

	private void bfs(Queue<int[]> queue) {
		while (!queue.isEmpty()) {
			int[] node = queue.poll();
			int val = grid[node[0]][node[1]];
			List<int[]> neighbors = neighbors(node);
			for (int[] next : neighbors) {
				int row = next[0];
				int col = next[1];
				if (grid[row][col] == INF) {
					grid[row][col] = 1 + val;
					queue.add(next);
				}
			}
		}
	}

	private List<int[]> neighbors(int[] node) {
		List<int[]> list = new ArrayList<>();
		for (int dist = 0; dist < dx.length; dist++) {
			int x = node[0] + dx[dist];
			int y = node[1] + dy[dist];
			if (valid(x, y)) {
				list.add(new int[] { x, y });
			}
		}
		return list;
	}

	private boolean valid(int row, int col) {
		boolean isRow = row >= 0 && row < rows;
		boolean isCol = col >= 0 && col < cols;
		return isRow && isCol && grid[row][col] != -1;
	}

	public static void main(String[] args) {
		WallsAndGates zid = new WallsAndGates();
		int[][] grid = { { INF, -1, 0, INF }, //
				{ INF, INF, INF, -1 }, //
				{ INF, -1, INF, -1 }, //
				{ 0, -1, INF, INF } };//
		zid.gateDists(grid);
		print(grid);
	}
}

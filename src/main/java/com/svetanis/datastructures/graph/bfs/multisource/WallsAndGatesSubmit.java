package com.svetanis.datastructures.graph.bfs.multisource;

import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;

// 286. Walls and Gates

public final class WallsAndGatesSubmit {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int INF = Integer.MAX_VALUE;
	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	public void gateDists(int[][] grid) {
		int rows = grid.length;
		if (rows == 0) {
			return;
		}
		int cols = grid[0].length;
		Deque<int[]> dq = new ArrayDeque<>();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == 0) {
					dq.add(new int[] { r, c });
				}
			}
		}
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int val = grid[node[0]][node[1]];
			for (int dist = 0; dist < dx.length; dist++) {
				int row = node[0] + dx[dist];
				int col = node[1] + dy[dist];
				if (row < 0 || col < 0 || row >= rows || col >= cols || grid[row][col] != INF) {
					continue;
				}
				grid[row][col] = 1 + val;
				dq.add(new int[] { row, col });
			}
		}
	}

	public static void main(String[] args) {
		WallsAndGatesSubmit zid = new WallsAndGatesSubmit();
		int[][] grid = { { INF, -1, 0, INF }, //
				{ INF, INF, INF, -1 }, //
				{ INF, -1, INF, -1 }, //
				{ 0, -1, INF, INF } };//
		zid.gateDists(grid);
		print(grid);
	}
}

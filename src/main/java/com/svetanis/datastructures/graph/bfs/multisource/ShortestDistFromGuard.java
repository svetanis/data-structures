package com.svetanis.datastructures.graph.bfs.multisource;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

public final class ShortestDistFromGuard {

	private static int dx[] = { -1, 0, 1, 0 };
	private static int dy[] = { 0, 1, 0, -1 };

	public static int[][] shortestDist(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[][] grid = initGrid(matrix);
		Queue<Cell> queue = initQueue(matrix);
		while (!queue.isEmpty()) {
			Cell node = queue.poll();

			for (int i = 0; i < dx.length; i++) {
				int x = node.getX() + dx[i];
				int y = node.getY() + dy[i];
				if (valid(x, y, n, m) && safe(matrix, grid, x, y)) {
					int dist = node.getDist() + 1;
					grid[x][y] = dist;
					queue.offer(new Cell(x, y, dist));
				}
			}
		}
		return grid;
	}

	private static boolean safe(char[][] matrix, int[][] grid, int r, int c) {
		if (matrix[r][c] != 'O' || grid[r][c] != -1) {
			return false;
		}
		return true;
	}

	private static boolean valid(int r, int c, int n, int m) {
		return r >= 0 && r < n && c >= 0 && c < m;
	}

	private static Queue<Cell> initQueue(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		Queue<Cell> queue = newLinkedList();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j] == 'G') {
					queue.offer(new Cell(i, j, 0));
				}
			}
		}
		return queue;
	}

	private static int[][] initGrid(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[][] grid = new int[n][m];
		for (int i = 0; i < n; i++) {
			fill(grid[i], -1);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j] == 'G') {
					grid[i][j] = 0;
				}
			}
		}
		return grid;
	}

	public static void main(String[] agrs) {
		char matrix[][] = { //
				{ 'O', 'O', 'O', 'O', 'G' }, //
				{ 'O', 'W', 'W', 'O', 'O' }, //
				{ 'O', 'O', 'O', 'W', 'O' }, //
				{ 'G', 'W', 'W', 'W', 'O' }, //
				{ 'O', 'O', 'O', 'O', 'G' } };//
		int[][] grid = shortestDist(matrix);
		print(grid);
	}
}

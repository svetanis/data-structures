package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1730. Shortest Path to Get Food

public final class ShortestPathToGetFood {

	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };
	private static final int[] dir = { -1, 0, 1, 0, -1 };

	public static int shortestPath(char[][] grid) {
		Deque<int[]> queue = init(grid);
		int steps = 0;
		while (!queue.isEmpty()) {
			++steps;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int[] p = queue.poll();
				for (int k = 0; k < 4; k++) {
					int x = p[0] + dir[k];
					int y = p[1] + dir[k + 1];
					if (valid(grid, x, y) && grid[x][y] != 'X') {
						if (grid[x][y] == '#') {
							return steps;
						}
						grid[x][y] = 'X';
						queue.offer(new int[] { x, y });
					}
				}
			}
		}
		return -1;
	}

	private static boolean valid(char[][] grid, int x, int y) {
		boolean row = x >= 0 && x < grid.length;
		boolean col = y >= 0 && y < grid[0].length;
		return row && col;
	}

	private static Deque<int[]> init(char[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		Deque<int[]> queue = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == '*') {
					queue.offer(new int[] { i, j });
					break;
				}
			}
		}
		return queue;
	}

	public static void main(String[] args) {
		char[][] grid = { //
				{ '*', 'O', 'O', 'X' }, //
				{ 'O', 'X', 'O', '#' }, //
				{ 'O', 'O', 'X', 'O' } };//
		System.out.println(shortestPath(grid)); // 4

		char[][] grid1 = { //
				{ 'X', 'X', 'X', 'X', 'X', 'X' }, //
				{ 'X', '*', 'O', 'O', 'O', 'X' }, //
				{ 'X', 'O', 'O', '#', 'O', 'X' }, //
				{ 'X', 'X', 'X', 'X', 'X', 'X' } };//
		System.out.println(shortestPath(grid1)); // 3

		char[][] grid2 = { //
				{ 'X', 'X', 'X', 'X', 'X' }, //
				{ 'X', '*', 'X', 'O', 'X' }, //
				{ 'X', 'O', 'X', '#', 'X' }, //
				{ 'X', 'X', 'X', 'X', 'X' } };//
		System.out.println(shortestPath(grid2)); // -1

	}
}
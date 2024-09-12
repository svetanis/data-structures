package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Integer.MAX_VALUE;

import java.awt.Point;
import java.util.Queue;

// given a binary matrix,
// find the distance of 
// nearest 1 for each 0
// in the matrix
// horizontal and vertical 
// moves are allowed

public final class NearestOneBinaryMatrix {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static int[][] nearestOne(int[][] grid) {
		int[][] dist = init(grid);
		bfs(grid, dist);
		return dist;
	}

	private static int[][] init(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int[][] dist = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dist[i][j] = MAX_VALUE;
				if (grid[i][j] == 1) {
					dist[i][j] = 0;
				}
			}
		}
		return dist;
	}

	private static void bfs(int[][] matrix, int[][] dist) {
		Queue<Point> queue = initQueue(dist);
		while (!queue.isEmpty()) {
			Point p = queue.poll();
			for (int i = 0; i < 4; i++) {
				int r = p.x + dx[i];
				int c = p.y + dy[i];
				if (isValid(dist, r, c) && dist[r][c] > dist[p.x][p.y] + 1) {
					dist[r][c] = dist[p.x][p.y] + 1;
					queue.offer(new Point(r, c));
				}
			}
		}
	}

	private static Queue<Point> initQueue(int[][] dist) {
		Queue<Point> queue = newLinkedList();
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[0].length; j++) {
				if (dist[i][j] == 0) {
					queue.offer(new Point(i, j));
				}
			}
		}
		return queue;
	}

	private static boolean isValid(int[][] dist, int row, int col) {
		boolean isRow = row >= 0 && row < dist.length;
		boolean isCol = col >= 0 && col < dist[0].length;
		return isRow && isCol;
	}

	public static void main(String[] args) {
		int[][] matrix = { //
				{ 0, 0, 0, 1 }, //
				{ 0, 0, 1, 1 }, //
				{ 0, 1, 1, 0 }, //
		};//
		print(nearestOne(matrix));
	}
}

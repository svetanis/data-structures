package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Deque;

// 909. Snakes and Ladders

public final class SnakeLadder909 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int shortestPath(int[][] board) {
		int n = board.length;
		boolean[] visited = new boolean[n * n + 1];
		visited[1] = true;
		Deque<Integer> queue = new ArrayDeque<>();
		queue.offer(1);
		int moves = 0;
		while (!queue.isEmpty()) {
			for (int i = queue.size(); i > 0; i--) {
				int curr = queue.poll();
				if (curr == n * n) {
					return moves;
				}
				for (int k = curr + 1; k <= Math.min(curr + 6, n * n); k++) {
					Point p = coordinates(k, n);
					int next = k;
					if (board[p.x][p.y] != -1) {
						next = board[p.x][p.y];
					}
					if (!visited[next]) {
						visited[next] = true;
						queue.offer(next);
					}
				}
			}
			moves++;
		}
		return -1;
	}

	private static Point coordinates(int num, int size) {
		int row = (num - 1) / size;
		int col = (num - 1) % size;
		if (row % 2 == 1) {
			col = size - 1 - col;
		}
		return new Point(size - 1 - row, col);
	}

	public static void main(String[] args) {
		int[][] board = { { -1, -1, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, 35, -1, -1, 13, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, 15, -1, -1, -1, -1 } };//
		System.out.println(shortestPath(board)); // 4
	}
	
	private static class Point{
		private int x;
		private int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

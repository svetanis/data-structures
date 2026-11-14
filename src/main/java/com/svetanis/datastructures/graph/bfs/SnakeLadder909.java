package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Deque;

// 909. Snakes and Ladders
//
// Input: board -- an n x n grid whose squares are numbered 1 to n * n in a zig-zag: start at the
// bottom-left corner, go right along the bottom row, go up one row, go left along that row, and so
// on. board[r][c] is -1 when the square has no snake or ladder, otherwise the number of the square
// it takes you to. You start on square 1; one move is a throw of 1 to 6 squares forward (never past
// n * n), and landing on a snake or ladder carries you along it at once, for one ride only.
// Returns the fewest moves to reach square n * n, or -1 if it cannot be reached. On LeetCode:
// 2 <= n <= 20, board[i][j] is -1 or in the range [1, n * n], and squares 1 and n * n have no snake
// or ladder.
//
// The one idea: one queue entry is a square number you can stand on after some number of moves (after
// any ride). From a square the next move reaches at most six squares, and every move costs one, so
// breadth first search, round by round, gives the fewest moves: round k holds the squares first
// reachable in exactly k moves, and the first round that holds square n * n is the answer. visited
// marks the square a move ENDS on, after the ride, so each square is queued once. The one fiddly
// part is coordinates(): it turns a square number into a board row and column, flipping the
// direction of every second row.
//
// Time: O(n^2) -- n^2 squares, each queued once and expanded into six throws.
// Space: O(n^2) -- visited and the queue.
//
// Siblings: SnakeLadder is the same game on a flat array of cells numbered from 0.

public final class SnakeLadder909 {

	public static int shortestPath(int[][] board) {
		int n = board.length;
		boolean[] visited = new boolean[n * n + 1]; // ENDED: squares a move has already ended on
		visited[1] = true;
		Deque<Integer> queue = new ArrayDeque<>();
		queue.offer(1); // START: square 1, no moves yet
		int moves = 0;
		while (!queue.isEmpty()) {
			for (int i = queue.size(); i > 0; i--) { // ROUND: every square reachable in exactly `moves` moves
				int curr = queue.poll(); // TAKE
				if (curr == n * n) {
					return moves; // RETURN: the first round to reach the last square
				}
				for (int k = curr + 1; k <= Math.min(curr + 6, n * n); k++) { // THROW: land on square k
					Point p = coordinates(k, n);
					int next = k;
					if (board[p.x][p.y] != -1) {
						next = board[p.x][p.y]; // RIDE: the snake or ladder carries you on
					}
					if (!visited[next]) {
						visited[next] = true;
						queue.offer(next);
					}
				}
			}
			moves++; // the next round is one move further
		}
		return -1; // the last square cannot be reached
	}

	private static Point coordinates(int num, int size) {
		int row = (num - 1) / size; // row counted from the bottom
		int col = (num - 1) % size;
		if (row % 2 == 1) {
			col = size - 1 - col; // every second row runs right to left
		}
		return new Point(size - 1 - row, col); // board row 0 is the top
	}

	public static void main(String[] args) {
		int[][] board = { { -1, -1, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, 35, -1, -1, 13, -1 }, //
				{ -1, -1, -1, -1, -1, -1 }, //
				{ -1, 15, -1, -1, -1, -1 } };//
		System.out.println(shortestPath(board)); // 4

		int[][] board2 = { { -1, -1 }, { -1, 3 } };
		System.out.println(shortestPath(board2)); // 1
	}

	private static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

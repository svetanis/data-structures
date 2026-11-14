package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 1926. Nearest Exit from Entrance in Maze
//
// Input: a grid g of '.' (empty cell) and '+' (wall), and an entrance cell, which is empty. In one
// step you move up, down, left or right onto an empty cell. An exit is an empty cell on the
// border of the grid, other than the entrance. On LeetCode: 1 <= rows, cols <= 100.
// Returns the fewest steps from the entrance to the nearest exit, or -1 if there is none. The
// grid is changed: every cell reached is overwritten with '+'.
//
// The one idea: one queue entry is a cell that has just been reached. All cells that are the same
// number of steps from the entrance wait in the queue together, so taking the queue out one batch
// at a time counts the steps: batch 1 is everything one step away, batch 2 two steps away, and so
// on. The first border cell found is therefore a nearest exit. A cell is turned into a wall the
// moment it is queued, which both marks it as seen and keeps the entrance itself from counting
// as an exit.
//
// Time: O(n * m) -- each cell is queued at most once, with 4 neighbours looked at.
// Space: O(n * m) -- the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class NearestExitInMaze {

	private static final char EMPTY = '.';
	private static final char WALL = '+';

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int shortestPath(char[][] g, int[] entrance) {
		int x = entrance[0];
		int y = entrance[1];
		Node src = new Node(x, y);
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(src);
		// RECORD: mark as visited
		g[x][y] = WALL;
		int steps = 0;
		while (!queue.isEmpty()) {
			steps++; // COUNT: every cell of this batch is one step further out
			for (int count = queue.size(); count > 0; count--) { // LEVEL: only the cells of this batch
				Node node = queue.poll(); // TAKE
				for (int k = 0; k < dx.length; k++) {
					int row = node.x + dx[k];
					int col = node.y + dy[k];
					if (safe(g, row, col)) {
						if (boundary(g, row, col)) {
							return steps; // RETURN: the first border cell found is nearest
						}
						queue.offer(new Node(row, col));
						g[row][col] = WALL; // RECORD: mark when queued
					}
				}
			}
		}
		return -1; // RETURN: every reachable cell tried, none on the border
	}

	private static boolean safe(char[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == EMPTY; // SKIP: off the grid, a wall, or seen
	}

	private static boolean boundary(char[][] g, int x, int y) {
		boolean row = x == 0 || x == g.length - 1;
		boolean col = y == 0 || y == g[0].length - 1;
		return row || col;
	}

	public static void main(String[] args) {
		int[] e1 = { 1, 2 };
		char[][] m1 = { { '+', '+', '.', '+' }, { '.', '.', '.', '+' }, { '+', '+', '+', '.' } };
		System.out.println(shortestPath(m1, e1)); // 1

		int[] e2 = { 1, 0 };
		char[][] m2 = { { '+', '+', '+' }, { '.', '.', '.' }, { '+', '+', '+' } };
		System.out.println(shortestPath(m2, e2)); // 2

		int[] e3 = { 0, 0 };
		char[][] m3 = { { '.', '+' } };
		System.out.println(shortestPath(m3, e3)); // -1

	}

	private static class Node {
		private int x;
		private int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}

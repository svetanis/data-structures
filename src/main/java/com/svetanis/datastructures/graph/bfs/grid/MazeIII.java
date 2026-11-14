package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// 499. The Maze III
//
// Input: a grid of 0 (empty) and 1 (wall), a ball cell and a hole cell, both empty and different.
// The ball rolls in one of the four directions ('u' up, 'd' down, 'l' left, 'r' right) until it
// hits a wall or the edge, but if it passes over the hole it falls in and stops there.
// On LeetCode: 1 <= rows, cols <= 100.
// Returns the instructions of the shortest route that drops the ball into the hole, as a string
// of direction letters; the length of a route is the number of cells the ball crosses (the start
// is not counted, the hole is). If several routes are equally short, the one that is smallest in
// alphabetical order. If the ball cannot fall into the hole, "impossible".
//
// The one idea: one queue entry is a place where the ball has come to REST (the hole counts as
// one), and for each such place two things are kept: dist, the fewest cells found so far to get
// there, and path, the letters of the best route found so far. A route replaces the stored one if
// it is shorter, or equally short and alphabetically smaller. That is enough to keep only one
// route per place: two equally short routes to a place end the same way, so the alphabetically
// smaller one stays smaller after any later letters are added. A place is queued again each time
// its stored route improves; the hole is never queued, because the ball stops there for good.
//
// Time: O(V * E) in the worst case, V = n * m resting places, E = 4 * V rolls, each roll crossing
// up to max(n, m) cells, and each comparison of two routes reading up to a route's length.
// Space: O(n * m * L) -- a route string for each place, L being the longest route.
//
// Siblings: MazeII is the same search without the hole and without route letters.

public final class MazeIII {

	private static final int INF = Integer.MAX_VALUE;
	private static final int[][] DIR = { { -1, 0, 'u' }, { 1, 0, 'd' }, { 0, -1, 'l' }, { 0, 1, 'r' } };

	public static String shortestPath(int[][] maze, int[] ball, int[] hole) {
		int n = maze.length;
		int m = maze[0].length;
		int srcX = ball[0];
		int srcY = ball[1];
		int dstX = hole[0];
		int dstY = hole[1];
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, INF); // BASE: nothing reached yet
		}
		dist[srcX][srcY] = 0; // BASE: the ball starts here at cost 0
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(ball);
		String[][] path = new String[n][m];
		path[srcX][srcY] = ""; // BASE: no letters yet

		while (!queue.isEmpty()) {
			int[] point = queue.poll(); // TAKE: a resting place
			int x = point[0];
			int y = point[1];
			for (int[] direction : DIR) {
				int dy = direction[0]; // the row step (named dy here, dx for the column step)
				int dx = direction[1];
				String dc = String.valueOf((char) direction[2]);
				int steps = dist[x][y];
				int row = x;
				int col = y;
				// ROLL: stop at a wall, an edge, or the moment the ball is on the hole
				while (safe(maze, row + dy, col + dx) && !(row == dstX && col == dstY)) {
					row += dy;
					col += dx;
					steps++;
				}
				boolean one = dist[row][col] > steps; // SHORTER
				boolean two = dist[row][col] == steps; // EQUAL
				boolean three = two && (path[x][y] + dc).compareTo(path[row][col]) < 0; // SMALLER letters
				if (one || three) {
					dist[row][col] = steps; // RECORD
					path[row][col] = path[x][y] + dc;
					if (row != dstX || col != dstY) {
						queue.offer(new int[] { row, col }); // QUEUE again; the hole is never rolled from
					}
				}
			}
		}
		return path[dstX][dstY] == null ? "impossible" : path[dstX][dstY]; // RETURN
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0; // SKIP: off the grid or a wall
	}

	public static void main(String[] args) {
		int[] src = { 1, 1 };
		int[] dst = { 3, 2 };
		int[][] m1 = { { 1, 1, 1, 1 }, { 1, 0, 0, 1 }, { 1, 0, 1, 1 }, { 1, 0, 0, 0 }, { 1, 1, 1, 1 } };
		System.out.println(shortestPath(m1, src, dst)); // dr

		int[][] m2 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0 } };
		System.out.println(shortestPath(m2, new int[] { 4, 3 }, new int[] { 0, 1 })); // lul
		System.out.println(shortestPath(m2, new int[] { 4, 3 }, new int[] { 3, 0 })); // impossible
	}
}

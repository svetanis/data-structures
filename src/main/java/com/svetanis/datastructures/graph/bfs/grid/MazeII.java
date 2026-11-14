package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 505. The Maze II
//
// Input: a grid g of 0 (empty) and 1 (wall), a start cell src and a destination cell dst, both
// empty and different. A ball rolls in one of the four directions and does not stop until it hits
// a wall or the edge of the grid. On LeetCode: 1 <= rows, cols <= 100.
// Returns the fewest cells the ball travels (the start is not counted, the stopping cell is) to
// come to rest exactly on dst, or -1 if it cannot.
//
// The one idea: one queue entry is a place where the ball has come to REST, and dist[x][y] is the
// shortest distance found so far to rest there. A roll costs the number of cells crossed, so the
// costs are not all equal and the first arrival is not always the shortest one. So a resting place
// is allowed back into the queue each time a shorter way to it is found, and each time it is taken
// out its four rolls are tried again with the better distance. When the queue runs dry no
// distance can be improved any more, so dist[dst] is the shortest.
//
// Time: O(V * E) in the worst case, V = n * m resting places, E = 4 * V rolls, each roll crossing
// up to max(n, m) cells -- a place is queued again every time its distance drops.
// Space: O(n * m) -- the distance table plus the queue.
//
// Siblings: MazeBfs / MazeDfs answer only whether dst is reachable (LC 490); MazeIII adds a hole
// that stops the ball and asks for the shortest route spelled in directions.

public final class MazeII {

	private static final int INF = 1 << 30;

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int shortestPath(int[][] g, int[] src, int[] dst) {
		int n = g.length;
		int m = g[0].length;
		int srcX = src[0];
		int srcY = src[1];
		int dstX = dst[0];
		int dstY = dst[1];
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, INF); // BASE: nothing reached yet
		}
		dist[srcX][srcY] = 0; // BASE: the ball starts here at cost 0
		Deque<int[]> queue = new ArrayDeque<>();
		queue.offer(src);
		while (!queue.isEmpty()) {
			int[] position = queue.poll(); // TAKE: a resting place
			int x = position[0];
			int y = position[1];
			for (int k = 0; k < dx.length; k++) {
				int row = x;
				int col = y;
				int count = dist[x][y];
				while (safe(g, row + dx[k], col + dy[k])) { // ROLL: count every cell crossed
					row += dx[k];
					col += dy[k];
					count += 1;
				}
				if (count < dist[row][col]) { // BETTER: a shorter way to this resting place
					dist[row][col] = count; // RECORD
					queue.offer(new int[] { row, col }); // QUEUE it again to improve its rolls
				}
			}
		}
		return dist[dstX][dstY] == INF ? -1 : dist[dstX][dstY]; // RETURN: -1 if never rested on dst
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col && g[x][y] == 0; // SKIP: off the grid or a wall
	}

	public static void main(String[] args) {
		int[][] m2 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(shortestPath(m2, new int[] { 0, 4 }, new int[] { 4, 4 })); // 12

		int[][] m3 = { { 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 }, { 1, 1, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		System.out.println(shortestPath(m3, new int[] { 0, 4 }, new int[] { 3, 2 })); // -1

		int[][] m4 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0 } };
		System.out.println(shortestPath(m4, new int[] { 4, 3 }, new int[] { 0, 1 })); // -1

	}
}

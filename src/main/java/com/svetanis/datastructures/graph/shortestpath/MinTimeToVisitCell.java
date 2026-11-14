package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 2577. Minimum Time to Visit a Cell In a Grid
//
// Input: grid -- an m x n grid; grid[r][c] is the earliest second at which you may be standing in
// cell (r, c). You start in (0, 0) at second 0, and every second you must move to an adjacent cell
// (up, down, left or right); you cannot stay still. Returns the least second at which you can be in
// the bottom-right cell, or -1 if you cannot get there. On LeetCode: 2 <= m, n <= 1000,
// 4 <= m * n <= 10^5, 0 <= grid[r][c] <= 10^5, grid[0][0] == 0.
//
// The one idea: dist[r][c] is the earliest second found so far at which you can be in cell (r, c),
// and one heap entry is a cell with such a second, earliest first. A step onto a neighbour arrives
// at time + 1, and if that is before the neighbour opens you cannot wait, but you can step back
// and forth, which burns exactly two seconds each time. So the arrival is the opening second
// grid[x][y] when the gap (grid[x][y] - (time + 1)) is even, and one second later when it is odd.
// If neither neighbour of the start is open by second 1 you cannot make even the first move, so
// the answer is -1; once one first move exists, back-and-forth steps reach every cell.
//
// Time: O(m * n * log(m * n)) -- a cell is pushed only when its time improves, at most once per
// neighbour, so at most 4 * m * n heap entries.
// Space: O(m * n) -- the table of times and the heap.
//
// Siblings: MinTimeToReachLastRoom is the easier variant (LC 3341), where you may wait.

public final class MinTimeToVisitCell {

	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int minTime(int[][] grid) {
		if (grid[0][1] > 1 && grid[1][0] > 1) {
			return -1; // BASE: no first move is possible, and standing still is not allowed
		}
		int n = grid.length;
		int m = grid[0].length;
		int[][] dist = new int[n][m];
		for (int[] row : dist) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		dist[0][0] = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[2]));
		pq.offer(new int[] { 0, 0, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll(); // PICK: the cell with the earliest second so far
			int row = node[0];
			int col = node[1];
			int time = node[2];
			if (row == n - 1 && col == m - 1) {
				return time; // RETURN: final the first time the target comes off the heap
			}
			for (int k = 0; k < 4; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				if (valid(grid, x, y)) {
					int nextTime = time + 1;
					if (nextTime < grid[x][y]) {
						// WAIT: too early, so bounce back and forth; each bounce costs 2 seconds
						nextTime = grid[x][y] + (grid[x][y] - nextTime) % 2;
					}
					if (nextTime < dist[x][y]) {
						dist[x][y] = nextTime; // RECORD: an earlier arrival at (x, y)
						pq.offer(new int[] { x, y, nextTime });
					}
				}
			}
		}
		return -1;
	}

	private static boolean valid(int[][] grid, int x, int y) {
		int n = grid.length;
		int m = grid[0].length;
		return x >= 0 && x < n && y >= 0 && y < m;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1, 3, 2 }, { 5, 1, 2, 5 }, { 4, 3, 8, 6 } };
		System.out.println(minTime(g1)); // 7
		int[][] g2 = { { 0, 2, 4 }, { 3, 2, 1 }, { 1, 0, 4 } };
		System.out.println(minTime(g2)); // -1
	}
}

package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1631. Path With Minimum Effort
//
// Input: heights -- a rows x columns grid; each cell holds a height. Start at the top-left cell and
// move up, down, left or right. The effort of a path is the largest height difference between two
// neighbouring cells on it. Returns the smallest effort over all paths to the bottom-right cell.
// On LeetCode: 1 <= rows, columns <= 100, 1 <= heights[i][j] <= 10^6.
//
// The one idea: efforts[r][c] is the smallest effort found so far of any path to cell (r, c), and
// one heap entry is a cell with that effort, smallest first. Stepping onto a neighbour costs
// max(effort so far, the height difference of the step), not a sum, because a path is only as
// hard as its worst step. That value never drops along a path, so the first time a cell comes off
// the heap its effort is final, which is all Dijkstra needs from its "length".
//
// Time: O(r * c * log(r * c)) -- a cell is pushed only when its effort improves, at most once per
// neighbouring cell, so at most 4 * r * c heap entries.
// Space: O(r * c) -- the efforts table and the heap.
//
// Siblings: todo.graph.bfs.MinEffortPathBinary binary-searches the effort with a flood fill as the
// yes/no test; todo.graph.bfs.MinEffortPathBacktrack tries every route with pruning.

public final class MinEffortPath {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };
	private static final int INF = (int) 1e6;

	public static int mep(int[][] heights) {
		int n = heights.length;
		int m = heights[0].length;
		// Priority Queue: row, col, effort
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[2]));
		pq.offer(new int[] { 0, 0, 0 });
		int[][] efforts = efforts(n, m);
		while (!pq.isEmpty()) {
			int[] node = pq.poll(); // PICK: the cell with the smallest effort so far
			int x = node[0];
			int y = node[1];
			int effort = node[2];
			if (x == n - 1 && y == m - 1) {
				return effort; // RETURN: final the first time the target comes off the heap
			}
			for (int i = 0; i < 4; i++) {
				int row = x + dx[i];
				int col = y + dy[i];
				if (safe(row, col, n, m)) {
					int diff = Math.abs(heights[row][col] - heights[x][y]);
					int max = Math.max(effort, diff); // the path is as hard as its worst step
					if (max < efforts[row][col]) {
						efforts[row][col] = max; // RECORD: a gentler path to (row, col)
						pq.offer(new int[] { row, col, max });
					}
				}
			}
		}
		return 0;
	}

	private static int[][] efforts(int n, int m) {
		int[][] grid = new int[n][m];
		for (int[] row : grid) {
			Arrays.fill(row, INF); // BASE: INF is above any real effort, 10^6 - 1 at most
		}
		return grid;
	}

	private static boolean safe(int x, int y, int n, int m) {
		boolean row = x >= 0 && x < n;
		boolean col = y >= 0 && y < m;
		return row && col;
	}

	public static void main(String[] args) {
		int[][] heights1 = { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } };
		System.out.println(mep(heights1)); // 2
		int[][] heights2 = { { 1, 2, 3 }, { 3, 8, 4 }, { 5, 3, 5 } };
		System.out.println(mep(heights2)); // 1
		int[][] heights3 = { { 1, 2, 1, 1, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 },
				{ 1, 1, 1, 2, 1 } };
		System.out.println(mep(heights3)); // 0
	}
}

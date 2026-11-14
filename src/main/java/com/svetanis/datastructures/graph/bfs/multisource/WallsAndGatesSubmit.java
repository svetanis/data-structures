package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 286. Walls and Gates
//
// Input: a grid of rooms; -1 is a wall, 0 is a gate, INF (2^31 - 1) is an empty room. Moves are
// one step up, down, left or right, never through a wall. Fills each empty room, in place, with
// the steps to its nearest gate; a room no gate can reach stays INF. On LeetCode:
// 1 <= m, n <= 250, rooms[i][j] is -1, 0 or 2^31 - 1.
//
// The one idea: the grid itself is the answer, and INF means "not reached yet". Every gate is
// put in the queue at the start, so the search spreads from all the gates at once, one ring of
// steps at a time. A queue entry is one cell { row, col } whose number is final; each neighbour
// that is still INF gets that number + 1 and joins the queue. Cells leave the queue in order of
// their distance, so the first time a room is reached is by its nearest gate and the number is
// never changed. A wall is never queued because it is not INF.
//
// Time: O(n * m) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(n * m) -- the queue.
//
// Siblings: WallsAndGates is the same search split into small methods.

public final class WallsAndGatesSubmit {

	private static final int INF = Integer.MAX_VALUE;
	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	public void gateDists(int[][] grid) {
		int rows = grid.length;
		if (rows == 0) {
			return;
		}
		int cols = grid[0].length;
		Deque<int[]> dq = new ArrayDeque<>();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == 0) { // SEED: every gate starts the search
					dq.add(new int[] { r, c });
				}
			}
		}
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int val = grid[node[0]][node[1]];
			for (int dist = 0; dist < dx.length; dist++) { // WALK: the four neighbours
				int row = node[0] + dx[dist];
				int col = node[1] + dy[dist];
				if (row < 0 || col < 0 || row >= rows || col >= cols || grid[row][col] != INF) {
					continue; // SKIP: off the grid, a wall, a gate, or a room already reached
				}
				grid[row][col] = 1 + val; // RECORD: one step farther than node
				dq.add(new int[] { row, col });
			}
		}
	}

	public static void main(String[] args) {
		WallsAndGatesSubmit zid = new WallsAndGatesSubmit();
		int[][] grid = { { INF, -1, 0, INF }, //
				{ INF, INF, INF, -1 }, //
				{ INF, -1, INF, -1 }, //
				{ 0, -1, INF, INF } };//
		zid.gateDists(grid);
		System.out.println(Arrays.deepToString(grid)); // [[3, -1, 0, 1], [2, 2, 1, -1], [1, -1, 2, -1], [0, -1, 3, 4]]
	}
}

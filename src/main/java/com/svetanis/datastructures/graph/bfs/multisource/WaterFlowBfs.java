package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 417. Pacific Atlantic Water Flow
//
// Input: grid -- an m x n island where grid[r][c] is the height of cell (r, c). The Pacific
// Ocean touches the top and left edges, the Atlantic Ocean the bottom and right edges. Rain water
// flows from a cell to a neighbour (north, south, east, west) of the same height or lower, and
// from an edge cell into the ocean it borders. Returns every cell [r, c] from which water can
// reach both oceans, in row-major order. On LeetCode: 1 <= m, n <= 200, 0 <= grid[r][c] <= 10^5.
//
// The one idea: pacific[r][c] is true when water from cell (r, c) can run downhill into the
// Pacific, and atlantic[r][c] the same for the Atlantic. Testing every cell on its own repeats
// work, so run it backwards: put every cell touching one ocean in a queue and climb UPHILL (to a
// neighbour of the same height or higher). Each cell reached can pour back down the same steps
// into that ocean, so it is marked. One climb per ocean; the cells marked in both grids are the
// answer.
//
// Time: O(m * n) -- a cell is marked once, and each queue entry looks at 4 neighbours; several
// neighbours may queue the same cell before it is marked, but each cell is queued by at most
// its 4 neighbours and its ocean edge, so the work stays proportional to m * n
// Space: O(m * n) -- the two boolean grids and the queue
//
// Siblings: WaterFlowDfs is the same climb with recursion instead of a queue; WaterFlow keeps
// the visited cells in sets of Cell objects; WaterFlowSubmit is the LeetCode submission.
public final class WaterFlowBfs {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private boolean[][] atlantic;
	private boolean[][] pacific;

	public List<List<Integer>> shortestPath(int[][] grid) {
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.atlantic = new boolean[rows][cols];
		this.pacific = new boolean[rows][cols];
		Deque<int[]> pq = new ArrayDeque<>();
		Deque<int[]> aq = new ArrayDeque<>();
		init(grid, pq, aq);
		bfs(grid, pq, pacific); // WALK: climb from the Pacific edge
		bfs(grid, aq, atlantic); // WALK: climb from the Atlantic edge
		return reachable();
	}

	private void init(int[][] grid, Deque<int[]> pq, Deque<int[]> aq) {
		for (int i = 0; i < rows; i++) {
			pq.offer(new int[] { i, 0 }); // BASE: left column touches the Pacific
			aq.offer(new int[] { i, cols - 1 }); // BASE: right column touches the Atlantic
		}
		for (int j = 0; j < cols; j++) {
			pq.offer(new int[] { 0, j }); // BASE: top row touches the Pacific
			aq.offer(new int[] { rows - 1, j }); // BASE: bottom row touches the Atlantic
		}
	}

	private List<List<Integer>> reachable() {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (atlantic[i][j] && pacific[i][j]) { // TAKE: both oceans reach it
					list.add(Arrays.asList(i, j));
				}
			}
		}
		return list;
	}

	private void bfs(int[][] grid, Deque<int[]> dq, boolean[][] reachable) {
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int row = cell[0];
			int col = cell[1];
			reachable[row][col] = true; // RECORD: this ocean's water can be traced back to it
			for (int k = 0; k < 4; k++) { // WALK: 4 neighbours
				int x = row + dx[k];
				int y = col + dy[k];
				if (!valid(x, y) || reachable[x][y] || grid[x][y] < grid[row][col]) {
					continue; // SKIP: off the island, already marked, or downhill
				}
				dq.offer(new int[] { x, y });
			}
		}
	}

	private boolean valid(int row, int col) {
		boolean isRow = row >= 0 && row < rows;
		boolean isCol = col >= 0 && col < cols;
		return isRow && isCol;
	}

	public static void main(String[] args) {
		int[][] grid = { //
				{ 1, 2, 2, 3, 5 }, //
				{ 3, 2, 3, 4, 4 }, //
				{ 2, 4, 5, 3, 1 }, //
				{ 6, 7, 1, 4, 5 }, //
				{ 5, 1, 1, 2, 4 } };//
		WaterFlowBfs wf = new WaterFlowBfs();
		System.out.println(wf.shortestPath(grid)); // [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]

		int[][] one = { { 1 } };
		System.out.println(wf.shortestPath(one)); // [[0, 0]]
	}
}

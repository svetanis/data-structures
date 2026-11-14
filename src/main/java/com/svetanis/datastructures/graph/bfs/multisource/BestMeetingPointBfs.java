package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 296. Best Meeting Point
//
// Input: an m x n grid of 0 and 1; a 1 is a friend's home, a 0 is empty ground. All friends walk
// to one meeting point, one step up, down, left or right at a time (the Manhattan distance:
// |r1 - r2| + |c1 - c2|). Returns the smallest possible total of the friends' steps. On
// LeetCode: 1 <= m, n <= 200, grid[i][j] is 0 or 1, at least two friends.
//
// The one idea: try every cell as the meeting point, and measure it with a breadth-first walk.
// One queue entry is one cell, and the map says how many steps it is from the cell being tried.
// The walk spreads outward one step at a time, so the first time it reaches a cell is the
// shortest way there; each friend's cell adds its step count to the total. The best total over
// all the cells tried is the answer. This is the slow, direct method: the walk itself is not
// needed, because on an open grid the steps are just the Manhattan distance.
//
// Time: O((r * c)^2) -- one walk over all r * c cells for each of the r * c cells tried.
// Space: O(r * c) -- the visited set, the step map and the queue of one walk.
//
// Siblings: BestMeetingPoint and BestMeetingPointSort find the answer in O(r * c) by taking the
// middle row and the middle column of the friends.

public final class BestMeetingPointBfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int[][] grid;

	public int bmp(int[][] grid) {
		int min = Integer.MAX_VALUE;
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.grid = grid;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) { // WALK: every cell is tried as the meeting point
				min = Math.min(min, bfs(new int[] { r, c })); // RECORD: the best total so far
			}
		}
		return min; // RETURN: smallest total over all cells
	}

	private int bfs(int[] src) {
		String key = src[0] + "_" + src[1];
		Set<String> set = new HashSet<>(); // cells already queued
		Deque<int[]> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>(); // cell -> steps from src
		queue.add(src);
		map.put(key, 0);
		set.add(key);
		int total = 0;
		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			int x = curr[0], y = curr[1];
			String currKey = x + "_" + y;
			int dist = map.get(currKey);
			if (grid[x][y] == 1) {
				total += dist; // TAKE: this friend's steps to src
			}
			List<int[]> neighbors = neighbors(curr);
			for (int[] next : neighbors) {
				String nextKey = next[0] + "_" + next[1];
				if (!set.contains(nextKey)) { // SKIP: already queued
					queue.add(next);
					map.put(nextKey, dist + 1); // RECORD: one step farther than curr
				}
				set.add(nextKey);
			}
		}
		return total;
	}

	private List<int[]> neighbors(int[] src) {
		List<int[]> list = new ArrayList<>();
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = src[0] + dx[dist];
			int y = src[1] + dy[dist];
			if (valid(x, y)) { // SKIP: off the grid
				list.add(new int[] { x, y });
			}
		}
		return list;
	}

	private boolean valid(int row, int col) {
		boolean one = row >= 0 && row < rows; // row number is in range
		boolean two = col >= 0 && col < cols; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		BestMeetingPointBfs bmp = new BestMeetingPointBfs();
		int[][] grid = { { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
		System.out.println(bmp.bmp(grid)); // 6 point (0,2)

		int[][] grid1 = { { 1, 1 } };
		System.out.println(bmp.bmp(grid1)); // 1
	}
}

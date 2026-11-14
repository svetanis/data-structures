package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Queue;

// 994. Rotting Oranges
//
// Input: a grid g where 0 is an empty cell, 1 a fresh orange and 2 a rotten orange. Every minute,
// each fresh orange that touches (up, down, left, right) a rotten orange turns rotten. On
// LeetCode: 1 <= rows, cols <= 10.
// Returns the number of minutes until no fresh orange is left, or -1 if some fresh orange can
// never rot. A grid with no fresh orange needs 0 minutes. The grid is changed: oranges that rot
// are overwritten with 2.
//
// The one idea: one queue entry is a rotten orange whose neighbours have not been checked yet. The
// oranges that are rotten at the start all spread at the same moment, so they all go into the
// queue before anything is taken out (a search that starts from many places at once). Taking the
// queue out one batch at a time is one minute: everything in the batch rots its fresh neighbours,
// and those neighbours form the next batch. A fresh orange that is left when the queue runs dry
// was never touched, so the answer is -1.
//
// Time: O(n * m) -- each orange rots and is queued at most once.
// Space: O(n * m) -- the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class RottingOranges {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int orangesRotting(int[][] g) {
		Queue<Node> queue = new ArrayDeque<>();
		int fresh = countAndEnque(g, queue); // SEED: all rotten oranges; fresh = how many are left to rot
		int minutes = 0;
		while (!queue.isEmpty() && fresh > 0) {
			for (int count = queue.size(); count > 0; count--) { // LEVEL: one minute = one batch
				Node node = queue.poll(); // TAKE
				for (int k = 0; k < dx.length; k++) {
					int x = node.x + dx[k];
					int y = node.y + dy[k];
					if (safe(g, x, y) && g[x][y] == 1) {
						g[x][y] = 2; // RECORD: rotten now, so it is not rotted twice
						fresh--;
						queue.offer(new Node(x, y));
					}
				}
			}
			minutes++; // COUNT
		}
		return fresh > 0 ? -1 : minutes; // RETURN: -1 if a fresh orange was never reached
	}

	private static int countAndEnque(int[][] g, Queue<Node> queue) {
		int n = g.length;
		int m = g[0].length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == 1) {
					count++; // COUNT: a fresh orange
				}
				if (g[i][j] == 2) {
					queue.offer(new Node(i, j)); // SEED: a rotten orange
				}
			}
		}
		return count;
	}

	private static boolean safe(int[][] g, int x, int y) {
		boolean row = x >= 0 && x < g.length;
		boolean col = y >= 0 && y < g[0].length;
		return row && col; // SKIP: off the grid
	}

	public static void main(String[] args) {
		int[][] m1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		System.out.println(orangesRotting(m1)); // 4

		int[][] m2 = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
		System.out.println(orangesRotting(m2)); // -1

		int[][] m3 = { { 0, 2 } };
		System.out.println(orangesRotting(m3)); // 0
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

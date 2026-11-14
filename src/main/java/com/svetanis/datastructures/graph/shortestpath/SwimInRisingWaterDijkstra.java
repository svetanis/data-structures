package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.PriorityQueue;

// 778. Swim in Rising Water
//
// Input: grid -- an n x n grid; grid[i][j] is the elevation of square (i, j). At time t the water
// is t deep everywhere, and you can swim between two neighbouring squares (up, down, left or
// right) only if both have elevation at most t. You start at the top-left square. Returns the
// least time at which you can reach the bottom-right square. On LeetCode: 1 <= n <= 50,
// 0 <= grid[i][j] < n * n, every value is different.
//
// The one idea: one heap entry is a square with the time at which you could be standing on it,
// smallest first. Stepping onto a neighbour needs the water to be at least as deep as that
// neighbour, so its time is max(time so far, its elevation) -- not a sum, because waiting is free
// and the time only ever goes up. The smallest entry in the heap can never be beaten by going
// through a larger one, so the first time the bottom-right square comes off the heap its time is
// the answer. A square is marked when it is pushed, not when it is popped: the square that first
// pushes a neighbour has the smallest time of all the squares beside it, and max(time, elevation)
// grows with time, so no later square could give the neighbour a smaller time.
//
// Time: O(n^2 * log n) -- each of the n^2 squares is pushed once, and a heap push costs log(n^2).
// Space: O(n^2) -- the visited board and the heap.
//
// Siblings: SwimInRisingWater binary-searches the depth and floods the board at each guess.

public final class SwimInRisingWaterDijkstra {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int swimInWater(int[][] grid) {
		int n = grid.length;
		boolean[][] visited = new boolean[n][n];
		visited[0][0] = true;
		// Priority Queue: row, col, time
		PriorityQueue<int[]> dq = new PriorityQueue<>(comparingInt(a -> a[2]));
		dq.add(new int[] { 0, 0, grid[0][0] });
		while (!dq.isEmpty()) {
			int[] node = dq.poll(); // PICK: the square with the smallest time so far
			int row = node[0];
			int col = node[1];
			int time = node[2];
			if (row == n - 1 && col == n - 1) {
				return time; // RETURN: final the first time the corner comes off the heap
			}
			for (int i = 0; i < 4; i++) {
				int x = row + dx[i];
				int y = col + dy[i];
				if (valid(n, x, y) && !visited[x][y]) {
					visited[x][y] = true; // MARK: when pushed, so no square is pushed twice
					int max = Math.max(time, grid[x][y]); // WAIT for the water to reach the neighbour
					dq.add(new int[] { x, y, max });
				}
			}
		}
		return -1;
	}

	private static boolean valid(int n, int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 2 }, { 1, 3 } };
		System.out.println(swimInWater(g1)); // 3

		int[][] g2 = { { 0, 1, 2, 3, 4 }, { 24, 23, 22, 21, 5 }, { 12, 13, 14, 15, 16 }, { 11, 17, 18, 19, 20 },
				{ 10, 9, 8, 7, 6 } };
		System.out.println(swimInWater(g2)); // 16
	}
}
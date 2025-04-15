package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 1368. Minimum Cost to Make at Least One Valid Path in a Grid

public final class MinCost {
	// Time & Space Complexity: O(n * m).

	private static final int[][] DIR = { { 0, 0 }, { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	public static int minCost(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		boolean[][] visited = new boolean[n][m];
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { 0, 0, 0 });
		while (!dq.isEmpty()) {
			int[] position = dq.poll();
			int x = position[0];
			int y = position[1];
			int cost = position[2];
			if (x == n - 1 && y == m - 1) {
				return cost;
			}
			if (visited[x][y]) {
				continue;
			}
			visited[x][y] = true;
			for (int k = 1; k <= 4; k++) {
				int row = x + DIR[k][0];
				int col = y + DIR[k][1];
				if (row >= 0 && row < n && col >= 0 && col < m) {
					if (g[x][y] == k) {
						dq.offerFirst(new int[] { row, col, cost });
					} else {
						dq.offer(new int[] { row, col, cost + 1 });
					}
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 1, 1, 1, 1 }, { 2, 2, 2, 2 } };
		System.out.println(minCost(g1)); // 3

		int[][] g2 = { { 1, 1, 3 }, { 3, 2, 2 }, { 1, 1, 4 } };
		System.out.println(minCost(g2)); // 0

		int[][] g3 = { { 1, 2 }, { 4, 3 } };
		System.out.println(minCost(g3)); // 1
	}
}

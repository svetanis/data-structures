package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;

// 1584. Min Cost to Connect All Points

public final class MinCostToConnectAllPoints {
	// Time Complexity: O(n^2)

	private static final int INF = 1 << 30;

	public int minCost(int[][] points) {
		int n = points.length;
		int[][] g = init(points);
		boolean[] visited = new boolean[n];
		int[] minDist = new int[n];
		Arrays.fill(minDist, INF);
		minDist[0] = 0;
		int cost = 0;
		for (int i = 0; i < n; i++) {
			int next = -1;
			for (int j = 0; j < n; j++) {
				boolean min = next == -1 || minDist[j] < minDist[next];
				if (!visited[j] && min) {
					next = j;
				}
			}
			visited[next] = true;
			cost += minDist[next];
			for (int k = 0; k < n; k++) {
				if (!visited[k]) {
					minDist[k] = Math.min(minDist[k], g[next][k]);
				}
			}
		}
		return cost;
	}

	private int[][] init(int[][] points) {
		int n = points.length;
		int[][] g = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int dist = mdist(points[i], points[j]);
				g[i][j] = dist;
				g[j][i] = dist;
			}
		}
		return g;
	}

	private int mdist(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPoints mcc = new MinCostToConnectAllPoints();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPoints mcc2 = new MinCostToConnectAllPoints();
		System.out.println(mcc2.minCost(points2)); // 18
	}
}

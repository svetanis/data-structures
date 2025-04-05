package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;

// 743. Network Delay Time
// Bellman-Ford Algorithm

public final class NetworkDelayTimeBFA {
	// Time Complexity: O(E log V)
	// Space Complexity: O(V + E)

	private static final int INF = (int) 1e9;

	public static int ndt(int[][] times, int n, int k) {
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dist[k] = 0;
		for (int i = 0; i < n - 1; i++) {
			for (int[] edge : times) {
				int from = edge[0];
				int to = edge[1];
				int time = edge[2];
				boolean visited = dist[from] != INF;
				if (visited && dist[from] + time < dist[to]) {
					dist[to] = dist[from] + time;
				}
			}
		}
		int max = 0;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, dist[i]);
		}
		return max == INF ? -1 : max;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(ndt(g1, 4, 2)); // 2

		int[][] g2 = { { 1, 2, 1 } };
		System.out.println(ndt(g2, 2, 1)); // 1

		int[][] g3 = { { 1, 2, 1 } };
		System.out.println(ndt(g3, 2, 2)); // -1
	}
}

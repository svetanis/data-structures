package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;

// 787. Cheapest Flights Within K Stops
// variation of Bellman-Ford algorithm

public final class CheapestFlightsWithKStops {
	// Time Complexity: O(k * (n + m))
	// Space Complexity: O(n)

	private static final int INF = (int) 1e9;

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		int[] prev = new int[n];
		int[] dist = new int[n];
		Arrays.fill(dist, INF);
		dist[src] = 0;
		for (int i = 0; i < k + 1; i++) {
			System.arraycopy(dist, 0, prev, 0, n);
			for (int[] flight : flights) {
				int from = flight[0];
				int to = flight[1];
				int cost = flight[2];
				dist[to] = Math.min(dist[to], prev[from] + cost);
			}
		}
		return dist[dst] == INF ? -1 : dist[dst];
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1, 100 }, { 1, 2, 100 }, { 2, 0, 100 }, { 1, 3, 600 }, { 2, 3, 200 } };
		System.out.println(cheapestFlight(4, g1, 0, 3, 1)); // 700

		int[][] g2 = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
		System.out.println(cheapestFlight(3, g2, 0, 2, 1)); // 200

		int[][] g3 = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
		System.out.println(cheapestFlight(3, g3, 0, 2, 0)); // 500
	}
}

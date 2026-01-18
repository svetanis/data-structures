package com.svetanis.datastructures.graph.advanced;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 787. Cheapest Flights Within K Stops
// BFS Algorithm 

public final class CheapestFlightsBFS {
	// Time Complexity: O(V + E * k)
	// Space Complexity: O(V + E * k)

	private static final int INF = (int) 1e9;

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		Map<Integer, List<int[]>> g = graph(flights);
		int[] minCost = new int[n];
		Arrays.fill(minCost, INF);
		// Queue: city, cost
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { src, 0 });
		int stops = 0;
		while (stops <= k && !dq.isEmpty()) {
			int size = dq.size();
			while (size-- > 0) {
				int[] node = dq.poll();
				int city = node[0];
				int cost = node[1];
				for (int[] neighbor : g.getOrDefault(city, new ArrayList<>())) {
					int next = neighbor[0];
					int newCost = cost + neighbor[1];
					if (newCost >= minCost[next]) {
						continue;
					}
					minCost[next] = newCost;
					dq.offer(new int[] { next, newCost });
				}
			}
			stops += 1;
		}
		return minCost[dst] == INF ? -1 : minCost[dst];
	}

	private static Map<Integer, List<int[]>> graph(int[][] flights) {
		Map<Integer, List<int[]>> g = new HashMap<>();
		for (int[] flight : flights) {
			int src = flight[0];
			int dst = flight[1];
			int cost = flight[2];
			g.computeIfAbsent(src, k -> new ArrayList<>()).add(new int[] { dst, cost });
		}
		return g;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1, 100 }, { 1, 2, 100 }, { 2, 0, 100 }, { 1, 3, 600 }, { 2, 3, 200 } };
		System.out.println(cheapestFlight(4, g1, 0, 3, 1)); // 700

		int[][] g2 = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
		System.out.println(cheapestFlight(3, g2, 0, 2, 1)); // 200

		int[][] g3 = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
		System.out.println(cheapestFlight(3, g3, 0, 2, 0)); // 500

		int[][] g4 = { { 0, 1, 5 }, { 1, 2, 5 }, { 0, 3, 2 }, { 3, 1, 2 }, { 1, 4, 1 }, { 4, 2, 1 } };
		System.out.println(cheapestFlight(5, g4, 0, 2, 2)); // 7

	}
}

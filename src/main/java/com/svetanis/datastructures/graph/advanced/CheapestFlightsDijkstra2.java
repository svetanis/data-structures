package com.svetanis.datastructures.graph.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// 787. Cheapest Flights Within K Stops
// Dijkstra's Algorithm 

public final class CheapestFlightsDijkstra2 {
	// Time Complexity: O(k * E * (E + log(E*K)))
	// Space Complexity: O(V + E * k)

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		Map<Integer, List<int[]>> g = graph(flights);
		Map<String, Integer> minCost = new HashMap<>();
		// Priority Queue: city, cost, stops
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.offer(new int[] { src, 0, 0 });

		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int city = node[0];
			int cost = node[1];
			int stops = node[2];
			if (city == dst) {
				return cost;
			}
			if (stops > k) {
				continue;
			}
			String key = city + "," + stops;
			if (minCost.containsKey(key) && minCost.get(key) <= cost) {
				continue;
			}
			minCost.put(key, cost);
			for (int[] neighbor : g.getOrDefault(city, new ArrayList<>())) {
				int next = neighbor[0];
				int newCost = cost + neighbor[1];
				pq.offer(new int[] { next, newCost, stops + 1 });
			}
		}
		return -1;
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

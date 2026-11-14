package com.svetanis.datastructures.graph.shortestpath;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 787. Cheapest Flights Within K Stops
//
// Input: n -- the number of cities, labelled 0 to n - 1; flights[i] = { from, to, price } -- a
// one-way flight; src and dst -- where the trip starts and ends; k -- the most cities the trip may
// pass through between them, so at most k + 1 flights. Returns the cheapest price from src to dst
// with at most k stops, or -1 if there is no such trip. On LeetCode: 1 <= n <= 100,
// 0 <= flights.length <= n * (n - 1) / 2, from != to, 1 <= price <= 10^4, no two flights share the
// same (from, to), 0 <= src, dst, k < n, src != dst.
//
// The one idea: one queue entry is one trip so far -- the city it has reached and what it has cost.
// The queue is walked one level at a time, and level L holds the trips that used exactly L
// flights, so k + 1 levels cover every allowed trip. minCost[c] is the cheapest price found so far
// to reach city c on any level. A trip to c is queued only if it beats minCost[c]. That is safe
// here, unlike in the Dijkstra versions, because levels are walked in order of flights used: a
// dearer trip found later has at least as many flights as the cheaper one found earlier, so it can
// never be the better answer.
//
// Time: O(V + E * k) -- at most k + 1 levels, and each level scans the flights out of the cities
// queued on it.
// Space: O(V + E * k) -- the lists of flights, minCost, and the queue entries.
//
// Siblings: CheapestFlightsBellmanFord does the same level-by-level relaxation over the flat list
// of flights; CheapestFlightsDijkstra and CheapestFlightsDijkstra2 take trips cheapest first and
// have to track the number of stops themselves.

public final class CheapestFlightsBFS {

	private static final int INF = (int) 1e9;

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		Map<Integer, List<int[]>> g = graph(flights);
		int[] minCost = new int[n];
		Arrays.fill(minCost, INF);
		// SEED: the trip from src to src costs nothing and uses no flight. Only
		// arrivals write minCost, so without this line src keeps INF and a query
		// with dst == src answers -1. It also stops a trip from returning to src
		minCost[src] = 0;
		// Queue: city, cost
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { src, 0 });
		int stops = 0;
		while (stops <= k && !dq.isEmpty()) {
			int size = dq.size(); // WALK: exactly the trips of this level
			while (size-- > 0) {
				int[] node = dq.poll();
				int city = node[0];
				int cost = node[1];
				for (int[] neighbor : g.getOrDefault(city, new ArrayList<>())) {
					int next = neighbor[0];
					int newCost = cost + neighbor[1];
					if (newCost >= minCost[next]) {
						continue; // SKIP: an earlier level already reached next at least as cheaply
					}
					minCost[next] = newCost; // RECORD: the new cheapest price to next
					dq.offer(new int[] { next, newCost });
				}
			}
			stops += 1;
		}
		return minCost[dst] == INF ? -1 : minCost[dst]; // RETURN: INF means never reached
	}

	private static Map<Integer, List<int[]>> graph(int[][] flights) {
		Map<Integer, List<int[]>> g = new HashMap<>();
		for (int[] flight : flights) {
			int src = flight[0];
			int dst = flight[1];
			int cost = flight[2];
			g.computeIfAbsent(src, k -> new ArrayList<>()).add(new int[] { dst, cost }); // BUILD: city -> flights out
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

		// src == dst is outside LC 787, which promises src != dst; the seeded source answers 0
		System.out.println(cheapestFlight(3, g2, 1, 1, 1)); // 0
	}
}

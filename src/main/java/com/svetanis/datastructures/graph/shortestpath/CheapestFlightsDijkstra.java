package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// 787. Cheapest Flights Within K Stops
//
// Input: n -- the number of cities, labelled 0 to n - 1; flights[i] = { from, to, price } -- a
// one-way flight; src and dst -- where the trip starts and ends; k -- the most cities the trip may
// pass through between them, so at most k + 1 flights. Returns the cheapest price from src to dst
// with at most k stops, or -1 if there is no such trip. On LeetCode: 1 <= n <= 100,
// 0 <= flights.length <= n * (n - 1) / 2, from != to, 1 <= price <= 10^4, no two flights share the
// same (from, to), 0 <= src, dst, k < n, src != dst.
//
// The one idea: one heap entry is one trip so far -- the city it has reached, what it has cost and
// how many flights it used. Trips come off the heap cheapest first, so the first trip to pop at
// dst is the cheapest one that fits. What differs from plain Dijkstra is what is remembered per
// city: minStops[c] holds the fewest flights with which c has been expanded, not a price. A trip to
// a city already expanded is dropped only if it used no fewer flights, because a dearer trip with
// fewer flights can still reach dst within the limit when the cheaper one cannot. Each expansion of
// a city lowers its minStops, so a city is expanded at most k + 2 times.
//
// Time: O(E * k * log(E * k)) -- at most k + 2 expansions per city, each pushing that city's
// flights, so at most E * (k + 2) heap entries.
// Space: O(V + E * k) -- the lists of flights, minStops, and the heap entries.
//
// Siblings: CheapestFlightsDijkstra2 remembers the cheapest price per (city, stops) pair instead
// of the fewest stops per city; CheapestFlightsBFS and CheapestFlightsBellmanFord walk the trips
// by number of flights instead of by price.

public final class CheapestFlightsDijkstra {

	private static final int INF = (int) 1e9;

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		Map<Integer, List<int[]>> g = graph(flights);
		// this array holds STOPS, not price. It is not Dijkstra's dist[]: a city is
		// worth expanding again if it is reached in fewer stops, even at a higher
		// price, because the usual settle-once-by-price rule discards the only trip
		// that can still reach dst within k. The price lives in the heap entry
		int[] minStops = new int[n];
		Arrays.fill(minStops, INF);
		// Priority Queue: city, cost, stops
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[1]));
		pq.offer(new int[] { src, 0, 0 });

		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int city = node[0];
			int cost = node[1];
			int stops = node[2];
			if (stops >= minStops[city] || stops > k + 1) {
				continue; // SKIP: expanded before with no more flights, or past the limit
			}
			minStops[city] = stops; // RECORD: the fewest flights this city has been expanded with
			if (city == dst) {
				return cost; // RETURN: cheapest first, so the first arrival at dst wins
			}
			for (int[] neighbor : g.getOrDefault(city, new ArrayList<>())) {
				int next = neighbor[0];
				int newCost = cost + neighbor[1];
				pq.offer(new int[] { next, newCost, stops + 1 }); // WALK: one more flight
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
	}
}

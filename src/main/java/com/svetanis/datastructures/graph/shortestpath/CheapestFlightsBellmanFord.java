package com.svetanis.datastructures.graph.shortestpath;

import java.util.Arrays;

// 787. Cheapest Flights Within K Stops
//
// Input: n -- the number of cities, labelled 0 to n - 1; flights[i] = { from, to, price } -- a
// one-way flight; src and dst -- where the trip starts and ends; k -- the most cities the trip may
// pass through between them, so at most k + 1 flights. Returns the cheapest price from src to dst
// with at most k stops, or -1 if there is no such trip. On LeetCode: 1 <= n <= 100,
// 0 <= flights.length <= n * (n - 1) / 2, from != to, 1 <= price <= 10^4, no two flights share the
// same (from, to), 0 <= src, dst, k < n, src != dst.
//
// The one idea: dist[c] is the cheapest price found so far to reach city c. Round r relaxes every
// flight once, and a round that reads only the previous round's prices adds at most one flight to
// any trip, so after k + 1 rounds dist[c] is the cheapest trip of at most k + 1 flights. The
// copy of dist taken at the start of each round (prev) is what holds a trip to one flight per
// round; relaxing against dist itself would let one round chain several flights.
//
// Time: O(k * (V + E)) -- k + 1 rounds, each copying V prices and relaxing E flights.
// Space: O(V) -- dist and prev, one price per city.
//
// Siblings: CheapestFlightsBFS walks the same rounds as queue levels and only touches cities whose
// price improved; CheapestFlightsDijkstra and CheapestFlightsDijkstra2 take trips cheapest first.

public final class CheapestFlightsBellmanFord {

	private static final int INF = (int) 1e9;

	public static int cheapestFlight(int n, int[][] flights, int src, int dst, int k) {
		int[] prev = new int[n];
		int[] dist = new int[n];
		Arrays.fill(dist, INF);
		dist[src] = 0;
		for (int i = 0; i < k + 1; i++) {
			// COPY: relaxing against the previous round's snapshot is what caps a trip at
			// k + 1 flights. Reading dist directly here lets one round chain several
			// flights: g3 below would return 200, not 500
			System.arraycopy(dist, 0, prev, 0, n);
			for (int[] flight : flights) {
				int from = flight[0];
				int to = flight[1];
				int cost = flight[2];
				if (prev[from] == INF) {
					// SKIP: never relax off an unreached city: INF + cost is not a price
					continue;
				}
				dist[to] = Math.min(dist[to], prev[from] + cost); // RELAX: one more flight
			}
		}
		return dist[dst] == INF ? -1 : dist[dst]; // RETURN: INF means never reached
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

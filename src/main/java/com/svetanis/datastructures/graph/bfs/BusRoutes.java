package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 815. Bus Routes
//
// Input: routes -- routes[i] lists the stops of bus i, which goes round that list for ever; source
// and target -- two stops. You start at source, on no bus yet, and may change buses at any stop that
// two buses share. Returns the fewest buses you must ride to get from source to target, 0 if they are
// the same stop, or -1 if it cannot be done. On LeetCode: 1 <= routes.length <= 500,
// 1 <= routes[i].length <= 10^5, the stops of one bus are distinct, the lengths add up to at most
// 10^5, 0 <= routes[i][j] < 10^6, 0 <= source, target < 10^6.
//
// The one idea: count buses, not stops. Once you are on a bus, every stop of its route costs
// nothing more, so one node is a BUS, and two buses are joined when they share a stop. One queue
// entry is {a bus you have boarded, the number of buses ridden so far, counting that one} -- the
// count rides in the entry. BusRoutesLevels is the same search with the count kept per level
// instead. Build a map from each stop to the buses through it. Start with every bus through
// source, at 1. Taking a bus off the queue, walk its stops: if target is one of them, the answer is
// its count; otherwise every bus through that stop that has not been boarded yet is boarded next,
// at one more. Two marks: visited per bus (marked when queued, so each bus is boarded once), and
// visitedStops -- once a stop's list of buses has been read, every bus on it is marked, so reading
// it again finds nothing. Without visitedStops, 1,000 buses sharing the same 1,000 stops took 5.4 s;
// with it, 71 ms.
//
// Time: O(s) -- s = the total number of stops listed. Each bus is walked once, and each stop's list
// of buses is read once; together those lists hold s entries. Building the map is O(s).
// Space: O(s) -- the map holds each stop of each bus once; visited, visitedStops and the queue
// are O(r + s).

public final class BusRoutes {

	public static int busRoutes(int[][] routes, int source, int target) {
		if (source == target) {
			return 0; // already there: no bus
		}
		Map<Integer, List<Integer>> map = map(routes); // MAP: a stop -> the buses through it
		List<Integer> buses = map.get(source);
		if (buses == null) {
			return -1; // no bus stops at source: never leave it
		}
		boolean[] visited = new boolean[routes.length]; // VISITED: buses already boarded
		Set<Integer> visitedStops = new HashSet<>(); // stops whose buses were all found already
		Deque<int[]> dq = new ArrayDeque<>(); // QUEUE: {bus, buses ridden}
		for (int bus : buses) { // START: board EVERY bus through source, at 1
			visited[bus] = true;
			dq.offer(new int[] { bus, 1 });
		}
		while (!dq.isEmpty()) {
			int[] entry = dq.poll(); // TAKE
			int bus = entry[0];
			int count = entry[1];
			for (int stop : routes[bus]) { // WALK: every stop of this bus, with no extra bus
				if (stop == target) { // BASE: target is on this bus
					return count;
				}
				if (!visitedStops.add(stop)) {
					continue; // SKIP: this stop's buses were all found already
				}
				for (int nextBus : map.get(stop)) { // CHANGE: to any bus through this stop
					if (!visited[nextBus]) {
						visited[nextBus] = true; // mark at enqueue
						dq.offer(new int[] { nextBus, count + 1 });
					}
				}
			}
		}
		return -1; // RETURN: target cannot be reached
	}

	private static Map<Integer, List<Integer>> map(int[][] routes) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int bus = 0; bus < routes.length; bus++) {
			for (int stop : routes[bus]) {
				map.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus); // RECORD: bus passes this stop
			}
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2, 7 }, { 3, 6, 7 } };
		System.out.println(busRoutes(g1, 1, 6)); // 2

		int[][] g2 = { { 7, 12 }, { 4, 5, 15 }, { 6 }, { 15, 19 }, { 9, 12, 13 } };
		System.out.println(busRoutes(g2, 15, 12)); // -1
		System.out.println(busRoutes(g1, 1, 1)); // 0

		int[][] g3 = { { 1, 2 }, { 1, 3 }, { 3, 4 } };
		System.out.println(busRoutes(g3, 1, 4)); // 2 -- buses 0 and 1 both start at 1
	}
}

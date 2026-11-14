package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// 815. Bus Routes -- the count kept per LEVEL
//
// Input: as BusRoutes -- routes[i] lists the stops bus i goes round for ever; start at stop source,
// on no bus; return the fewest buses to reach stop target, 0 if source == target, -1 if impossible.
//
// The one idea: one node is a BUS, not a stop -- a ride of any length is one step. Breadth first over
// buses, one level per extra bus: one turn of the while loop takes out every bus you can be on after
// `count` buses, and count goes up once, after the turn. Buses that come out one after another in the
// same turn are different riders' trips, so they share one count. The queue holds only buses;
// BusRoutes carries the count in each queue entry instead. routes[bus] is the bus -> stops lookup;
// the map is stop -> buses. visitedStops lives for the whole search: once a stop's buses have been
// read they are all marked, so the stop is skipped by every later bus.
//
// Time: O(s) -- s = the total number of stops listed; each bus is walked once and each stop's list
// of buses is read once. Space: O(s) for the map; O(r + s) for the marks and the queue.

public final class BusRoutesLevels {

	public static int busRoutes(int[][] routes, int source, int target) {
		if (source == target) {
			return 0; // already there: no bus
		}
		Map<Integer, List<Integer>> map = new HashMap<>(); // MAP: stop -> the buses through it
		for (int bus = 0; bus < routes.length; bus++) {
			for (int stop : routes[bus]) {
				map.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus);
			}
		}
		List<Integer> buses = map.get(source);
		if (buses == null) {
			return -1; // no bus stops at source
		}
		boolean[] visited = new boolean[routes.length]; // buses already queued
		Set<Integer> visitedStops = new HashSet<>(); // stops already read: lives for the whole search
		Queue<Integer> dq = new ArrayDeque<>();
		for (int bus : buses) { // START: EVERY bus through source, not just one
			visited[bus] = true;
			dq.offer(bus);
		}
		int count = 1; // boarding the first bus is 1
		while (!dq.isEmpty()) {
			for (int k = dq.size(); k > 0; k--) { // LEVEL: every bus reachable with `count` buses
				int next = dq.poll();
				for (int stop : routes[next]) { // routes[next] IS the bus -> stops lookup
					if (stop == target) {
						return count; // this bus reaches the target
					}
					if (!visitedStops.add(stop)) {
						continue; // this stop's buses were all found already
					}
					for (int nextBus : map.get(stop)) { // CHANGE: every bus sharing this stop
						if (!visited[nextBus]) {
							visited[nextBus] = true; // mark at enqueue
							dq.offer(nextBus);
						}
					}
				}
			}
			count++; // ONE place: one level = one more bus
		}
		return -1; // target never reached
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2, 7 }, { 3, 6, 7 } };
		System.out.println(busRoutes(g1, 1, 6)); // 2

		int[][] g2 = { { 7, 12 }, { 4, 5, 15 }, { 6 }, { 15, 19 }, { 9, 12, 13 } };
		System.out.println(busRoutes(g2, 15, 12)); // -1

		int[][] g3 = { { 1, 2 }, { 1, 3 }, { 3, 4 } };
		System.out.println(busRoutes(g3, 1, 4)); // 2 -- buses 0 and 1 share count 1
	}
}

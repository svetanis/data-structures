package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 815. Bus Routes

public final class BusRoutes {
	// Time Complexity: O(n + s)
	// Space Complexity: O(n + s)

	public static int busRoutes(int[][] routes, int src, int dst) {
		if (src == dst) {
			return 0;
		}
		boolean[] visited = new boolean[routes.length];
		Map<Integer, List<Integer>> g = graph(routes);
		Deque<int[]> dq = new ArrayDeque<>();
		for (int route : g.getOrDefault(src, new ArrayList<>())) {
			visited[route] = true;
			dq.offer(new int[] { route, 1 });
		}
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int index = node[0];
			int buses = node[1];
			for (int stop : routes[index]) {
				if (stop == dst) {
					return buses;
				}
				for (int next : g.getOrDefault(stop, new ArrayList<>())) {
					if (!visited[next]) {
						dq.offer(new int[] { next, buses + 1 });
						visited[next] = true;
					}
				}
			}
		}
		return -1;
	}

	private static Map<Integer, List<Integer>> graph(int[][] routes) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < routes.length; i++) {
			for (int stop : routes[i]) {
				map.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2, 7 }, { 3, 6, 7 } };
		System.out.println(busRoutes(g1, 1, 6)); // 2

		int[][] g2 = { { 7, 12 }, { 4, 5, 15 }, { 6 }, { 15, 19 } };
		System.out.println(busRoutes(g2, 15, 12)); // -1
	}
}

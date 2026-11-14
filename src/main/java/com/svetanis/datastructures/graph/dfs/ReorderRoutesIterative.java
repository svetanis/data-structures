package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1466. Reorder Routes to Make All Paths Lead to the City Zero
//
// Input: n cities numbered 0 to n - 1; connections[i] = {a, b} is a one-way road from city a to
// city b. The roads form a tree: n - 1 roads, and exactly one route between any two cities if the
// directions are ignored. Returns the fewest roads to turn around so that every city can reach
// city 0. On LeetCode: 2 <= n <= 5 * 10^4, connections.length == n - 1, a != b.
//
// The one idea: the same walk outward from city 0 as ReorderRoutes, with a stack of cities in
// place of the recursion. One entry in a city's list is one road seen from that city: the city at
// the other end, and whether the road leaves this city (directed is true) or arrives at it. One
// stack entry is a city already reached whose roads are not looked at yet. Going from a city over
// a road to a city not yet reached, the road must carry traffic back towards 0; if it leaves the
// city we are at, it points the wrong way and costs one turn. A tree has one route to each city,
// so every road is met exactly once, whatever order the stack takes the cities in.
//
// Time: O(n) -- n - 1 roads, each put in two lists and met once on the walk.
// Space: O(n) -- the lists, the marks and the stack.
//
// Siblings: ReorderRoutes is the recursive walk, which overflows a default Java stack on a very
// long chain of cities.

public final class ReorderRoutesIterative {

	public static int minReorder(int n, int[][] connections) {
		boolean[] visited = new boolean[n];
		Map<Integer, List<Edge>> graph = graph(connections);
		Deque<Integer> stack = new ArrayDeque<>();
		visited[0] = true; // MARK: city 0
		stack.push(0);
		int total = 0;
		while (!stack.isEmpty()) {
			int src = stack.pop(); // TAKE: a reached city
			for (Edge edge : graph.getOrDefault(src, Collections.emptyList())) { // WALK: every road at src
				if (!visited[edge.node]) { // SKIP: the road we came along
					total += edge.directed ? 1 : 0; // COUNT: a road leaving src points away from 0
					visited[edge.node] = true; // MARK
					stack.push(edge.node);
				}
			}
		}
		return total; // RETURN: wrong-way roads met on the walk from city 0
	}

	private static Map<Integer, List<Edge>> graph(int[][] connections) {
		Map<Integer, List<Edge>> map = new HashMap<>();
		for (int[] connection : connections) { // BUILD: each road in the lists of both its cities
			int src = connection[0];
			int dst = connection[1];
			map.computeIfAbsent(src, k -> new ArrayList<>()).add(new Edge(dst, true));
			map.computeIfAbsent(dst, k -> new ArrayList<>()).add(new Edge(src, false));
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1 }, { 1, 3 }, { 2, 3 }, { 4, 0 }, { 4, 5 } };
		System.out.println(minReorder(6, g1)); // 3
		int[][] g2 = { { 1, 0 }, { 1, 2 }, { 3, 2 }, { 3, 4 } };
		System.out.println(minReorder(5, g2)); // 2
		int[][] g3 = { { 1, 0 }, { 2, 0 } };
		System.out.println(minReorder(3, g3)); // 0
	}

	private static class Edge {
		private int node;
		private boolean directed;

		public Edge(int node, boolean directed) {
			this.node = node;
			this.directed = directed;
		}
	}
}

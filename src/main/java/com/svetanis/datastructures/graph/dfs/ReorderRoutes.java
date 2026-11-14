package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Collections;
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
// The one idea: walk outward from city 0, ignoring the directions. One entry in a city's list is
// one road seen from that city: the city at the other end, and whether the road leaves this city
// (directed is true) or arrives at it (false). Each road is put in the lists of both of its
// cities. Going outward from city src over a road to a city not yet visited, the road must carry
// traffic back towards 0, that is, from the new city to src. If the road leaves src (directed is
// true), it points the wrong way and costs one turn; if it arrives at src, it is already right.
// A tree has one route to each city, so every road is seen exactly once on the way out, and the
// answer is the number of wrong-way roads met.
//
// Time: O(n) -- n - 1 roads, each put in two lists and met once on the walk.
// Space: O(n) -- the lists and the marks. The recursion is as deep as the tree is tall: a chain
// of 50,000 cities needs 50,000 nested calls, more than a default Java stack holds (run with a
// larger -Xss); LeetCode's stack is larger.
//
// Siblings: ReorderRoutesIterative is the same walk with a stack of cities, for very long chains.

public final class ReorderRoutes {

	public static int minReorder(int n, int[][] connections) {
		boolean[] visited = new boolean[n];
		Map<Integer, List<Edge>> graph = graph(connections);
		return dfs(graph, 0, visited); // RETURN: wrong-way roads met on the walk from city 0
	}

	private static int dfs(Map<Integer, List<Edge>> graph, int src, boolean[] visited) {
		int total = 0;
		visited[src] = true; // MARK
		List<Edge> edges = graph.getOrDefault(src, Collections.emptyList());
		for (Edge edge : edges) { // WALK: every road at src
			int node = edge.node;
			boolean directed = edge.directed;
			if (!visited[node]) { // SKIP: the road we came along
				int count = directed ? 1 : 0; // COUNT: a road leaving src points away from 0
				total += count + dfs(graph, node, visited);
			}
		}
		return total;
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

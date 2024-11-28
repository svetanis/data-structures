package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1466. Reorder Routes to Make All Paths Lead to the City Zero

public final class ReorderRoutes {
	// Time Complexity: O(N + E)
	// Space Complexity: O(N + E)

	public static int minReorder(int n, int[][] connections) {
		boolean[] visited = new boolean[n];
		Map<Integer, List<Edge>> graph = graph(connections);
		return dfs(graph, 0, visited);
	}

	private static int dfs(Map<Integer, List<Edge>> graph, int src, boolean[] visited) {
		int total = 0;
		visited[src] = true;
		List<Edge> edges = graph.getOrDefault(src, Collections.emptyList());
		for (Edge edge : edges) {
			int node = edge.node;
			boolean directed = edge.directed;
			if (!visited[node]) {
				int count = directed ? 1 : 0;
				total += count + dfs(graph, node, visited);
			}
		}
		return total;
	}

	private static Map<Integer, List<Edge>> graph(int[][] connections) {
		Map<Integer, List<Edge>> map = new HashMap<>();
		for (int[] connection : connections) {
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
		System.out.println(minReorder(6, g2)); // 2
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

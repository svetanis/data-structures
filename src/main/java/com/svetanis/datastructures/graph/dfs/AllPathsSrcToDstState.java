package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1059. All Paths from Source Lead to Destination

public final class AllPathsSrcToDstState {
	// Time complexity: O(V + E)
	// Space complexity: O(V + E)

	private int dst;
	private Boolean[] visited;
	private List<Integer>[] graph;

	public boolean leadsToDst(int n, int[][] edges, int src, int dst) {
		this.dst = dst;
		this.visited = new Boolean[n];
		this.graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			this.graph[from].add(to);
		}
		return dfs(src);
	}

	private boolean dfs(int node) {
		if (visited[node] != null) {
			return visited[node];
		}

		if (graph[node].size() == 0) {
			return node == dst;
		}
		visited[node] = false;
		for (int next : graph[node]) {
			if (!dfs(next)) {
				return false;
			}
		}
		visited[node] = true;
		return true;
	}

	public static void main(String[] args) {
		AllPathsSrcToDstState psd = new AllPathsSrcToDstState();
		int[][] edges1 = { { 0, 1 }, { 0, 2 } };
		System.out.println(psd.leadsToDst(3, edges1, 0, 2)); // false

		int[][] edges2 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 2, 1 } };
		System.out.println(psd.leadsToDst(4, edges2, 0, 3)); // false

		int[][] edges3 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(psd.leadsToDst(4, edges3, 0, 3)); // true
	}
}

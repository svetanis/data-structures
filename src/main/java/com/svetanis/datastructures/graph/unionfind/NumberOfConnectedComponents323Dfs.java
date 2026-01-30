package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 323. Number of Connected Components in an Undirected Graph

public final class NumberOfConnectedComponents323Dfs {
	// Time Complexity: O(N + E)
	// Space Complexity: O(n)

	public int countComponents(int n, int[][] edges) {
		int components = 0;
		boolean[] visited = new boolean[n];
		List<Integer>[] graph = new ArrayList[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			graph[from].add(to);
			graph[to].add(from);
		}
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				dfs(i, graph, visited);
				components += 1;
			}
		}
		return components;
	}

	private void dfs(int src, List<Integer>[] graph, boolean[] visited) {
		visited[src] = true;
		for (int node : graph[src]) {
			if (!visited[node]) {
				dfs(node, graph, visited);
			}
		}
	}

	public static void main(String[] args) {
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
		NumberOfConnectedComponents323Dfs cc = new NumberOfConnectedComponents323Dfs();
		System.out.println(cc.countComponents(5, edges)); // 2
	}
}

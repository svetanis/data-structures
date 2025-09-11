package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 2192. All Ancestors of a Node in a Directed Acyclic Graph

public final class AllAncestorsOfNode {
	// Time Complexity: O(V * (V + E))
	// Space Complexity: O(V + E)

	private int n;
	private List<Integer>[] graph;
	private List<List<Integer>> ancestors;

	public List<List<Integer>> ancestors(int n, int[][] edges) {
		this.n = n;
		this.ancestors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ancestors.add(new ArrayList<>());
		}
		buildGraph(edges);
		for (int node = 0; node < n; node++) {
			bfs(node);
		}
		return ancestors;
	}

	private void bfs(int src) {
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(src);
		boolean[] visited = new boolean[n];
		visited[src] = true;
		while (!dq.isEmpty()) {
			int node = dq.poll();
			for (int child : graph[node]) {
				if (!visited[child]) {
					visited[child] = true;
					dq.offer(child);
					ancestors.get(child).add(src);
				}
			}
		}
	}

	private void buildGraph(int[][] edges) {
		this.graph = new List[n];
		Arrays.setAll(graph, i -> new ArrayList<>());
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			graph[from].add(to);
		}
	}

	public static void main(String[] args) {
		AllAncestorsOfNode aan = new AllAncestorsOfNode();
		int[][] edges1 = { { 0, 3 }, { 0, 4 }, { 1, 3 }, { 2, 4 }, { 2, 7 }, { 3, 5 }, { 3, 6 }, { 3, 7 }, { 4, 6 } };
		System.out.println(aan.ancestors(8, edges1));
		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 2, 3 }, { 2, 4 },
				{ 3, 4 } };
		System.out.println(aan.ancestors(5, edges2));
	}
}

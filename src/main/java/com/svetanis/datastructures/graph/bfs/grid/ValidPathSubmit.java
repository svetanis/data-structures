package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 1971. Find if Path Exists in Graph

public final class ValidPathSubmit {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V)

	public static boolean validPath(int n, int[][] edges, int src, int dst) {
		List<Integer>[] g = graph(edges, n);
		boolean[] visited = new boolean[n];
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(src);
		visited[src] = true;
		while (!queue.isEmpty()) {
			int node = queue.poll();
			if (node == dst) {
				return true;
			}
			for (int neighbour : g[node]) {
				if (!visited[neighbour]) {
					queue.add(neighbour);
					visited[neighbour] = true;
				}
			}
		}
		return false;
	}

	private static List<Integer>[] graph(int[][] edges, int n) {
		List<Integer>[] graph = new List[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int[] edge : edges) {
			int src = edge[0], dst = edge[1];
			graph[src].add(dst);
			graph[dst].add(src);
		}
		return graph;
	}

	public static void main(String[] args) {
		int[][] e1 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
		System.out.println(validPath(3, e1, 0, 2)); // true

		int[][] e2 = { { 0, 1 }, { 0, 2 }, { 3, 5 }, { 5, 4 }, { 4, 3 } };
		System.out.println(validPath(6, e2, 0, 5)); // false

		int[][] e3 = { { 0, 7 }, { 0, 8 }, { 6, 1 }, { 2, 0 }, { 0, 4 }, { 5, 8 }, { 4, 7 }, { 1, 3 }, { 3, 5 }, { 6, 5 } };
		System.out.println(validPath(10, e3, 7, 5)); // true
	}
}

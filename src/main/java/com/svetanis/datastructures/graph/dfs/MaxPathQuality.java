package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.List;

// 2065. Maximum Path Quality of a Graph

public final class MaxPathQuality {
	// Time Complexity: O(n * 2^n)
	// Space Complexity: O(n + e)

	private int maxQuality;
	private int[] values;
	private boolean[] visited;
	private List<int[]>[] graph;

	public int mpq(int[] values, int[][] edges, int maxTime) {
		this.values = values;
		int n = values.length;
		this.visited = new boolean[n];
		visited[0] = true;
		this.graph = graph(n, edges);
		dfs(0, maxTime, values[0]);
		return maxQuality;
	}

	private void dfs(int node, int remainingTime, int quality) {
		if (node == 0 && quality > maxQuality) {
			maxQuality = quality;
		}
		for (int[] pair : graph[node]) {
			int next = pair[0];
			int time = pair[1];
			if (remainingTime - time >= 0) {
				if (!visited[next]) {
					visited[next] = true;
					dfs(next, remainingTime - time, quality + values[next]);
					visited[next] = false;
				} else {
					dfs(next, remainingTime - time, quality);
				}
			}
		}
	}

	private List<int[]>[] graph(int n, int[][] edges) {
		List<int[]>[] g = new List[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
		}
		for (int[] edge : edges) {
			int src = edge[0];
			int dst = edge[1];
			int time = edge[2];
			g[src].add(new int[] { dst, time });
			g[dst].add(new int[] { src, time });
		}
		return g;
	}

	public static void main(String[] args) {
		int[] values1 = { 0, 32, 10, 43 };
		int[][] edges1 = { { 0, 1, 10 }, { 1, 2, 15 }, { 0, 3, 10 } };
		MaxPathQuality mpq = new MaxPathQuality();
		System.out.println(mpq.mpq(values1, edges1, 49)); // 75

		int[] values2 = { 5, 10, 15, 20 };
		int[][] edges2 = { { 0, 1, 10 }, { 1, 2, 10 }, { 0, 3, 10 } };
		MaxPathQuality mpq2 = new MaxPathQuality();
		System.out.println(mpq2.mpq(values2, edges2, 30)); // 25

		int[] values3 = { 1, 2, 3, 4 };
		int[][] edges3 = { { 0, 1, 10 }, { 1, 2, 11 }, { 2, 3, 12 }, { 1, 3, 13 } };
		MaxPathQuality mpq3 = new MaxPathQuality();
		System.out.println(mpq3.mpq(values3, edges3, 50)); // 7
	}
}

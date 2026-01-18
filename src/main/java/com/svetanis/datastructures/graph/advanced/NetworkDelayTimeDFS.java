package com.svetanis.datastructures.graph.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 743. Network Delay Time
// DFS Algorithm 

public final class NetworkDelayTimeDFS {
	// Time Complexity: O((V - 1)! + E logE)
	// Space Complexity: O(V + E)

	private static final int INF = (int) 1e9;

	private Map<Integer, List<int[]>> graph;

	public int ndt(int[][] times, int n, int k) {
		this.graph = graph(n, times);
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dfs(dist, k, 0);
		int max = 0;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, dist[i]);
		}
		return max == INF ? -1 : max;
	}

	private void dfs(int[] dist, int node, int time) {
		if (time >= dist[node]) {
			return;
		}
		dist[node] = time;
		for (int[] edge : graph.getOrDefault(node, new ArrayList<>())) {
			int arrivalTime = edge[1];
			int next = edge[0];
			dfs(dist, next, time + arrivalTime);
		}
	}

	private Map<Integer, List<int[]>> graph(int n, int[][] times) {
		Map<Integer, List<int[]>> g = new HashMap<>();
		for (int[] node : times) {
			int src = node[0];
			int dst = node[1];
			int time = node[2];
			g.computeIfAbsent(src, k -> new ArrayList<>()).add(new int[] { dst, time });
		}
		return g;
	}

	public static void main(String[] args) {
		NetworkDelayTimeDFS ndt = new NetworkDelayTimeDFS();
		int[][] g1 = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(ndt.ndt(g1, 4, 2)); // 2

		int[][] g2 = { { 1, 2, 1 } };
		System.out.println(ndt.ndt(g2, 2, 1)); // 1

		int[][] g3 = { { 1, 2, 1 } };
		System.out.println(ndt.ndt(g3, 2, 2)); // -1
	}
}

package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// 743. Network Delay Time
// Dijkstra's Algorithm 

public final class NetworkDelayTime {
	// Time Complexity: O(E log V)
	// Space Complexity: O(V + E)

	private static final int INF = (int) 1e9;

	public static int ndt(int[][] times, int n, int k) {
		Map<Integer, Integer> dist = new HashMap<>();
		dist.put(k, 0);
		Map<Integer, List<int[]>> g = graph(n, times);
		// Priority queue: (node, time)
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.offer(new int[] { k, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int src = node[0], currentTime = node[1];
			if (!g.containsKey(src)) {
				continue;
			}
			for (int[] neighbor : g.get(src)) {
				int next = neighbor[0];
				int time = currentTime + neighbor[1];
				if (time < dist.getOrDefault(next, INF)) {
					dist.put(next, time);
					pq.offer(new int[] { next, time });
				}
			}
		}
		int max = 0;
		for (int delay : dist.values()) {
			max = Math.max(max, delay);
		}
		return dist.size() == n ? max : -1;
	}

	private static Map<Integer, List<int[]>> graph(int n, int[][] times) {
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
		int[][] g1 = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(ndt(g1, 4, 2)); // 2

		int[][] g2 = { { 1, 2, 1 } };
		System.out.println(ndt(g2, 2, 1)); // 1

		int[][] g3 = { { 1, 2, 1 } };
		System.out.println(ndt(g3, 2, 2)); // -1
	}
}

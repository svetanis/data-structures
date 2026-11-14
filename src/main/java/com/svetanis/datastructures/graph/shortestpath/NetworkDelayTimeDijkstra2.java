package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// 743. Network Delay Time
//
// Input: times[i] = { u, v, w } -- a signal sent from node u reaches node v after w time units;
// n -- the number of nodes, labelled 1 to n; k -- the node that sends the signal. Returns the time
// until every node has received it, or -1 if some node never does. On LeetCode: 1 <= k <= n <= 100,
// 1 <= times.length <= 6000, u != v, 0 <= w <= 100, all pairs (u, v) are different.
//
// The one idea: dist[v] is the earliest time found so far at which node v gets the signal, and one
// heap entry is a node with such a time, earliest first. No edge takes negative time, so the
// earliest entry in the heap can never be beaten by going through a later one: the first time a
// node comes off the heap its time is final. The answer is the largest dist; a node still at INF
// was never reached, so the answer is -1.
//
// Time: O(V + E log V) -- a node is pushed only when its time improves, at most once per edge, and
// the stale-entry skip means each node's edges are scanned once.
// Space: O(V + E) -- the adjacency lists, dist, and the heap.
//
// Siblings: NetworkDelayTimeDijkstra is the same search with a map in place of the int[];
// NetworkDelayTimeBFS, NetworkDelayTimeBellmanFord and NetworkDelayTimeDFS relax without a heap.

public final class NetworkDelayTimeDijkstra2 {

	private static final int INF = (int) 1e9;

	public static int ndt(int[][] times, int n, int k) {
		Map<Integer, List<int[]>> g = graph(n, times);
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dist[k] = 0;
		// Priority queue: (node, time)
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[1]));
		pq.offer(new int[] { k, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll(); // PICK: the node with the earliest time so far
			int src = node[0], time = node[1];
			if (time > dist[src]) {
				continue; // SKIP: a stale copy; src was reached earlier
			}
			for (int[] neighbor : g.getOrDefault(src, new ArrayList<>())) {
				int next = neighbor[0];
				int arrivalTime = time + neighbor[1];
				if (arrivalTime < dist[next]) {
					dist[next] = arrivalTime; // RECORD: an earlier signal at next
					pq.offer(new int[] { next, arrivalTime });
				}
			}
		}
		int max = 0;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, dist[i]); // TAKE: the last node to hear decides the delay
		}
		return max == INF ? -1 : max; // RETURN: INF means some node never heard
	}

	private static Map<Integer, List<int[]>> graph(int n, int[][] times) {
		Map<Integer, List<int[]>> g = new HashMap<>();
		for (int[] node : times) {
			int src = node[0];
			int dst = node[1];
			int time = node[2];
			g.computeIfAbsent(src, k -> new ArrayList<>()).add(new int[] { dst, time }); // BUILD: node -> edges out
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

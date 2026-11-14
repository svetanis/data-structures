package com.svetanis.datastructures.graph.shortestpath;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 743. Network Delay Time
//
// Input: times[i] = { u, v, w } -- a signal sent from node u reaches node v after w time units;
// n -- the number of nodes, labelled 1 to n; k -- the node that sends the signal. Returns the time
// until every node has received it, or -1 if some node never does. On LeetCode: 1 <= k <= n <= 100,
// 1 <= times.length <= 6000, u != v, 0 <= w <= 100, all pairs (u, v) are different.
//
// The one idea: dist[v] is the earliest time found so far at which node v gets the signal, and one
// queue entry is a node whose time just improved and whose out-edges have not been tried since.
// Taking a node off the queue offers each of its edges as a shorter way to its far end; if that
// beats the far end's time, the far end goes on the queue. The queue is first in, first out and
// has no ordering by time, so a node can be queued again every time its time improves; the search
// ends when nothing improves any more. The answer is the largest time, because the signal has
// reached every node only when the last one has it.
//
// Time: O(V * E) -- the usual Bellman-Ford bound: a node is queued again each time its time
// improves, and with no in-queue flag the same node can be queued more than once at a time.
// Space: O(V + E) for the adjacency lists and dist, plus the queue, which holds one entry per
// improvement not yet processed and so can hold the same node several times.
//
// Siblings: NetworkDelayTimeDijkstra and NetworkDelayTimeDijkstra2 take nodes earliest first, so
// each node is final the first time it comes off the queue; NetworkDelayTimeBellmanFord relaxes
// the flat list of edges V - 1 times; NetworkDelayTimeDFS goes depth first with the same pruning.

public final class NetworkDelayTimeBFS {

	private static final int INF = (int) 1e9;

	public static int ndt(int[][] times, int n, int k) {
		Map<Integer, List<int[]>> g = graph(n, times);
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dist[k] = 0;
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(k);
		while (!dq.isEmpty()) {
			int node = dq.poll(); // WALK: a node whose time improved
			for (int[] neighbor : g.getOrDefault(node, new ArrayList<>())) {
				int next = neighbor[0];
				int time = neighbor[1];
				int arrivalTime = dist[node] + time;
				if (dist[next] > arrivalTime) {
					dist[next] = arrivalTime; // RECORD: an earlier signal at next
					dq.offer(next);
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

package com.svetanis.datastructures.graph.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
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
// The one idea: dist[v] is the earliest time found so far at which node v gets the signal. The
// walk goes depth first from k carrying the time it arrived at the current node. A node is
// entered only if this arrival is strictly earlier than any before, which is the cutoff that stops
// the walk going round a cycle and also drops every route that is already beaten. The answer is
// the largest dist, because the signal has reached every node only when the last one has it.
//
// Time: O((V - 1)!) in the worst case -- a node is re-entered every time a faster route to it is
// found, so the number of routes tried can grow with the number of orderings of the nodes. The
// cutoff 'if (time >= dist[node]) return;' prunes most of them in practice. There is no sort in
// this file, so there is no E log E term.
// Space: O(V + E) -- the adjacency lists, dist, and the recursion, at most V deep because a
// route that revisits a node at an equal or later time is cut off.
//
// Siblings: NetworkDelayTimeDijkstra and NetworkDelayTimeDijkstra2 take nodes earliest first, so
// no node is re-entered; NetworkDelayTimeBFS is the same relax-and-retry with a queue.

public final class NetworkDelayTimeDFS {

	private static final int INF = (int) 1e9;

	private Map<Integer, List<int[]>> graph;

	public int ndt(int[][] times, int n, int k) {
		this.graph = graph(n, times);
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dfs(dist, k, 0);
		int max = 0;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, dist[i]); // TAKE: the last node to hear decides the delay
		}
		return max == INF ? -1 : max; // RETURN: INF means some node never heard
	}

	private void dfs(int[] dist, int node, int time) {
		if (time >= dist[node]) {
			return; // SKIP: an earlier or equal arrival already went through here
		}
		dist[node] = time; // RECORD: the earliest arrival at node so far
		for (int[] edge : graph.getOrDefault(node, new ArrayList<>())) {
			int arrivalTime = edge[1];
			int next = edge[0];
			dfs(dist, next, time + arrivalTime); // WALK: carry the time along the edge
		}
	}

	private Map<Integer, List<int[]>> graph(int n, int[][] times) {
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
		NetworkDelayTimeDFS ndt = new NetworkDelayTimeDFS();
		int[][] g1 = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(ndt.ndt(g1, 4, 2)); // 2

		int[][] g2 = { { 1, 2, 1 } };
		System.out.println(ndt.ndt(g2, 2, 1)); // 1

		int[][] g3 = { { 1, 2, 1 } };
		System.out.println(ndt.ndt(g3, 2, 2)); // -1
	}
}

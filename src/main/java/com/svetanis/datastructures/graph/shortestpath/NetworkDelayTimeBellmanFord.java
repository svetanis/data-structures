package com.svetanis.datastructures.graph.shortestpath;

import java.util.Arrays;

// 743. Network Delay Time
//
// Input: times[i] = { u, v, w } -- a signal sent from node u reaches node v after w time units;
// n -- the number of nodes, labelled 1 to n; k -- the node that sends the signal. Returns the time
// until every node has received it, or -1 if some node never does. On LeetCode: 1 <= k <= n <= 100,
// 1 <= times.length <= 6000, u != v, 0 <= w <= 100, all pairs (u, v) are different.
//
// The one idea: dist[v] is the earliest time found so far at which node v gets the signal. One
// round offers every edge as a shorter way to its far end. After round r, dist[v] is correct for
// every node whose best route uses at most r edges, and no best route uses more than n - 1 edges,
// so n - 1 rounds are enough. An edge out of a node that has not heard yet (dist is INF) is not
// tried, because INF + w is not a time. The answer is the largest dist, because the signal has
// reached every node only when the last one has it.
//
// Time: O(V * E) -- V - 1 rounds over all E edges. There is no heap here: O(E log V) is
// Dijkstra's bound, and this file is the slower algorithm that also survives negative weights.
// Space: O(V) -- the dist array; the edges are the input.
//
// Siblings: NetworkDelayTimeDijkstra and NetworkDelayTimeDijkstra2 take nodes earliest first;
// NetworkDelayTimeBFS queues only the nodes whose time just improved; NetworkDelayTimeDFS goes
// depth first with the same pruning.

public final class NetworkDelayTimeBellmanFord {

	private static final int INF = (int) 1e9;

	public static int ndt(int[][] times, int n, int k) {
		int[] dist = new int[n + 1];
		Arrays.fill(dist, INF);
		dist[k] = 0;
		for (int i = 0; i < n - 1; i++) { // WALK: n - 1 rounds
			for (int[] edge : times) {
				int from = edge[0];
				int to = edge[1];
				int time = edge[2];
				boolean visited = dist[from] != INF;
				if (visited && dist[from] + time < dist[to]) {
					dist[to] = dist[from] + time; // RELAX: a shorter way to to through from
				}
			}
		}
		int max = 0;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, dist[i]); // TAKE: the last node to hear decides the delay
		}
		return max == INF ? -1 : max; // RETURN: INF means some node never heard
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

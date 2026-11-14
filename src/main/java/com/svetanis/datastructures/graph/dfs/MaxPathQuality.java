package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.List;

// 2065. Maximum Path Quality of a Graph
//
// Input: values -- values[i] is the value of node i; edges -- each {u, v, time} is a two-way road
// between nodes u and v that takes time to cross; maxTime. A path starts at node 0, ends at node 0
// and takes at most maxTime in total. Its quality is the sum of the values of the DIFFERENT nodes
// on it: a node walked through twice counts once. Returns the largest quality. On LeetCode: 2 <= n
// <= 1000 (n = values.length), 0 <= values[i] <= 10^8, 0 <= edges.length <= 2000, 10 <= time,
// maxTime <= 100, and no node has more than four roads.
//
// The one idea: try every walk; the limits make that small. One call of dfs is one stop on a walk:
// it holds the node we stand on, the time still left, and the quality so far (the values of the
// nodes visited at least once). Every road takes at least 10, and maxTime is at most 100, so a walk
// crosses at most 10 roads, and at each node there are at most 4 roads to pick from. Standing on a
// node, take each road the time left can pay for. If it leads to a node not yet on the walk, mark
// that node and add its value; if it leads to a node already on the walk, go on without adding
// (going back is allowed, but the value must not count twice). On the way back, un-mark a node we
// marked, so that other walks can use it. Whenever the walk stands on node 0, it is a finished
// path, and its quality is a candidate for the answer.
//
// Time: O(4^(maxTime / 10)) -- a walk has at most maxTime / 10 = 10 steps and each step has at most
// 4 roads to take, so at most 4^0 + 4^1 + ... + 4^10 calls (about 1.4 * 10^6); building the lists
// of roads adds O(n + e).
// Space: O(n + e) -- the road lists and the marks; the walk itself is at most 11 calls deep.

public final class MaxPathQuality {

	private int maxQuality;
	private int[] values;
	private boolean[] visited;
	private List<int[]>[] graph;

	public int mpq(int[] values, int[][] edges, int maxTime) {
		this.maxQuality = 0; // BASE: every call starts its own answer
		this.values = values;
		int n = values.length;
		this.visited = new boolean[n];
		visited[0] = true; // MARK: the start counts once, with its value
		this.graph = graph(n, edges);
		dfs(0, maxTime, values[0]);
		return maxQuality; // RETURN: the best quality of a walk that ended at node 0
	}

	private void dfs(int node, int remainingTime, int quality) {
		if (node == 0 && quality > maxQuality) {
			maxQuality = quality; // RECORD: a finished path through node 0
		}
		for (int[] pair : graph[node]) { // WALK: each road out of this node
			int next = pair[0];
			int time = pair[1];
			if (remainingTime - time >= 0) { // SKIP: a road the time left cannot pay for
				if (!visited[next]) {
					visited[next] = true; // MARK: first visit, its value counts
					dfs(next, remainingTime - time, quality + values[next]);
					visited[next] = false; // UNMARK: other walks may use this node
				} else {
					dfs(next, remainingTime - time, quality); // visited before: no value added
				}
			}
		}
	}

	private List<int[]>[] graph(int n, int[][] edges) {
		List<int[]>[] g = new List[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
		}
		for (int[] edge : edges) { // BUILD: each road is listed at both ends, as {other end, time}
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

		int[] values4 = { 0, 1, 2 };
		int[][] edges4 = { { 1, 2, 10 } };
		System.out.println(mpq3.mpq(values4, edges4, 10)); // 0
	}
}

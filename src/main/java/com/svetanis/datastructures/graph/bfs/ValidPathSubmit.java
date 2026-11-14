package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 1971. Find if Path Exists in Graph
//
// Input: n nodes numbered 0 to n - 1 and a list of undirected edges, each a pair {u, v}; a source
// node and a destination node. On LeetCode: 1 <= n <= 2 * 10^5, 0 <= edges.length <= 2 * 10^5, no
// edge joins a node to itself, and no pair of nodes is joined twice.
// Returns true if you can walk along edges from the source to the destination (a node always
// reaches itself).
//
// The one idea: one queue entry is a node that has just been reached. Start with the source and
// repeatedly take a node out and put all its not-yet-reached neighbours in, marking each one as it
// goes in. The nodes that ever enter the queue are exactly the nodes reachable from the source,
// so the answer is whether the destination is one of them. Marking a node when it is queued keeps
// it from being queued twice, so the walk ends even when the graph has cycles.
//
// Time: O(V + E) -- each node is queued once and each edge is looked at twice (once from each end).
// Space: O(V + E) -- the neighbour lists hold 2 * E entries, plus the visited array and the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class ValidPathSubmit {

	public static boolean validPath(int n, int[][] edges, int src, int dst) {
		List<Integer>[] g = graph(edges, n); // NEIGHBOURS: g[node] lists the nodes joined to it
		boolean[] visited = new boolean[n];
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(src);
		visited[src] = true; // RECORD: mark when queued
		while (!queue.isEmpty()) {
			int node = queue.poll(); // TAKE
			if (node == dst) {
				return true; // RETURN: the destination is reachable
			}
			for (int neighbour : g[node]) {
				if (!visited[neighbour]) { // SKIP: already reached
					queue.add(neighbour);
					visited[neighbour] = true;
				}
			}
		}
		return false; // RETURN: everything reachable was tried, dst is not among it
	}

	private static List<Integer>[] graph(int[][] edges, int n) {
		List<Integer>[] graph = new List[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int[] edge : edges) {
			int src = edge[0], dst = edge[1];
			graph[src].add(dst); // BOTH WAYS: the edge is undirected
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

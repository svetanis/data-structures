package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 323. Number of Connected Components in an Undirected Graph
//
// Input: n -- the number of nodes, labelled 0..n-1; edges -- pairs [a, b], each a wire between two
// nodes. Returns the number of connected components: groups of nodes reachable from one another.
// On LeetCode: 1 <= n <= 2000, 1 <= edges.length <= 5000, 0 <= a <= b < n, a != b, no edge repeated.
//
// The one idea: one element is one node. Build a list of neighbours for every node, then go through
// the nodes in order; a node not yet visited starts a new component, and a depth-first search from
// it visits every node reachable from it, so none of them can start another. The number of
// searches started is the number of components.
//
// Time: O(n + e) -- the lists take e steps to build; each node is visited once and each edge looked
// at twice.
// Space: O(n + e) -- the neighbour lists and the visited array; the recursion is at most n deep.
//
// Siblings: NumberOfConnectedComponents323 counts with union-find, no walking.

public final class NumberOfConnectedComponents323Dfs {

	public int countComponents(int n, int[][] edges) {
		int components = 0;
		boolean[] visited = new boolean[n]; // VISITED: reached by some search already
		List<Integer>[] graph = new ArrayList[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			graph[from].add(to); // BUILD: the wire is usable in both directions
			graph[to].add(from);
		}
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				dfs(i, graph, visited); // WALK: everything reachable from i
				components += 1; // COUNT: a new component
			}
		}
		return components;
	}

	private void dfs(int src, List<Integer>[] graph, boolean[] visited) {
		visited[src] = true; // MARK
		for (int node : graph[src]) {
			if (!visited[node]) {
				dfs(node, graph, visited); // WALK on
			}
		}
	}

	public static void main(String[] args) {
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
		NumberOfConnectedComponents323Dfs cc = new NumberOfConnectedComponents323Dfs();
		System.out.println(cc.countComponents(5, edges)); // 2 -- {0, 1, 2} and {3, 4}
	}
}

package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayList;
import java.util.List;

// 797. All Paths From Source to Target
//
// Input: graph -- a directed acyclic graph of n nodes numbered 0 to n - 1; graph[i] lists the nodes
// that node i has an edge to. Returns every path from node 0 to node n - 1, each path as the list of
// its nodes. On LeetCode: n == graph.length, 2 <= n <= 15, 0 <= graph[i][j] < n, graph[i][j] != i,
// the entries of graph[i] are distinct, and the graph is acyclic. The paths may come back in any order.
//
// The one idea: keep ONE list that holds the path walked so far, from node 0 to the node the search
// stands on. To follow an edge, add the neighbour to the end of the list and search from there; when
// that search is over, take the neighbour off again, so the list is back to what it was and the next
// edge starts from the same path. Reaching node n - 1 means the list is a finished path, and a copy
// of it goes into the answer (the list itself keeps changing). No visited set: every path is wanted,
// and the graph has no cycles, so the search always ends.
//
// Time: O(2^n * n) -- at most 2^(n - 2) paths, and each is copied once, n nodes at most.
// Space: O(2^n * n) -- the answer; the shared path and the recursion are O(n).
//
// Siblings: AllPathsFromSrcToTarget finds the same paths breadth first, with a queue of whole paths
// and a copy for every edge it follows.

public final class AllPathsFromSrcToTargetBacktracking {

	public static List<List<Integer>> allPaths(int[][] graph) {
		List<Integer> path = new ArrayList<>(); // PATH: the one shared path, from node 0
		path.add(0);
		List<List<Integer>> paths = new ArrayList<>();
		dfs(0, graph, path, paths);
		return paths; // RETURN: paths come out in the order the edges are listed
	}

	private static void dfs(int index, int[][] graph, List<Integer> path, List<List<Integer>> paths) {
		if (index == graph.length - 1) {
			paths.add(new ArrayList<>(path)); // RECORD: a copy, because path keeps changing
			return;
		}
		for (int node : graph[index]) {
			path.add(node); // CHOOSE: step along this edge
			dfs(node, graph, path, paths); // WALK: search on from the neighbour
			path.remove(path.size() - 1); // UNDO: step back so the next edge starts from the same path
		}
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 3 }, { 3 }, {} };
		System.out.println(allPaths(g1)); // [[0, 1, 3], [0, 2, 3]]

		int[][] g2 = { { 4, 3, 1 }, { 3, 2, 4 }, { 3 }, { 4 }, {} };
		System.out.println(allPaths(g2)); // [[0, 4], [0, 3, 4], [0, 1, 3, 4], [0, 1, 2, 3, 4], [0, 1, 4]]
	}
}

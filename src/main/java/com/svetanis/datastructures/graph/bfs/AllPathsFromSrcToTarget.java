package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

// 797. All Paths From Source to Target
//
// Input: graph -- a directed acyclic graph of n nodes numbered 0 to n - 1; graph[i] lists the nodes
// that node i has an edge to. Returns every path from node 0 to node n - 1, each path as the list of
// its nodes. On LeetCode: n == graph.length, 2 <= n <= 15, 0 <= graph[i][j] < n, graph[i][j] != i,
// the entries of graph[i] are distinct, and the graph is acyclic. The paths may come back in any order.
//
// The one idea: one queue entry is a whole path -- the nodes walked from node 0 so far, as a list --
// and not a single node. Take a path off the queue and look at its last node. If that is node n - 1,
// the path is finished and goes into the answer. Otherwise, for each edge out of the last node, make
// a copy of the path with that neighbour added and put the copy on the queue. There is no visited
// set, on purpose: this problem asks for every path, so the same node must be allowed to arrive
// again by another route. The graph has no cycles, so every path ends.
//
// Time: O(2^n * n) -- at most 2^(n - 2) paths (each middle node is on a path or not), and each path
// is copied one node at a time, up to n copies of up to n nodes.
// Space: O(2^n * n) -- the answer, and the queue holds partial paths of the same size.
//
// Siblings: AllPathsFromSrcToTargetBacktracking finds the same paths depth first with one shared
// path that grows and shrinks, so it copies a path only when it is finished.

public final class AllPathsFromSrcToTarget {

	public static List<List<Integer>> allPaths(int[][] graph) {
		int dst = graph.length - 1;
		Queue<List<Integer>> queue = new ArrayDeque<>(); // QUEUE: each entry is a path from node 0
		queue.add(Arrays.asList(0)); // START: the path that holds only node 0
		List<List<Integer>> paths = new ArrayList<>();
		while (!queue.isEmpty()) {
			List<Integer> path = queue.poll(); // TAKE: the oldest path
			int node = path.get(path.size() - 1); // the node the path has reached
			if (node == dst) {
				paths.add(path); // RECORD: a finished path
			} else {
				for (int neighbour : graph[node]) {
					List<Integer> next = new ArrayList<>(path); // COPY: the path stays as it is for the other edges
					next.add(neighbour); // EXTEND: along this edge
					queue.add(next);
				}
			}
		}
		return paths; // RETURN: paths come out shortest first
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 3 }, { 3 }, {} };
		System.out.println(allPaths(g1)); // [[0, 1, 3], [0, 2, 3]]

		int[][] g2 = { { 4, 3, 1 }, { 3, 2, 4 }, { 3 }, { 4 }, {} };
		System.out.println(allPaths(g2)); // [[0, 4], [0, 3, 4], [0, 1, 4], [0, 1, 3, 4], [0, 1, 2, 3, 4]]
	}
}

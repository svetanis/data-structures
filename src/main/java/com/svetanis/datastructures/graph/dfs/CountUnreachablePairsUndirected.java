package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 2316. Count Unreachable Pairs of Nodes in an Undirected Graph
//
// Input: n nodes numbered 0 .. n - 1; edges -- undirected edges [a, b]. Returns the number of
// pairs of nodes (a, b), a < b, that have no path between them. On LeetCode: 1 <= n <= 10^5,
// 0 <= edges.length <= 2 * 10^5, no self-loops, no repeated edges.
//
// The one idea: the nodes fall into components, groups with a path between any two of them. Two
// nodes are unreachable from each other exactly when they sit in different components. Walk the
// components one at a time. When a component of size s has just been counted, sum holds the
// number of nodes in all the components before it; each of its s nodes is unreachable from each
// of those sum nodes, so sum * s new pairs are added, and then sum grows by s. Every pair is
// counted once, when the later of its two components is counted.
//
// Known limit: dfs goes one call deeper per node along a path, so a path-shaped component of
// 10^4 nodes already overflows a default JVM stack, and LeetCode allows 10^5. The code stays
// recursive; a union-find or an explicit stack gives the sizes without the depth.
//
// Time: O(n + m) -- every node is entered once and every edge is looked at twice.
// Space: O(n + m) -- the adjacency lists and the visited set, plus the recursion.
//
// Siblings: CountUnreachablePairsUndirectedIterative measures the components with a stack, for
// very long paths.

public final class CountUnreachablePairsUndirected {

	public static long countPairs(int n, int[][] edges) {
		long sum = 0;
		long count = 0;
		Set<Integer> set = new HashSet<>();
		List<List<Integer>> lists = graph(n, edges);
		for (int node = 0; node < n; node++) {
			if (!set.contains(node)) {
				// size of current component
				int size = dfs(node, set, lists); // WALK: count the component
				count += sum * size; // TAKE: each of its nodes pairs with every earlier node
				// sum of component sizes visited so far
				sum += size; // RECORD
			}
		}
		return count; // RETURN: the pairs
	}

	private static int dfs(int node, Set<Integer> set, List<List<Integer>> lists) {
		if (set.contains(node)) {
			return 0;
		}
		set.add(node); // MARK
		int count = 1; // this node
		for (int next : lists.get(node)) {
			if (!set.contains(next)) {
				count += dfs(next, set, lists); // WALK: add the size reached through this edge
			}
		}
		return count;
	}

	private static List<List<Integer>> graph(int n, int[][] edges) {
		List<List<Integer>> lists = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			lists.add(new ArrayList<>());
		}
		for (int[] edge : edges) { // BUILD: both directions
			int node1 = edge[0];
			int node2 = edge[1];
			lists.get(node1).add(node2);
			lists.get(node2).add(node1);
		}
		return lists;
	}

	public static void main(String[] args) {
		int[][] edges1 = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
		System.out.println(countPairs(3, edges1)); // 0

		int[][] edges2 = { { 0, 2 }, { 0, 5 }, { 2, 4 }, { 1, 6 }, { 5, 4 } };
		System.out.println(countPairs(7, edges2)); // 14
	}
}

package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 2316. Count Unreachable Pairs of Nodes in an Undirected Graph

public final class CountUnreachablePairsUndirected {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	public static long countPairs(int n, int[][] edges) {
		long sum = 0;
		long count = 0;
		Set<Integer> set = new HashSet<>();
		List<List<Integer>> lists = graph(n, edges);
		for (int node = 0; node < n; node++) {
			if (!set.contains(node)) {
				// size of current component
				int size = dfs(node, set, lists);
				count += sum * size;
				// sum of component sizes visited so far
				sum += size;
			}
		}
		return count;
	}

	private static int dfs(int node, Set<Integer> set, List<List<Integer>> lists) {
		if (set.contains(node)) {
			return 0;
		}
		set.add(node);
		int count = 1;
		for (int next : lists.get(node)) {
			if (!set.contains(next)) {
				count += dfs(next, set, lists);
			}
		}
		return count;
	}

	private static List<List<Integer>> graph(int n, int[][] edges) {
		List<List<Integer>> lists = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			lists.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
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

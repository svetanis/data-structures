package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 2316. Count Unreachable Pairs of Nodes in an Undirected Graph
//
// Input: n nodes numbered 0 .. n - 1; edges -- undirected edges [a, b]. Returns the number of
// pairs of nodes (a, b), a < b, that have no path between them. On LeetCode: 1 <= n <= 10^5,
// 0 <= edges.length <= 2 * 10^5, no self-loops, no repeated edges.
//
// The one idea: the same counting as CountUnreachablePairsUndirected. Two nodes are unreachable
// from each other exactly when they sit in different components; when a component of size s has
// just been measured, sum holds the number of nodes in all the components before it, so sum * s
// new pairs are added, and then sum grows by s. Only the measuring changes: a stack of nodes
// replaces the recursion. One stack entry is a node of the current component whose neighbours
// are not looked at yet; a node is marked when it is pushed, so each is counted once.
//
// Time: O(n + m) -- every node is pushed once and every edge is looked at twice.
// Space: O(n + m) -- the adjacency lists, the marks and the stack.
//
// Siblings: CountUnreachablePairsUndirected measures each component with a recursion, which
// overflows a default Java stack on a very long path.

public final class CountUnreachablePairsUndirectedIterative {

	public static long countPairs(int n, int[][] edges) {
		long sum = 0;
		long count = 0;
		boolean[] visited = new boolean[n];
		List<List<Integer>> lists = graph(n, edges);
		for (int node = 0; node < n; node++) {
			if (!visited[node]) {
				int size = size(node, visited, lists); // WALK: count the component
				count += sum * size; // TAKE: each of its nodes pairs with every earlier node
				sum += size; // RECORD: nodes in the components measured so far
			}
		}
		return count; // RETURN: the pairs
	}

	private static int size(int start, boolean[] visited, List<List<Integer>> lists) {
		Deque<Integer> stack = new ArrayDeque<>();
		visited[start] = true; // MARK: when pushed
		stack.push(start);
		int count = 0;
		while (!stack.isEmpty()) {
			int node = stack.pop(); // TAKE: one more node of the component
			count++;
			for (int next : lists.get(node)) {
				if (!visited[next]) {
					visited[next] = true; // MARK
					stack.push(next); // WALK: same component
				}
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

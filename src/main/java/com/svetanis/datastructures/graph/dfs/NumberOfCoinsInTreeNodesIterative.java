package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

// 2973. Find Number of Coins to Place in Tree Nodes
//
// Input: edges -- the n - 1 edges of an undirected tree whose nodes are 0 to n - 1 and whose root
// is node 0; cost[i] -- the cost of node i. Node i gets coins by a rule on the subtree below it
// (node i and everything under it): with fewer than 3 nodes in the subtree it gets 1 coin;
// otherwise it gets the largest product of the costs of any 3 different nodes of the subtree, or
// 0 if that largest product is negative. Returns the coins of every node. On LeetCode: 2 <= n <=
// 2 * 10^4, edges.length == n - 1, 1 <= |cost[i]| <= 10^4.
//
// The one idea: the same SHORT LIST per node as NumberOfCoinsInTreeNodes -- at most five costs
// standing for the whole subtree, the two smallest and the three largest, because the best product
// of three is either the three largest or the largest times the two smallest. A node's list needs
// its children's lists first. The recursion gets that order by returning from the children before
// finishing the node; here two loops get it without deep calls. The first walks down from node 0
// with a stack and writes the nodes in the order they are reached, together with each node's
// parent. Every node is reached after its parent, so going through that order BACKWARDS finishes
// every child before its parent. The second loop does exactly that: it merges the node's own cost
// into the list its children have already left in lists[node], sorts, reads the coins, cuts the
// list to five and adds it to the parent's list.
//
// Time: O(n log n) -- each list holds at most 1 + 5 * (children) costs, so all the lists together
// hold O(n) costs, and each is sorted once.
// Space: O(n) -- the tree, the order, the parent array and the lists.
//
// Siblings: NumberOfCoinsInTreeNodes is the recursive version, which overflows a default Java stack
// on a very tall tree.

public final class NumberOfCoinsInTreeNodesIterative {

	public long[] placeCoins(int[][] edges, int[] costs) {
		int n = costs.length;
		List<List<Integer>> graph = graph(n, edges);
		long[] maxProduct = new long[n];
		Arrays.fill(maxProduct, 1); // BASE: a subtree of fewer than 3 nodes gets 1 coin
		int[] parent = new int[n];
		int[] order = order(graph, parent);
		List<List<Integer>> lists = new ArrayList<>(); // lists[node]: the short lists its children handed up
		for (int node = 0; node < n; node++) {
			lists.add(new ArrayList<>());
		}
		for (int i = n - 1; i >= 0; i--) { // BACKWARDS: every child before its parent
			int node = order[i];
			List<Integer> list = lists.get(node);
			list.add(costs[node]); // TAKE: this node's own cost
			Collections.sort(list); // SORT: smallest first, so both ends are easy to read
			int size = list.size();
			if (size >= 3) {
				maxProduct[node] = maxProduct(list); // RECORD: coins for this node
			}
			if (size >= 5) {
				list = trimmed(list); // CUT: only two smallest and three largest can matter above
			}
			if (parent[node] != -1) {
				lists.get(parent[node]).addAll(list); // MERGE: hand the short list up
			}
		}
		return maxProduct; // RETURN: coins of every node
	}

	// the nodes in the order a walk from node 0 reaches them; fills parent[] on the way
	private int[] order(List<List<Integer>> graph, int[] parent) {
		int n = graph.size();
		int[] order = new int[n];
		int count = 0;
		Deque<Integer> stack = new ArrayDeque<>();
		parent[0] = -1;
		stack.push(0);
		while (!stack.isEmpty()) {
			int node = stack.pop();
			order[count++] = node; // RECORD: reached after its parent
			for (int neighbor : graph.get(node)) {
				if (neighbor != parent[node]) { // SKIP: the way back up
					parent[neighbor] = node;
					stack.push(neighbor); // WALK
				}
			}
		}
		return order;
	}

	private List<Integer> trimmed(List<Integer> list) {
		int n = list.size();
		List<Integer> trimmed = new ArrayList<>();
		trimmed.addAll(list.subList(0, 2)); // the two smallest
		trimmed.addAll(list.subList(n - 3, n)); // the three largest
		return trimmed;
	}

	private long maxProduct(List<Integer> list) {
		int n = list.size();
		int largest = list.get(n - 1);
		long prod1 = (long) largest * list.get(n - 2) * list.get(n - 3); // the three largest
		long prod2 = (long) largest * list.get(0) * list.get(1); // the largest and the two smallest
		return Math.max(0, Math.max(prod1, prod2)); // a negative best is 0 coins
	}

	private List<List<Integer>> graph(int n, int[][] edges) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) { // BUILD: each edge is listed at both ends
			int node1 = edge[0];
			int node2 = edge[1];
			graph.get(node1).add(node2);
			graph.get(node2).add(node1);
		}
		return graph;
	}

	public static void main(String[] args) {
		NumberOfCoinsInTreeNodesIterative ctn = new NumberOfCoinsInTreeNodesIterative();
		int[][] edges1 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 } };
		int[] cost1 = { 1, 2, 3, 4, 5, 6 };
		System.out.println(Arrays.toString(ctn.placeCoins(edges1, cost1))); // [120, 1, 1, 1, 1, 1]

		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 2, 6 }, { 2, 7 }, { 2, 8 } };
		int[] cost2 = { 1, 4, 2, 3, 5, 7, 8, -4, 2 };
		System.out.println(Arrays.toString(ctn.placeCoins(edges2, cost2))); // [280, 140, 32, 1, 1, 1, 1, 1, 1]

		int[][] edges3 = { { 0, 1 }, { 0, 2 } };
		int[] cost3 = { 1, 2, -2 };
		System.out.println(Arrays.toString(ctn.placeCoins(edges3, cost3))); // [0, 1, 1]
	}
}

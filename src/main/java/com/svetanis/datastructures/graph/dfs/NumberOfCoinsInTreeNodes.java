package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
// The one idea: one dfs call answers for one node, and returns a SHORT LIST standing for the whole
// subtree: at most five costs -- the two smallest and the three largest. That is enough because the
// best product of three costs is always either the three largest (three big numbers), or the
// largest times the two smallest (two big negative numbers multiply into a big positive); no
// other triple can beat those two. A node merges its own cost with the short lists of its
// children, sorts the result, reads the coins from the ends of the sorted list, and cuts the list
// back to five before handing it up. Products are taken as long: 10^4 * 10^4 * 10^4 = 10^12
// does not fit an int.
//
// Time: O(n log n) -- each list holds at most 1 + 5 * (children) costs, so all the lists together
// hold O(n) costs, and each is sorted once.
// Space: O(n) -- the tree and the lists. The recursion is as deep as the tree is tall: a tree that
// is one long chain of 20,000 nodes needs 20,000 nested calls, more than a default Java stack
// holds (run with a larger -Xss); LeetCode's stack is larger.
//
// Siblings: NumberOfCoinsInTreeNodesIterative gets the children-before-parent order from two loops
// instead of the recursion, for very tall trees.

public final class NumberOfCoinsInTreeNodes {

	private List<List<Integer>> graph;

	public long[] placeCoins(int[][] edges, int[] costs) {
		int n = costs.length;
		this.graph = graph(n, edges);
		long[] maxProduct = new long[n];
		Arrays.fill(maxProduct, 1); // BASE: a subtree of fewer than 3 nodes gets 1 coin
		dfs(0, -1, costs, maxProduct);
		return maxProduct; // RETURN: coins of every node
	}

	private List<Integer> dfs(int root, int parent, int[] costs, long[] maxProduct) {
		List<Integer> list = new ArrayList<>();
		list.add(costs[root]); // TAKE: this node's own cost
		for (int neighbor : this.graph.get(root)) {
			if (neighbor != parent) { // SKIP: the way back up
				list.addAll(dfs(neighbor, root, costs, maxProduct)); // MERGE: a child's short list
			}
		}
		Collections.sort(list); // SORT: smallest first, so both ends are easy to read
		int size = list.size();
		if (size >= 3) {
			maxProduct[root] = maxProduct(list); // RECORD: coins for this node
		}
		if (size >= 5) {
			list = trimmed(list); // CUT: only two smallest and three largest can matter above
		}
		return list;
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
		NumberOfCoinsInTreeNodes ctn = new NumberOfCoinsInTreeNodes();
		int[][] edges1 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 } };
		int[] cost1 = { 1, 2, 3, 4, 5, 6 };
		print(ctn.placeCoins(edges1, cost1)); // 120 1 1 1 1 1

		NumberOfCoinsInTreeNodes ctn2 = new NumberOfCoinsInTreeNodes();
		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 2, 6 }, { 2, 7 }, { 2, 8 } };
		int[] cost2 = { 1, 4, 2, 3, 5, 7, 8, -4, 2 };
		print(ctn2.placeCoins(edges2, cost2)); // 280 140 32 1 1 1 1 1 1

		NumberOfCoinsInTreeNodes ctn3 = new NumberOfCoinsInTreeNodes();
		int[][] edges3 = { { 0, 1 }, { 0, 2 } };
		int[] cost3 = { 1, 2, -2 };
		print(ctn3.placeCoins(edges3, cost3)); // 0 1 1

	}

	private static void print(long[] a) {
		for (long e : a) {
			System.out.print(e + " ");
		}
		System.out.println();
	}
}

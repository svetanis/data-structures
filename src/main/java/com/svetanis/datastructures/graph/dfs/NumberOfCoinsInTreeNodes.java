package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 2973. Find Number of Coins to Place in Tree Nodes

public final class NumberOfCoinsInTreeNodes {
	// Time Complexity: O(n log n)
	// Space Complexity: O(n)

	private List<List<Integer>> graph;

	public long[] placeCoins(int[][] edges, int[] costs) {
		int n = costs.length;
		this.graph = graph(n, edges);
		long[] maxProduct = new long[n];
		Arrays.fill(maxProduct, 1);
		dfs(0, -1, costs, maxProduct);
		return maxProduct;
	}

	private List<Integer> dfs(int root, int parent, int[] costs, long[] maxProduct) {
		List<Integer> list = new ArrayList<>();
		list.add(costs[root]);
		for (int neighbor : this.graph.get(root)) {
			if (neighbor != parent) {
				list.addAll(dfs(neighbor, root, costs, maxProduct));
			}
		}
		Collections.sort(list);
		int size = list.size();
		if (size >= 3) {
			maxProduct[root] = maxProduct(list);
		}
		if (size >= 5) {
			list = trimmed(list);
		}
		return list;
	}

	private List<Integer> trimmed(List<Integer> list) {
		int n = list.size();
		List<Integer> trimmed = new ArrayList<>();
		trimmed.addAll(list.subList(0, 2));
		trimmed.addAll(list.subList(n - 3, n));
		return trimmed;
	}

	private long maxProduct(List<Integer> list) {
		int n = list.size();
		int largest = list.get(n - 1);
		long prod1 = (long) largest * list.get(n - 2) * list.get(n - 3);
		long prod2 = (long) largest * list.get(0) * list.get(1);
		return Math.max(0, Math.max(prod1, prod2));
	}

	private List<List<Integer>> graph(int n, int[][] edges) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
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
		print(ctn.placeCoins(edges1, cost1)); // 120,1,1,1,1,1

		NumberOfCoinsInTreeNodes ctn2 = new NumberOfCoinsInTreeNodes();
		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 2, 6 }, { 2, 7 }, { 2, 8 } };
		int[] cost2 = { 1, 4, 2, 3, 5, 7, 8, -4, 2 };
		print(ctn2.placeCoins(edges2, cost2)); // 280,140,32,1,1,1,1,1,1

		NumberOfCoinsInTreeNodes ctn3 = new NumberOfCoinsInTreeNodes();
		int[][] edges3 = { { 0, 1 }, { 0, 2 } };
		int[] cost3 = { 1, 2, -2 };
		print(ctn3.placeCoins(edges3, cost3)); // 0, 1, 1

	}

	private static void print(long[] a) {
		for (long e : a) {
			System.out.print(e + " ");
		}
		System.out.println();
	}
}

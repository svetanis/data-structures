package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 1168. Optimize Water Distribution in a Village

public final class WaterDistributionRank {
	// Time Complexity: O(E * log E + N)
	// Space Complexity: O(E + N)

	private int[] rank;
	private int[] parent;

	public int minCost(int n, int[] wells, int[][] pipes) {
		initParent(n);
		int[][] connections = connections(n, wells, pipes);
		// sort connections by cost
		Arrays.sort(connections, (a, b) -> a[2] - b[2]);
		int totalCost = 0;
		for (int[] connection : connections) {
			if (union(connection[0], connection[1])) {
				totalCost += connection[2];
			}
		}
		return totalCost;
	}

	private void initParent(int n) {
		this.rank = new int[n + 1];
		this.parent = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	private int[][] connections(int n, int[] wells, int[][] pipes) {
		int index = 0;
		int[][] grid = new int[n + pipes.length][3];
		for (int[] pipe : pipes) {
			grid[index++] = pipe;
		}
		for (int i = 0; i < n; i++) {
			grid[index++] = new int[] { 0, i + 1, wells[i] };
		}
		return grid;
	}

	private boolean union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px == py) {
			return false;
		}
		if (rank[px] > rank[py]) {
			parent[py] = px;
		} else if (rank[px] < rank[py]) {
			parent[px] = py;
		} else {
			parent[py] = px;
			rank[px] += 1;
		}
		return true;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	public static void main(String[] args) {
		WaterDistributionRank wdr = new WaterDistributionRank();
		int[] wells = { 1, 2, 2 };
		int[][] pipes = { { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(wdr.minCost(3, wells, pipes)); // 3

		int[] wells2 = { 1, 1 };
		int[][] pipes2 = { { 1, 2, 1 }, { 1, 2, 2 } };
		System.out.println(wdr.minCost(2, wells2, pipes2)); // 2
	}
}
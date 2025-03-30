package com.svetanis.datastructures.graph.unionfind;

// 1319. Number of Operations to Make Network Connected

public final class OperationsToMakeNetworkConnected {
	// Time Complexity: O(connections * log n)

	private int[] parent;

	public int network(int n, int[][] connections) {
		init(n);
		int redundant = 0;
		for (int[] connection : connections) {
			int px = find(connection[0]);
			int py = find(connection[1]);
			if (px != py) {
				parent[px] = py;
				n -= 1;
			} else {
				redundant++;
			}
		}
		return n - 1 > redundant ? -1 : n - 1;
	}

	private void init(int n) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	public static void main(String[] args) {
		OperationsToMakeNetworkConnected mnc = new OperationsToMakeNetworkConnected();
		int[][] g1 = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
		System.out.println(mnc.network(4, g1)); // 1

		int[][] g2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };
		System.out.println(mnc.network(6, g2)); // 2

		int[][] g3 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 } };
		System.out.println(mnc.network(6, g3)); // -1
	}
}

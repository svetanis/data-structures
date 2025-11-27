package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.Comparator;

// 1135. Connecting Cities With Minimum Cost

public final class ConnectCitiesMinCost {
	// Time Complexity: O(m log m)
	// Space Complexity: O(n)

	private int[] parent;

	public int minCost(int n, int[][] connections) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		Arrays.sort(connections, Comparator.comparing(c -> c[2]));
		int totalCost = 0;
		int remainingComponents = n;
		for (int[] connection : connections) {
			int src = connection[0] - 1;
			int dst = connection[1] - 1;
			int cost = connection[2];
			if (find(src) == find(dst)) {
				continue;
			}
			parent[find(src)] = find(dst);
			totalCost += cost;
			remainingComponents -= 1;
			if (remainingComponents == 1) {
				return totalCost;
			}
		}
		return -1;
	}

	private int find(int node) {
		if (parent[node] != node) {
			parent[node] = find(parent[node]);
		}
		return parent[node];
	}

	public static void main(String[] args) {
		ConnectCitiesMinCost ccc = new ConnectCitiesMinCost();
		int[][] g = { { 1, 2, 5 }, { 1, 3, 6 }, { 2, 3, 1 } };
		System.out.println(ccc.minCost(3, g));

		int[][] g2 = { { 1, 2, 3 }, { 3, 4, 4 }, { 1, 4, 7 }, { 2, 3, 5 } };
		System.out.println(ccc.minCost(4, g2)); // 12
	}
}

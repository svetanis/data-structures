package com.svetanis.datastructures.graph.advanced;

import java.util.Arrays;

// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
// Dijkstra's Algorithm 

public final class CityWithMinNeighborsFWA {
	// Time Complexity: O(n^3)
	// Space Complexity: O(n^2)

	private static final int INF = (int) 1e9 + 7;

	private int[][] dist;

	public int city(int n, int[][] edges, int threshold) {
		init(n, edges);
		floyd(n, dist);
		int minReachable = n;
		int index = -1;
		for (int city = 0; city < n; city++) {
			int count = 0;
			for (int j = 0; j < n; j++) {
				if (city == j) {
					continue;
				}
				if (dist[city][j] <= threshold) {
					count += 1;
				}
			}
			if (count <= minReachable) {
				minReachable = count;
				index = city;
			}
		}
		return index;
	}

	private void floyd(int n, int[][] dist) {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
	}

	private void init(int n, int[][] edges) {
		this.dist = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			int weight = edge[2];
			dist[from][to] = weight;
			dist[to][from] = weight;
		}
	}

	public static void main(String[] args) {
		CityWithMinNeighborsFWA cmn = new CityWithMinNeighborsFWA();
		int[][] g1 = { { 0, 1, 3 }, { 1, 2, 1 }, { 1, 3, 4 }, { 2, 3, 1 } };
		System.out.println(cmn.city(4, g1, 4)); // 3

		int[][] g2 = { { 0, 1, 2 }, { 0, 4, 8 }, { 1, 2, 3 }, { 1, 4, 2 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(cmn.city(5, g2, 2)); // 0
	}
}

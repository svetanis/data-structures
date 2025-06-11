package com.svetanis.datastructures.graph.advanced;

import java.util.Arrays;

// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
// Dijkstra's Algorithm 

public final class CityWithMinNeighbors {
	// Time Complexity: O(n^3)
	// Space Complexity: O(n^2)

	private static final int INF = 1 << 30;

	private int n;
	private int threshold;
	private int[] dist;
	private boolean[] visited;
	private int[][] graph;

	public int city(int n, int[][] edges, int threshold) {
		this.n = n;
		this.threshold = threshold;
		init(n, edges);
		int min = INF;
		int index = n;
		for (int city = n - 1; city >= 0; city--) {
			int reachable = dijkstra(city);
			if (min > reachable) {
				min = reachable;
				index = city;
			}
		}
		return index;
	}

	private void init(int n, int[][] edges) {
		this.dist = new int[n];
		this.visited = new boolean[n];
		this.graph = new int[n][n];
		for (int[] row : graph) {
			Arrays.fill(row, INF);
		}
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			int weight = edge[2];
			graph[from][to] = weight;
			graph[to][from] = weight;
		}
	}

	private int dijkstra(int src) {
		Arrays.fill(visited, false);
		Arrays.fill(dist, INF);
		dist[src] = 0;
		for (int i = 0; i < n; i++) {
			int closest = -1;
			for (int j = 0; j < n; j++) {
				if (!visited[j] && (closest == -1 || dist[closest] > dist[j])) {
					closest = j;
				}
			}
			visited[closest] = true;
			for (int j = 0; j < n; j++) {
				int d = dist[closest] + graph[closest][j];
				dist[j] = Math.min(dist[j], d);
			}
		}
		int count = 0;
		for (int d : dist) {
			if (d <= threshold) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		CityWithMinNeighbors cmn = new CityWithMinNeighbors();
		int[][] g1 = { { 0, 1, 3 }, { 1, 2, 1 }, { 1, 3, 4 }, { 2, 3, 1 } };
		System.out.println(cmn.city(4, g1, 4)); // 3

		int[][] g2 = { { 0, 1, 2 }, { 0, 4, 8 }, { 1, 2, 3 }, { 1, 4, 2 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(cmn.city(5, g2, 2)); // 0
	}
}

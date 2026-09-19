package com.svetanis.datastructures.graph.shortestpath;

import java.util.Arrays;

// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
// Dijkstra's Algorithm 

public final class CityWithMinNeighborsDijkstra {
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
			// keep the CHEAPER road, not the last one read. LC 1334 promises
			// each pair of cities appears once, so plain assignment agrees --
			// but a matrix has one slot per pair and silently drops the other
			graph[from][to] = Math.min(graph[from][to], weight);
			graph[to][from] = graph[from][to];
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
			if (dist[closest] == INF) {
				// the nearest unvisited city is already unreachable, so every
				// city still left is too. going on would compute INF + INF,
				// and two of these overflow past Integer.MAX_VALUE into a
				// large NEGATIVE number that then reads as a very short path
				break;
			}
			visited[closest] = true;
			for (int j = 0; j < n; j++) {
				if (graph[closest][j] == INF) {
					// no road between these two, so there is nothing to add
					continue;
				}
				int d = dist[closest] + graph[closest][j];
				dist[j] = Math.min(dist[j], d);
			}
		}
		int count = 0;
		for (int city = 0; city < n; city++) {
			// a city is not its own neighbour: dist[src] is 0, which clears
			// every threshold, so counting it adds one to every city's total
			if (city != src && dist[city] <= threshold) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		CityWithMinNeighborsDijkstra cmn = new CityWithMinNeighborsDijkstra();
		int[][] g1 = { { 0, 1, 3 }, { 1, 2, 1 }, { 1, 3, 4 }, { 2, 3, 1 } };
		System.out.println(cmn.city(4, g1, 4)); // 3

		int[][] g2 = { { 0, 1, 2 }, { 0, 4, 8 }, { 1, 2, 3 }, { 1, 4, 2 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(cmn.city(5, g2, 2)); // 0

		// four cities and one road. cities 0 and 2 reach nobody, and the
		// tie goes to the larger label, so the answer is 2. every city
		// with no road out used to relax INF against INF, and the sum
		// wrapped negative, so all four looked equally reachable and the
		// first one tried won -- this printed 3
		int[][] g3 = { { 1, 3, 4 } };
		System.out.println(cmn.city(4, g3, 9)); // 2

		// the road 0-1 is listed twice, once cheap and once dear. LC 1334
		// promises that never happens, and the matrix has one slot for the
		// pair, so writing whichever came last kept the 9 and answered 0
		int[][] g4 = { { 0, 1, 1 }, { 0, 1, 9 }, { 1, 2, 1 } };
		System.out.println(cmn.city(3, g4, 2)); // 2
	}
}

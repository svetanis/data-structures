package com.svetanis.datastructures.graph.shortestpath;

import java.util.Arrays;

// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
//
// Input: n -- the number of cities, labelled 0 to n - 1; edges[i] = { from, to, weight } -- a
// two-way road of that length; distanceThreshold -- the farthest a city may be to count as a
// neighbour. Returns the city that can reach the fewest other cities within the threshold along
// roads; on a tie, the city with the larger label. On LeetCode: 2 <= n <= 100,
// 1 <= edges.length <= n * (n - 1) / 2, from < to, 1 <= weight, distanceThreshold <= 10^4, every
// pair of cities appears at most once.
//
// The one idea: for each city in turn, find its shortest road distance to every other city, then
// count the cities within the threshold. dist[c] is the shortest distance found so far from the
// city being tried to city c. Each round finds the unvisited city with the smallest dist, closest,
// whose distance is now final, and offers every road out of it as a shorter way to its far end.
// The graph is a matrix with one slot per pair, so finding closest is a scan of n slots and the
// whole search is n rounds of n + n steps.
//
// Time: O(n^3) -- n starting cities, each running n rounds that scan n slots twice.
// Space: O(n^2) -- the matrix of road lengths.
//
// Siblings: CityWithMinNeighborsFloydWarshall computes every pair's distance in one triple loop
// and counts afterwards.

public final class CityWithMinNeighborsDijkstra {

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
		for (int city = n - 1; city >= 0; city--) { // WALK: larger labels first, so a tie keeps the larger
			int reachable = dijkstra(city);
			if (min > reachable) {
				min = reachable;
				index = city;
			}
		}
		return index; // RETURN: the city with the fewest neighbours
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
			// BUILD: keep the cheaper road, not the last one read. LC 1334 lists each pair once,
			// so plain assignment agrees there, but a matrix has one slot per pair and would
			// silently drop the cheaper of two roads between the same pair
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
					closest = j; // PICK: the unvisited city nearest the start
				}
			}
			if (dist[closest] == INF) {
				// STOP: the nearest unvisited city is unreachable, so every city left is too.
				// Going on would compute INF + INF, which overflows past Integer.MAX_VALUE
				// into a large negative number that then reads as a very short road
				break;
			}
			visited[closest] = true;
			for (int j = 0; j < n; j++) {
				if (graph[closest][j] == INF) {
					// SKIP: no road between these two, so there is nothing to add
					continue;
				}
				int d = dist[closest] + graph[closest][j];
				dist[j] = Math.min(dist[j], d); // RELAX: a shorter way to j through closest
			}
		}
		int count = 0;
		for (int city = 0; city < n; city++) {
			// COUNT: a city is not its own neighbour. dist[src] is 0, which clears every
			// threshold, so counting it would add one to every city's total
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

		// four cities and one road: cities 0 and 2 reach nobody and the tie goes to the
		// larger label. Cities with no road out are where INF meets INF
		int[][] g3 = { { 1, 3, 4 } };
		System.out.println(cmn.city(4, g3, 9)); // 2

		// the road 0-1 is listed twice, once cheap and once dear. LC 1334 never does this;
		// the cheaper road is kept, so cities 0, 1, 2 are all within 2 and the tie goes to 2
		int[][] g4 = { { 0, 1, 1 }, { 0, 1, 9 }, { 1, 2, 1 } };
		System.out.println(cmn.city(3, g4, 2)); // 2
	}
}

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
// The one idea: dist[i][j] is the shortest road distance found so far from city i to city j. The
// outer loop adds one city k at a time as a possible stop on the way: after the pass for k,
// dist[i][j] is the shortest distance that stops only at cities 0 to k. After the last pass it is
// the true shortest distance for every pair, and counting the cities within the threshold is a
// scan of one row.
//
// Time: O(n^3) -- the triple loop over k, i and j.
// Space: O(n^2) -- the table of distances.
//
// Siblings: CityWithMinNeighborsDijkstra runs a single-source search from each city in turn
// instead.

public final class CityWithMinNeighborsFloydWarshall {

	// INF + INF = 2,000,000,014 still fits in an int
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
					count += 1; // COUNT: j is a neighbour of city
				}
			}
			if (count <= minReachable) {
				minReachable = count; // TAKE: <= lets a later, larger label win a tie
				index = city;
			}
		}
		return index; // RETURN: the city with the fewest neighbours
	}

	private void floyd(int n, int[][] dist) {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j]; // RELAX: a shorter way from i to j through k
					}
				}
			}
		}
	}

	private void init(int n, int[][] edges) {
		this.dist = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0; // BASE: a city is 0 away from itself
		}
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			int weight = edge[2];
			// BUILD: keep the cheaper road, not the last one read. LC 1334 lists each pair once,
			// so plain assignment agrees there, but a matrix has one slot per pair and would
			// silently drop the cheaper of two roads between the same pair
			dist[from][to] = Math.min(dist[from][to], weight);
			dist[to][from] = dist[from][to];
		}
	}

	public static void main(String[] args) {
		CityWithMinNeighborsFloydWarshall cmn = new CityWithMinNeighborsFloydWarshall();
		int[][] g1 = { { 0, 1, 3 }, { 1, 2, 1 }, { 1, 3, 4 }, { 2, 3, 1 } };
		System.out.println(cmn.city(4, g1, 4)); // 3

		int[][] g2 = { { 0, 1, 2 }, { 0, 4, 8 }, { 1, 2, 3 }, { 1, 4, 2 }, { 2, 3, 1 }, { 3, 4, 1 } };
		System.out.println(cmn.city(5, g2, 2)); // 0

		// the road 0-1 is listed twice, once cheap and once dear. LC 1334 never does this;
		// the cheaper road is kept, so cities 0, 1, 2 are all within 2 and the tie goes to 2
		int[][] g4 = { { 0, 1, 1 }, { 0, 1, 9 }, { 1, 2, 1 } };
		System.out.println(cmn.city(3, g4, 2)); // 2
	}
}

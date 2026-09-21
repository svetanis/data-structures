package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.svetanis.datastructures.graph.unionfind.DisjointSet;

// 1584. Min Cost to Connect All Points
// Kruskal's Algorithm with Union-Find -- the wrong tool for this problem, kept to be
// compared against the three Prims. Every pair of points is an edge, so Kruskal must
// build and sort n(n-1)/2 of them before it starts; Prim never holds an edge at all.
// Write this shape when the edges ARRIVE as a list: see ConnectCitiesMinCost (LC 1135).

public final class MinCostToConnectAllPointsKruskal {
	// Time Complexity: O(n^2 log n) -- n^2/2 edges built, then sorted
	// Space Complexity: O(n^2) -- the edge list
	// The union-find walk itself is the cheap part; the sort is nearly all of the time.
	// For this problem prefer MinCostToConnectAllPointsPrimScan.

	public int minCost(int[][] points) {
		int n = points.length;
		List<int[]> edges = edges(points);
		int total = 0;
		int count = 0;
		DisjointSet ds = new DisjointSet(n);
		// cheapest first, keeping only the edges that join two separate groups
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1], dist = edge[2];
			// union asks and acts in one call: true means they were apart and are now joined,
			// false means they were already connected and this edge would close a cycle
			if (ds.union(from, to)) {
				total += dist;
				count++;
				// a tree on n points has exactly n - 1 edges, so there is nothing left to find
				if (count == n - 1) {
					break;
				}
			}
		}
		// no -1 guard here: every pair of points is an edge, so the graph is always
		// connected. ConnectCitiesMinCost needs one, because its edges are given.
		return total;
	}

	private List<int[]> edges(int[][] points) {
		int n = points.length;
		// n(n-1)/2 rows: {one point's index, the other's, the distance between them}
		List<int[]> edges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int dist = mdist(points, i, j);
				edges.add(new int[] { i, j, dist });
			}
		}
		Collections.sort(edges, Comparator.comparing(e -> e[2]));
		return edges;
	}

	private int mdist(int[][] points, int u, int v) {
		return Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsKruskal mcc = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsKruskal mcc2 = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc2.minCost(points2)); // 18
	}

}

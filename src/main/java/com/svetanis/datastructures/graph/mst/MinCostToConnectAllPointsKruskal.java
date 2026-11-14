package com.svetanis.datastructures.graph.mst;

import com.svetanis.datastructures.graph.unionfind.DisjointSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 1584. Min Cost to Connect All Points
//
// Input: points, a list of [x, y] positions. On LeetCode: 1 <= points.length <= 1000,
// -10^6 <= x, y <= 10^6, and all points are different.
// Returns: the least total length of segments that joins every point to every other, directly or
// through other points. The length of a segment is the Manhattan distance |x1 - x2| + |y1 - y2|.
// A cheapest such set of segments is a minimum spanning tree (MST): n - 1 segments, no loops.
//
// Kruskal's Algorithm with Union-Find -- the wrong tool for this problem, kept to be
// compared against the three Prims. Every pair of points is an edge, so Kruskal must
// build and sort n(n-1)/2 of them before it starts; Prim never holds an edge at all.
// Write this shape when the edges ARRIVE as a list: see ConnectCitiesMinCost (LC 1135).
//
// The one idea: take the segments from the shortest up, and keep a segment only if its two ends
// are not already connected through the segments kept so far; a segment between points that are
// already connected would close a loop and is never needed. The shortest segment is always safe
// to keep, and after it the shortest segment that does not close a loop is safe again, which is
// why this greedy choice never has to be undone. A union-find answers "already connected?" and
// joins two groups in one call: union returns true when the ends were in different groups.
//
// Time: O(n^2 log n) -- n(n-1)/2 edges built, then sorted (log of n^2 is 2 log n); the
// union-find walk itself is the cheap part, the sort is nearly all of the time.
// Space: O(n^2) -- the edge list.
//
// Siblings: MinCostToConnectAllPointsKruskalSubmit is the same algorithm with its own nested
// union-find (by rank) so it can be pasted into a judge. For this problem prefer
// MinCostToConnectAllPointsPrimScan; PrimMatrix and PrimHeap are the other two Prims.

public final class MinCostToConnectAllPointsKruskal {

	public int minCost(int[][] points) {
		int n = points.length;
		List<int[]> edges = edges(points); // EDGES: every pair, sorted lightest first
		int total = 0;
		int count = 0;
		DisjointSet ds = new DisjointSet(n);
		// cheapest first, keeping only the edges that join two separate groups
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1], dist = edge[2];
			// union asks and acts in one call: true means they were apart and are now joined,
			// false means they were already connected and this edge would close a cycle
			if (ds.union(from, to)) {
				total += dist; // TAKE: this segment joins two groups
				count++;
				// a tree on n points has exactly n - 1 edges, so there is nothing left to find
				if (count == n - 1) {
					break; // STOP: the tree is complete
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
				edges.add(new int[] { i, j, dist }); // BUILD: one edge per unordered pair
			}
		}
		Collections.sort(edges, Comparator.comparing(e -> e[2])); // SORT: lightest first
		return edges;
	}

	private int mdist(int[][] points, int u, int v) {
		return Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]); // RETURN: Manhattan distance
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsKruskal mcc = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsKruskal mcc2 = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc2.minCost(points2)); // 18

		int[][] points3 = { { 7, -3 } };
		MinCostToConnectAllPointsKruskal mcc3 = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc3.minCost(points3)); // 0, one point needs no edges
	}

}

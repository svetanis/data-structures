package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;

// 1584. Min Cost to Connect All Points
//
// Input: points, a list of [x, y] positions. On LeetCode: 1 <= points.length <= 1000,
// -10^6 <= x, y <= 10^6, and all points are different.
// Returns: the least total length of segments that joins every point to every other, directly or
// through other points. The length of a segment is the Manhattan distance |x1 - x2| + |y1 - y2|.
// A cheapest such set of segments is a minimum spanning tree (MST): n - 1 segments, no loops.
//
// Prim's Algorithm, array scan, with every pair cost stored up front in a matrix.
// Same algorithm as MinCostToConnectAllPointsPrimScan; the only difference is that
// this one materialises the n x n table before it starts, which is n^2 ints held for
// the whole run, with n^2 / 2 distances computed before the first round. Worth reading once, for g[next][k] making the
// relaxation step plain, then prefer the Scan version.
//
// The one idea: grow one tree from point 0. cheapestEdge[i] is the length of the CHEAPEST SINGLE
// SEGMENT that joins point i (outside the tree) to any point already in the tree. Each round,
// scan for the outside point with the smallest cheapestEdge, bring it in and pay for that one
// segment; then, for every point still outside, a segment from the new point may be shorter than
// what it had, so keep the smaller. The shortest segment leaving the tree is always safe to keep,
// so no choice is undone. g[a][b] holds the segment length between points a and b, so the
// relaxation reads it from the table instead of computing it. Do not add the route so far -- that
// is Dijkstra, which builds a shortest-path tree and gives a different total.
//
// Time: O(n^2) -- n rounds, each scanning all n points twice; the matrix takes n^2 / 2 distance
// computations. With n(n-1)/2 edges in the graph, that is constant work per edge.
// Space: O(n^2) -- the matrix; the Scan version needs only O(n).
//
// Siblings: MinCostToConnectAllPointsPrimScan (no matrix, O(n) space), PrimHeap (a heap, which
// only adds a log here), and the Kruskal pair, which sort an edge list.

public final class MinCostToConnectAllPointsPrimMatrix {

	// large enough to lose every comparison, small enough not to overflow when added to
	private static final int INF = 1 << 30;

	public int minCost(int[][] points) {
		int n = points.length;
		int[][] g = init(points); // MATRIX: g[a][b] -- segment length between points a and b
		boolean[] inMst = new boolean[n]; // TREE: points already joined
		// cheapestEdge[i] = cost of the CHEAPEST SINGLE EDGE joining point i to the tree.
		// Not a distance from point 0, and nothing accumulates -- a spanning tree
		// has no source, and its cost is the sum of the edges chosen.
		int[] cheapestEdge = new int[n];
		Arrays.fill(cheapestEdge, INF);
		// point 0 joins first, paying nothing: a tree of one point has no edges
		cheapestEdge[0] = 0; // START: point 0 joins first, for free
		int total = 0;
		for (int i = 0; i < n; i++) {
			// pick the point outside the tree that is cheapest to bring in
			int next = -1;
			for (int j = 0; j < n; j++) {
				boolean closer = next == -1 || cheapestEdge[j] < cheapestEdge[next];
				if (!inMst[j] && closer) {
					next = j; // PICK: the cheapest point outside the tree so far
				}
			}
			inMst[next] = true;
			// pay for the one edge that brings it in, not for a route
			total += cheapestEdge[next]; // TAKE: the segment that brought it in
			// the tree just grew, so every point outside it has a new way in -- through next
			for (int k = 0; k < n; k++) {
				if (!inMst[k]) {
					// Dijkstra would write cheapestEdge[next] + g[next][k] here. Prim does not.
					// Adding the route so far builds a shortest-path tree instead -- also a
					// spanning tree, also plausible, and a different total.
					cheapestEdge[k] = Math.min(cheapestEdge[k], g[next][k]); // RELAX: keep the shorter way in
				}
			}
		}
		return total;
	}

	// the full n x n table of pair costs, held up front.
	// both other 1584 files compute each distance at the moment it is needed.
	private int[][] init(int[][] points) {
		int n = points.length;
		int[][] g = new int[n][n];
		for (int i = 0; i < n; i++) {
			// j starts at i + 1: each unordered pair is measured once, then mirrored
			for (int j = i + 1; j < n; j++) {
				int dist = mdist(points[i], points[j]);
				g[i][j] = dist; // BUILD: both directions
				g[j][i] = dist;
			}
		}
		return g;
	}

	private int mdist(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]); // RETURN: Manhattan distance
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsPrimMatrix mcc = new MinCostToConnectAllPointsPrimMatrix();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsPrimMatrix mcc2 = new MinCostToConnectAllPointsPrimMatrix();
		System.out.println(mcc2.minCost(points2)); // 18

		int[][] points3 = { { 7, -3 } };
		MinCostToConnectAllPointsPrimMatrix mcc3 = new MinCostToConnectAllPointsPrimMatrix();
		System.out.println(mcc3.minCost(points3)); // 0, one point needs no edges
	}
}

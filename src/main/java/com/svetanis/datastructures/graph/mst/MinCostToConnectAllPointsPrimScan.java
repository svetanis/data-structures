package com.svetanis.datastructures.graph.mst;

// 1584. Min Cost to Connect All Points
// Prim's Algorithm, array scan, distances computed on demand.
// The one to write for this problem: fastest of the three, and the only one
// that never holds an edge anywhere.

public final class MinCostToConnectAllPointsPrimScan {
	// Time Complexity: O(n^2) -- n rounds, each scanning all n points twice
	// Space Complexity: O(n)  -- two arrays, no matrix and no heap
	// A complete graph has n(n-1)/2 edges, so O(n^2) is constant work per edge
	// and nothing can beat it here. The heap in MinCostToConnectAllPointsPrimHeap
	// only pays off when the inner loop walks a neighbour list -- that is MstPrim.

	public int minCost(int[][] points) {
		int n = points.length;
		// cheapestEdge[i] = cost of the CHEAPEST SINGLE EDGE joining point i to the tree.
		// Not a distance from point 0, and nothing accumulates -- a spanning tree has no
		// source, and its cost is the sum of the edges chosen.
		int[] cheapestEdge = new int[n];
		boolean[] inMst = new boolean[n];
		// the tree starts as point 0 alone, so every other point's cheapest way in
		// is simply its own edge to point 0
		int[] start = points[0];
		inMst[0] = true;
		for (int i = 0; i < n; i++) {
			cheapestEdge[i] = mdist(start, points[i]);
		}
		int total = 0;
		// n - 1 rounds: point 0 is already in, and a tree on n points has n - 1 edges
		for (int i = 1; i < n; i++) {
			int next = cheapest(cheapestEdge, inMst);
			// pay for the one edge that brings it in, not for a route
			total += cheapestEdge[next];
			inMst[next] = true;
			// the tree just grew, so every point outside it has a new way in -- through next
			int[] joined = points[next];
			for (int j = 0; j < n; j++) {
				// Dijkstra would compare cheapestEdge[next] + dist here. Prim does not.
				// Adding the route so far builds a shortest-path tree instead -- also a
				// spanning tree, also plausible, and a different total.
				int dist = mdist(joined, points[j]);
				if (!inMst[j] && cheapestEdge[j] > dist) {
					cheapestEdge[j] = dist;
				}
			}
		}
		return total;
	}

	// the point outside the group that is cheapest to bring in
	private int cheapest(int[] cheapestEdge, boolean[] inMst) {
		int index = -1;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < cheapestEdge.length; i++) {
			if (!inMst[i] && cheapestEdge[i] < min) {
				min = cheapestEdge[i];
				index = i;
			}
		}
		return index;
	}

	private int mdist(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsPrimScan mcc = new MinCostToConnectAllPointsPrimScan();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsPrimScan mcc2 = new MinCostToConnectAllPointsPrimScan();
		System.out.println(mcc2.minCost(points2)); // 18

		int[][] points3 = { { 7, -3 } };
		MinCostToConnectAllPointsPrimScan mcc3 = new MinCostToConnectAllPointsPrimScan();
		System.out.println(mcc3.minCost(points3)); // 0, one point needs no edges
	}
}

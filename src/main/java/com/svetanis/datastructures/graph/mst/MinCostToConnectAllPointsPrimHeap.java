package com.svetanis.datastructures.graph.mst;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1584. Min Cost to Connect All Points
// Prim's Algorithm with a heap -- the slowest of the three, and the one to know anyway,
// because it is Dijkstra with one term deleted and every sparse graph wants this shape.
// On THIS problem the heap is dead weight: the inner loop still visits all n points,
// so the heap only adds a log to work that was already being done.

public final class MinCostToConnectAllPointsPrimHeap {
	// Time Complexity: O(n^2 log n) -- n^2 relaxations, each pushing onto a heap
	// Space Complexity: O(n^2) -- the heap is never cleaned of stale entries
	// For this problem prefer MinCostToConnectAllPointsPrimScan.
	// For a graph given as an edge list, see MstPrim.

	private static final int INF = 1 << 30;

	public int minCost(int[][] points) {
		int n = points.length;
		boolean[] inMst = new boolean[n];
		// cheapestEdge[i] = cost of the CHEAPEST SINGLE EDGE joining point i to the tree
		int[] cheapestEdge = new int[n];
		Arrays.fill(cheapestEdge, INF);
		cheapestEdge[0] = 0;
		int total = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[1]));
		pq.offer(new int[] { 0, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int next = node[0];
			if (inMst[next]) {
				// a cheaper way into this point was taken earlier; this entry is stale
				continue;
			}
			inMst[next] = true;
			// pay for the one edge that brings it in, not for a route
			total += node[1];
			// the tree just grew, so every point outside it has a new way in -- through next
			for (int v = 0; v < n; v++) {
				if (!inMst[v]) {
					int dist = mdist(points[next], points[v]);
					// Dijkstra would compare node[1] + dist here. Prim does not.
					// Adding the route so far builds a shortest-path tree instead -- also a
					// spanning tree, also plausible, and a different total.
					if (dist < cheapestEdge[v]) {
						cheapestEdge[v] = dist;
						pq.offer(new int[] { v, dist });
					}
				}
			}
		}
		return total;
	}

	private int mdist(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsPrimHeap mcc = new MinCostToConnectAllPointsPrimHeap();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsPrimHeap mcc2 = new MinCostToConnectAllPointsPrimHeap();
		System.out.println(mcc2.minCost(points2)); // 18
	}
}

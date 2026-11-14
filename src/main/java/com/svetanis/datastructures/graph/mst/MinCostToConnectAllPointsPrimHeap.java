package com.svetanis.datastructures.graph.mst;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1584. Min Cost to Connect All Points
//
// Input: points, a list of [x, y] positions. On LeetCode: 1 <= points.length <= 1000,
// -10^6 <= x, y <= 10^6, and all points are different.
// Returns: the least total length of segments that joins every point to every other, directly or
// through other points. The length of a segment is the Manhattan distance |x1 - x2| + |y1 - y2|.
// A cheapest such set of segments is a minimum spanning tree (MST): n - 1 segments, no loops.
//
// Prim's Algorithm with a heap -- the slowest of the three, and the one to know anyway,
// because it is Dijkstra with one term deleted and every sparse graph wants this shape.
// On THIS problem the heap is dead weight: the inner loop still visits all n points,
// so the heap only adds a log to work that was already being done.
//
// The one idea: grow one tree from point 0. cheapestEdge[i] is the length of the CHEAPEST SINGLE
// SEGMENT that joins point i (outside the tree) to any point already in the tree. One heap entry
// is {a point, the segment length that was its best when the entry was pushed}. Take the entry
// with the shortest segment, bring that point in, pay for that one segment, and for every point
// still outside check whether the new point gives it a shorter segment; if so, push a fresh
// entry. The shortest segment leaving the tree is always safe to keep, so no choice is undone.
// An entry whose point is already in the tree is stale (a cheaper way in was taken first) and is
// skipped. Do not add the route so far to the segment length -- that is Dijkstra, which builds a
// shortest-path tree and gives a different total.
//
// Time: O(n^2 log n) -- n^2 relaxations, each of which may push onto a heap of up to n^2 entries.
// Space: O(n^2) -- the heap is never cleaned of stale entries.
//
// Siblings: MinCostToConnectAllPointsPrimScan does the same with two arrays and no heap, in
// O(n^2) time and O(n) space -- prefer it for this problem. PrimMatrix keeps all pair lengths in
// a matrix first. For a graph given as an edge list, see MstPrim.

public final class MinCostToConnectAllPointsPrimHeap {

	private static final int INF = 1 << 30;

	public int minCost(int[][] points) {
		int n = points.length;
		boolean[] inMst = new boolean[n]; // TREE: points already joined
		// cheapestEdge[i] = cost of the CHEAPEST SINGLE EDGE joining point i to the tree
		int[] cheapestEdge = new int[n];
		Arrays.fill(cheapestEdge, INF);
		cheapestEdge[0] = 0; // START: point 0 joins first, for free
		int total = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[1])); // HEAP: {point, segment length}
		pq.offer(new int[] { 0, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int next = node[0];
			if (inMst[next]) {
				// a cheaper way into this point was taken earlier; this entry is stale
				continue; // SKIP: stale entry
			}
			inMst[next] = true;
			// pay for the one edge that brings it in, not for a route
			total += node[1]; // TAKE: the segment that brought it in
			// the tree just grew, so every point outside it has a new way in -- through next
			for (int v = 0; v < n; v++) {
				if (!inMst[v]) {
					int dist = mdist(points[next], points[v]);
					// Dijkstra would compare node[1] + dist here. Prim does not.
					// Adding the route so far builds a shortest-path tree instead -- also a
					// spanning tree, also plausible, and a different total.
					if (dist < cheapestEdge[v]) {
						cheapestEdge[v] = dist; // RELAX: a shorter way in
						pq.offer(new int[] { v, dist });
					}
				}
			}
		}
		return total;
	}

	private int mdist(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]); // RETURN: Manhattan distance
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsPrimHeap mcc = new MinCostToConnectAllPointsPrimHeap();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsPrimHeap mcc2 = new MinCostToConnectAllPointsPrimHeap();
		System.out.println(mcc2.minCost(points2)); // 18

		int[][] points3 = { { 7, -3 } };
		MinCostToConnectAllPointsPrimHeap mcc3 = new MinCostToConnectAllPointsPrimHeap();
		System.out.println(mcc3.minCost(points3)); // 0, one point needs no edges
	}
}

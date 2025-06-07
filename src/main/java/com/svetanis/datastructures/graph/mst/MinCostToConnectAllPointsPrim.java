package com.svetanis.datastructures.graph.mst;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1584. Min Cost to Connect All Points
// Prim's Algorithm using Priority Queue

public final class MinCostToConnectAllPointsPrim {
	// Time Complexity: O(n^2 log n)

	private static final int INF = 1 << 30;

	public int minCost(int[][] points) {
		int n = points.length;
		boolean[] inMst = new boolean[n];
		int[] minDist = new int[n];
		Arrays.fill(minDist, INF);
		minDist[0] = 0;
		int cost = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.offer(new int[] { 0, 0 });
		while (!pq.isEmpty()) {
			int[] node = pq.poll();
			int u = node[0];
			if (inMst[u]) {
				continue;
			}
			inMst[u] = true;
			cost += node[1];
			for (int v = 0; v < n; v++) {
				if (!inMst[v]) {
					int dist = mdist(points, u, v);
					if (dist < minDist[v]) {
						minDist[v] = dist;
						pq.offer(new int[] { v, dist });
					}
				}
			}
		}
		return cost;
	}

	private int mdist(int[][] points, int u, int v) {
		return Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsPrim mcc = new MinCostToConnectAllPointsPrim();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsPrim mcc2 = new MinCostToConnectAllPointsPrim();
		System.out.println(mcc2.minCost(points2)); // 18
	}
}

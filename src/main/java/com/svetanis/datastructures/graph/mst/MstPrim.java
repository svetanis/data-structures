package com.svetanis.datastructures.graph.mst;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Minimum Spanning Tree: Prim's Algorithm over an adjacency list
// The sparse counterpart of MinCostToConnectAllPointsPrimScan:
// there the inner loop walks all n nodes because every pair is an edge;
// here it walks one node's neighbour list, so the heap earns its log

public final class MstPrim {
	// Time Complexity: O(m log m)
	// Space Complexity: O(n + m)

	// nodes are labelled 1..n

	public int mst(int n, int[][] grid) {
		List<List<Edge>> adj = adjacency(n, grid);
		boolean[] inMst = new boolean[n + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>(comparingInt(edge -> edge.weight));
		pq.offer(new Edge(1, 0));
		int total = 0;
		int count = 0;
		while (!pq.isEmpty()) {
			Edge top = pq.poll();
			if (inMst[top.node]) {
				// a cheaper way into this node was taken earlier
				continue;
			}
			inMst[top.node] = true;
			total += top.weight;
			count++;
			for (Edge next : adj.get(top.node)) {
				if (!inMst[next.node]) {
					pq.offer(next);
				}
			}
		}
		// a forest is not a spanning tree
		return count == n ? total : -1;
	}

	private List<List<Edge>> adjacency(int n, int[][] grid) {
		List<List<Edge>> adj = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			adj.add(new ArrayList<>());
		}
		for (int[] row : grid) {
			adj.get(row[0]).add(new Edge(row[1], row[2]));
			adj.get(row[1]).add(new Edge(row[0], row[2]));
		}
		return adj;
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 2, 1 }, { 2, 5, 1 }, { 4, 5, 2 }, { 1, 5, 3 }, { 3, 2, 3 }, { 3, 4, 5 }, { 4, 1, 6 } };
		MstPrim prim = new MstPrim();
		System.out.println(prim.mst(5, grid)); // 7, the same graph as MstKruskal

		int[][] split = { { 1, 2, 1 }, { 3, 4, 1 } };
		MstPrim forest = new MstPrim();
		System.out.println(forest.mst(4, split)); // -1, two components
	}

	private static class Edge {

		private final int node;
		private final int weight;

		public Edge(int node, int weight) {
			this.node = node;
			this.weight = weight;
		}
	}
}

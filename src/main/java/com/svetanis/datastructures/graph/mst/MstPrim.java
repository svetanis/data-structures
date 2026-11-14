package com.svetanis.datastructures.graph.mst;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Minimum Spanning Tree: Prim's Algorithm over an adjacency list
//
// Input: n, the number of nodes, labelled 1..n, and grid, a list of weighted two-way edges
// [a, b, weight] between them. No LeetCode problem fixes the limits; the weights are assumed to
// add up inside an int. Self-loops and parallel edges are allowed.
// Returns: the total weight of a minimum spanning tree, or -1 when the graph is in pieces and no
// tree reaches every node.
//
// The sparse counterpart of MinCostToConnectAllPointsPrimScan:
// there the inner loop walks all n nodes because every pair is an edge;
// here it walks one node's neighbour list, so the heap earns its log
//
// The one idea: grow one tree from node 1. One heap entry is {a node, the weight of one edge that
// reaches it from the tree}. Take the entry with the lightest edge; if its node is already in the
// tree the entry is stale (a lighter way in was taken first) and is skipped, otherwise bring the
// node in, pay for that one edge, and push the edges from the new node to nodes still outside.
// The lightest edge leaving the tree is always safe to keep, so no choice is ever undone. The
// heap holds edge weights, not routes -- adding the route so far would be Dijkstra, which builds
// a shortest-path tree. If the tree has fewer than n nodes when the heap runs dry, the graph was
// in pieces.
//
// Time: O(m log m) -- each of the 2m directed edges is pushed at most once and a heap step costs
// O(log m).
// Space: O(n + m) -- the adjacency lists and the heap.
//
// Siblings: MinCostToConnectAllPointsPrimScan, PrimMatrix and PrimHeap are Prim on a complete
// graph of points; MstKruskal and MstForest build the same tree by sorting the edges.

public final class MstPrim {

	// nodes are labelled 1..n

	public int mst(int n, int[][] grid) {
		List<List<Edge>> adj = adjacency(n, grid); // ADJ: adj.get(v) -- the edges leaving node v
		boolean[] inMst = new boolean[n + 1]; // TREE: nodes already joined
		PriorityQueue<Edge> pq = new PriorityQueue<>(comparingInt(edge -> edge.weight)); // HEAP: {node, edge weight}
		pq.offer(new Edge(1, 0)); // START: node 1 joins first, for free
		int total = 0;
		int count = 0;
		while (!pq.isEmpty()) {
			Edge top = pq.poll();
			if (inMst[top.node]) {
				// a cheaper way into this node was taken earlier
				continue; // SKIP: stale entry
			}
			inMst[top.node] = true;
			total += top.weight; // TAKE: the edge that brought it in
			count++;
			for (Edge next : adj.get(top.node)) {
				if (!inMst[next.node]) {
					pq.offer(next); // PUSH: a way in for a node still outside
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
			adj.get(row[0]).add(new Edge(row[1], row[2])); // BUILD: the edge, in both directions
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

package com.svetanis.datastructures.graph.mst;

import static java.util.Comparator.comparingInt;

import com.svetanis.datastructures.graph.unionfind.UnionFind;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Minimum Spanning Tree: Kruskal's Algorithm
//
// Input: n, the number of nodes, and grid, a list of weighted two-way edges [a, b, weight]
// between nodes labelled with any integers (not only 0..n-1). No LeetCode problem fixes the
// limits; the weights are assumed to add up inside an int. Self-loops (a == b) and parallel edges
// are allowed. The graph is assumed to be connected.
// Returns: the total weight of a minimum spanning tree: the lightest set of n - 1 edges that
// joins every node. On a graph in pieces it does not check and does not fail: it returns the
// total of a minimum spanning forest, the same as MstForest.
//
// The one idea: go through the edges from the lightest up and keep an edge only if its two ends
// are not already connected through the edges kept so far; an edge between nodes that are already
// connected would close a loop and is never needed. The shortest edge that does not close a loop
// is always safe to keep, so no choice is ever undone. A tree on n nodes has exactly n - 1 edges,
// so the walk stops the moment n - 1 have been kept. UnionFind keeps the groups; isSame asks
// whether two nodes are in one group and union joins them.
//
// Time: O(m log m) -- sorting the m edges dominates; the union-find steps are near-constant.
// Space: O(n + m) -- the edge list and the union-find map.
//
// Siblings: MstForest is the same walk without the stopping rule; MstPrim grows one tree from
// node 1 and answers -1 when the graph is in pieces; CriticalEdgesInMST runs Kruskal once per
// edge.

public final class MstKruskal {

	private UnionFind<Integer> dsu = new UnionFind<>(); // GROUPS: which nodes are already connected

	public int mst(int n, int[][] grid) {
		List<Edge> edges = edges(grid); // EDGES: sorted lightest first
		int total = 0;
		int count = 0;
		for (Edge edge : edges) {
			if (!dsu.isSame(edge.a, edge.b)) { // KEEP: only an edge that joins two separate groups
				dsu.union(edge.a, edge.b);
				total += edge.weight; // TAKE: this edge
				count++;
				if (count == n - 1) {
					break; // STOP: a tree on n nodes has n - 1 edges
				}
			}
		}
		return total;
	}

	private List<Edge> edges(int[][] grid) {
		List<Edge> list = new ArrayList<>();
		for (int[] row : grid) {
			list.add(new Edge(row[0], row[1], row[2]));
		}
		Collections.sort(list, comparingInt(a -> a.weight)); // SORT: lightest first
		return list;
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 2, 1 }, { 2, 5, 1 }, { 4, 5, 2 }, { 1, 5, 3 }, { 3, 2, 3 }, { 3, 4, 5 }, { 4, 1, 6 } };
		MstKruskal kruskal = new MstKruskal();
		System.out.println(kruskal.mst(5, grid)); // 7
	}

	private static class Edge {

		private int a, b;
		private int weight;

		public Edge(int a, int b, int weight) {
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}
}

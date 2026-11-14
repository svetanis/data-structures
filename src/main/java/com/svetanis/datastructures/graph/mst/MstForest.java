package com.svetanis.datastructures.graph.mst;

import com.svetanis.datastructures.graph.unionfind.UnionFind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Minimum Spanning Tree | Forests
//
// Input: n and grid, a list of weighted two-way edges [a, b, weight] between nodes labelled with
// any integers (not only 0..n-1). No LeetCode problem fixes the limits; the weights are assumed to
// add up inside an int. Self-loops (a == b) and parallel edges are allowed.
// Returns: the total weight of a minimum spanning FOREST: the lightest set of edges that joins
// every node to all the nodes it can reach. When the graph is connected that is the weight of a
// minimum spanning tree; when it is not, it is the sum of one tree per piece. This method never
// stops early and never checks that the graph is connected, on purpose. n is not used.
//
// The one idea: Kruskal's method -- go through the edges from the lightest up and keep an edge
// only if its two ends are not already connected through the edges kept so far; an edge between
// nodes that are already connected would close a loop and is never needed. The shortest edge that
// does not close a loop is always safe to keep, so no choice is ever undone. Running through ALL
// the edges, with no stopping rule, is what makes the answer a forest on a graph in pieces.
// UnionFind keeps the groups; isSame asks whether two nodes are in one group and union joins them.
//
// Time: O(m log m) -- sorting the m edges dominates; the union-find steps are near-constant.
// Space: O(n + m) -- the edge list and the union-find map.
//
// Siblings: MstKruskal is the same walk that stops after n - 1 edges; MstPrim grows one tree from
// node 1 and answers -1 when the graph is in pieces; CriticalEdgesInMST runs Kruskal once per
// edge.

public final class MstForest {

	private UnionFind<Integer> dsu = new UnionFind<>(); // GROUPS: which nodes are already connected

	public int mst(int n, int[][] grid) {
		int total = 0;
		List<List<Integer>> edges = edges(grid); // EDGES: sorted lightest first
		for (List<Integer> edge : edges) {
			int a = edge.get(0), b = edge.get(1);
			int weight = edge.get(2);
			if (!dsu.isSame(a, b)) { // KEEP: only an edge that joins two separate groups
				dsu.union(a, b);
				total += weight; // TAKE: this edge
			}
		}
		return total;
	}

	private List<List<Integer>> edges(int[][] grid) {
		List<List<Integer>> list = new ArrayList<>();
		for (int[] row : grid) {
			list.add(Arrays.asList(row[0], row[1], row[2]));
		}
		Collections.sort(list, (a, b) -> a.get(2).compareTo(b.get(2))); // SORT: lightest first
		return list;
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 2, 1 }, { 2, 4, 2 }, { 3, 5, 3 }, { 4, 4, 4 } };
		MstForest kruskal = new MstForest();
		System.out.println(kruskal.mst(5, grid)); // 6, two pieces {1,2,4} and {3,5}; the self-loop {4,4,4} is skipped
	}
}

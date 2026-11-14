package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 310. Minimum Height Trees
//
// Input: v -- the number of nodes, numbered 0 .. v - 1; edges -- the pairs {a, b} of an undirected
// graph that is a tree (connected, no cycle, so v - 1 edges). Any node can be chosen as the root,
// which hangs the tree from it; the height is the number of edges on the longest path down from the
// root. Returns: every root that gives the smallest possible height (at most two of them), in any
// order. On LeetCode: 1 <= v <= 2 * 10^4, edges.length == v - 1, no repeated edge.
//
// The one idea: the best roots are in the middle of the tree. Peel the tree like an onion: all the
// leaves (nodes with exactly one neighbour) are the farthest from the middle, so remove them all
// at once; the tree that is left has new leaves, and so on. When one or two nodes are left they are
// the middle (two when the longest path in the tree has an odd number of edges, one when it has an
// even number). "Leaf" is detected by the number of neighbours a node still has (its degree):
// removing a leaf lowers its neighbour's degree, and a neighbour that falls to 1 is a leaf in the
// next round.
//
// Time: O(V + E) -- every node is removed once and every edge is used up once.
// Space: O(V + E) -- the neighbour lists, the degrees and the queue.
//
// Siblings: MinimumHeightTreesSubmit is the same peeling with arrays instead of maps and ends the
// loop by the queue running dry; this one stops once two or fewer nodes are left.

public final class MinimumHeightTrees {

	public static List<Integer> mht(int v, int[][] edges) {
		List<Integer> list = new ArrayList<>();
		if (v == 1) { // BASE: a single node is its own root
			list.add(0);
			return list;
		}
		Map<Integer, Integer> inDegree = inDegreeInit(v);
		Map<Integer, List<Integer>> graph = graphInit(v);
		buildGraph(v, edges, inDegree, graph);
		Queue<Integer> queue = leaves(inDegree);
		// remove leaves level by level
		// until left with 1 or 2 nodes
		int total = v;
		while (total > 2) {
			int leaves = queue.size();
			total -= leaves; // PEEL: remove the whole current layer of leaves
			for (int i = 0; i < leaves; i++) {
				int leaf = queue.poll();
				List<Integer> nodes = graph.get(leaf);
				for (int node : nodes) {
					int f = inDegree.getOrDefault(node, 0) - 1; // the leaf is gone from its neighbour's count
					if (f == 1) { // the neighbour is a leaf in the next round
						queue.add(node);
					}
					inDegree.put(node, f);
				}
			}
		}
		list.addAll(queue); // RETURN: the one or two nodes left in the middle
		return list;
	}

	private static Queue<Integer> leaves(Map<Integer, Integer> map) {
		Queue<Integer> queue = new LinkedList<>();
		// all leaves (all nodes with only 1 neighbour)
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) { // LEAF
				queue.add(entry.getKey());
			}
		}
		return queue;
	}

	private static void buildGraph(int v, int[][] edges, Map<Integer, Integer> inDegree,
			Map<Integer, List<Integer>> map) {
		// this is undirected graph
		for (int i = 0; i < edges.length; i++) {
			int node1 = edges[i][0];
			int node2 = edges[i][1];
			// add a link for both nodes
			map.get(node1).add(node2);
			map.get(node2).add(node1);
			int nf1 = inDegree.getOrDefault(node1, 0);
			int nf2 = inDegree.getOrDefault(node2, 0);
			// increment the degrees for both nodes
			inDegree.put(node1, nf1 + 1);
			inDegree.put(node2, nf2 + 1);
		}
	}

	private static Map<Integer, Integer> inDegreeInit(int v) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < v; i++) {
			map.put(i, 0);
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int v) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < v; i++) {
			map.put(i, new ArrayList<>());
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] m0 = { { 0, 1 }, { 1, 2 }, { 1, 3 }, { 2, 4 } };
		int[][] m1 = { { 0, 1 }, { 0, 2 }, { 2, 3 } };
		int[][] m2 = { { 0, 1 }, { 1, 2 }, { 1, 3 } };
		System.out.println(mht(5, m0)); // [1, 2]
		System.out.println(mht(4, m1)); // [0, 2]
		System.out.println(mht(4, m2)); // [1]
	}
}

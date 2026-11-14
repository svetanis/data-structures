package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
// at once; the tree that is left has new leaves, and so on. The last layer removed before the
// queue runs dry is the middle: one or two nodes. A node's degree (how many neighbours it still
// has) tells when it is a leaf: removing a leaf lowers its neighbour's degree, and a neighbour that
// falls to 1 is a leaf in the next round.
//
// Time: O(V + E) -- every node is removed once and every edge is used up once.
// Space: O(V + E) -- the neighbour lists, the degrees and the queue.
//
// Siblings: MinimumHeightTrees is the same peeling with maps instead of arrays and stops once two
// or fewer nodes are left.

public final class MinimumHeightTreesSubmit {

	public static List<Integer> mht(int v, int[][] edges) {
		List<Integer> list = new ArrayList<>();
		if (v == 1) { // BASE: a single node is its own root
			list.add(0);
			return list;
		}
		int[] inDegree = new int[v];
		List<Integer>[] graph = graphInit(v);
		buildGraph(v, edges, inDegree, graph);
		Queue<Integer> queue = leaves(v, inDegree);
		// remove leaves level by level
		// until the queue runs dry
		while (!queue.isEmpty()) {
			list.clear(); // the layer before this one is not the middle
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int leaf = queue.poll();
				list.add(leaf); // RECORD: the latest layer seen is the answer so far
				for (int neighbor : graph[leaf]) {
					inDegree[neighbor]--; // the leaf is gone from its neighbour's count
					if (inDegree[neighbor] == 1) { // the neighbour is a leaf in the next round
						queue.add(neighbor);
					}
				}
			}
		}
		return list; // RETURN: the last layer removed, the middle
	}

	private static Queue<Integer> leaves(int v, int[] inDegree) {
		Queue<Integer> queue = new LinkedList<>();
		// all leaves (all nodes with only 1 neighbour)
		for (int i = 0; i < v; i++) {
			if (inDegree[i] == 1) { // LEAF
				queue.add(i);
			}
		}
		return queue;
	}

	private static void buildGraph(int v, int[][] edges, int[] inDegree, List<Integer>[] list) {
		// this is undirected graph
		for (int[] edge : edges) {
			int node1 = edge[0];
			int node2 = edge[1];
			// add a link for both nodes
			list[node1].add(node2);
			list[node2].add(node1);
			// increment the degrees for both nodes
			inDegree[node1]++;
			inDegree[node2]++;
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Integer>[] graphInit(int v) {
		List<Integer>[] list = new List[v];
		for (int i = 0; i < v; i++) {
			list[i] = new ArrayList<>();
		}
		return list;
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

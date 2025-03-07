package com.svetanis.datastructures.graph.directed.ts.kahn;

import static com.google.common.collect.Maps.filterValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 310. Minimum Height Trees

// given an undirected graph with 
// characteristics of a k-ary tree
// in such a graph, we can choose
// any node as the root to make a
// k-ary tree. the root (or the tree)
// with the minimum height is called
// Minimum Height Tree (MHT). there 
// can be multiple MHTs for a graph.

// find all MHTs of the given graph
// return a list of their roots

public final class MinimumHeightTrees {
	// Time Complexity: O(V + E)

	public static List<Integer> mht(int v, int[][] edges) {
		List<Integer> list = new ArrayList<>();
		if (v == 1) {
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
			total -= leaves;
			for (int i = 0; i < leaves; i++) {
				int leaf = queue.poll();
				List<Integer> nodes = graph.get(leaf);
				for (int node : nodes) {
					int f = inDegree.getOrDefault(node, 0) - 1;
					if (f == 1) {
						queue.add(node);
					}
					inDegree.put(node, f);
				}
			}
		}
		list.addAll(queue);
		return list;
	}

	private static Queue<Integer> leaves(Map<Integer, Integer> map) {
		Queue<Integer> queue = new LinkedList<>();
		// all leaves (all nodes with only 1 in-degree)
		Map<Integer, Integer> filtered = filterValues(map, v -> v == 1);
		queue.addAll(filtered.keySet());
		return queue;
	}

	private static Queue<Integer> leaves2(Map<Integer, Integer> map) {
		Queue<Integer> queue = new LinkedList<>();
		// all leaves (all nodes with only 1 in-degree)
		for (int key : map.keySet()) {
			if (map.get(key) == 1) {
				queue.add(key);
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
			// increment the in-degrees for both nodes
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
		System.out.println(mht(5, m0)); // 1, 2
		System.out.println(mht(4, m1)); // 0, 2
		System.out.println(mht(4, m2)); // 1
	}
}

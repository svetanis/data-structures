package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Does a directed graph have a cycle? (Kahn's method on an edge array)
//
// Input: v -- the number of vertices, numbered 0 .. v - 1; m -- the edges as pairs {from, to}
// (repeated edges and self-loops allowed). Returns: true if some chain of edges leads from a vertex
// back to itself.
//
// The one idea: the in-degree of a vertex is how many edges point at it. A vertex nobody points at
// can go first in a line in which every edge points forward; place it and take its outgoing edges
// away, which frees the vertices it pointed at. If the line takes in all v vertices there is no
// cycle. If it stops early, every vertex left has an edge from another vertex that is left, and
// following those edges backwards must come round to a vertex already met: a cycle.
//
// Time: O(V + E) -- every vertex is queued once and every edge is used up once.
// Space: O(V + E) -- the two maps and the queue.
//
// Siblings: CyclicGraphBfs is the same test on the Graph class with an in-degree array;
// TopologicalSortingBfs is the same line-building but returns the order. CyclicGraphDfs finds the
// cycle by walking.

public final class IsCyclicDGraphBfs {

	public static boolean hasCycle(int v, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(v);
		Map<Integer, List<Integer>> graph = graphInit(v);
		buildGraph(v, m, inDegree, graph);
		List<Integer> list = new ArrayList<>();
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src); // PLACE src in the line
			List<Integer> children = graph.get(src);
			for (int child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1; // USE UP the edge src -> child
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		return list.size() != v; // RETURN: the line fell short, so the rest is on, or behind, a cycle
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = new LinkedList<>();
		// all vertices with 0 in-degree
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 0) { // SOURCE: nothing points at it
				queue.add(entry.getKey());
			}
		}
		return queue;
	}

	private static void buildGraph(int v, int[][] m, Map<Integer, Integer> inDegree,
			Map<Integer, List<Integer>> map) {
		for (int i = 0; i < m.length; i++) {
			int parent = m[i][0];
			int child = m[i][1];
			map.get(parent).add(child); // EDGE: parent -> child
			int fc = inDegree.getOrDefault(child, 0);
			inDegree.put(child, fc + 1); // COUNT the edge at its head
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
		int[][] m1 = { { 3, 2 }, { 3, 0 }, { 2, 0 }, { 2, 1 } };
		int[][] m2 = { { 4, 2 }, { 4, 3 }, { 2, 0 }, { 2, 1 }, { 3, 1 } };
		int[][] m3 = { { 6, 4 }, { 6, 2 }, { 5, 3 }, { 5, 4 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 4, 1 } };
		// the three graphs above are the ones TopologicalSortingBfs sorts,
		// and all three are acyclic
		System.out.println(hasCycle(4, m1)); // false
		System.out.println(hasCycle(5, m2)); // false
		System.out.println(hasCycle(7, m3)); // false

		// and one that does have a cycle: 0 -> 1 -> 2 -> 0
		int[][] m4 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
		System.out.println(hasCycle(3, m4)); // true
	}
}

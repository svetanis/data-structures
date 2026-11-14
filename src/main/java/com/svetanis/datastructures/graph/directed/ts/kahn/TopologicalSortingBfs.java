package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Topological order of a directed graph, by repeatedly removing vertices nobody points at (Kahn's
// algorithm, on an edge array).
//
// Input: v -- the number of vertices, numbered 0 .. v - 1; m -- the edges as pairs {from, to}.
// Returns: all the vertices in a line so that every edge {from, to} has from before to; an EMPTY
// list if the graph has a cycle, since no such line exists then. (Several lines may be valid;
// this returns the one the queue produces.)
//
// The one idea: the in-degree of a vertex is how many edges point at it. A vertex with in-degree 0
// has nothing that must come before it, so it can go next. Place it, and take its outgoing edges
// away: each vertex it pointed at has one fewer edge pointing at it, and any that reach 0 are ready
// to place. If the line takes in all v vertices it is a valid order; if it stops early, the
// vertices left all have an edge from another vertex that is left, which is a cycle.
//
// Time: O(V + E) -- every vertex is queued once and every edge is used up once.
// Space: O(V + E) -- the two maps, the queue and the result.
//
// Siblings: TopologicalSortingKahn.tc is this method on the Graph class, and throws on a cycle
// instead of returning an empty list; IsCyclicDGraphBfs answers only whether a cycle exists.

public final class TopologicalSortingBfs {

	public static List<Integer> sort(int v, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(v);
		Map<Integer, List<Integer>> graph = graphInit(v);
		buildGraph(v, m, inDegree, graph);
		List<Integer> list = new ArrayList<>();
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src); // PLACE src next in the line
			List<Integer> children = graph.get(src);
			for (int child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1; // USE UP the edge src -> child
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		return list.size() != v ? new ArrayList<>() : list; // RETURN: a short line means a cycle
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
		System.out.println(sort(4, m1)); // [3, 2, 0, 1]
		System.out.println(sort(5, m2)); // [4, 2, 3, 0, 1]
		System.out.println(sort(7, m3)); // [5, 6, 3, 4, 0, 2, 1]

		int[][] m4 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
		System.out.println(sort(3, m4)); // [] -- a cycle has no order
	}
}

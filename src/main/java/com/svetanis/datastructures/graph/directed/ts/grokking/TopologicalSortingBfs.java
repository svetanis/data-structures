package com.svetanis.datastructures.graph.directed.ts.grokking;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

// given a directed graph, 
// find the topological ordering 
// of its vertices

public final class TopologicalSortingBfs {
	// Time Complexity: O(V + E)

	public static ImmutableList<Integer> sort(int v, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(v);
		Map<Integer, List<Integer>> graph = graphInit(v);
		buildGraph(v, m, inDegree, graph);
		List<Integer> list = newArrayList();
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src);
			List<Integer> children = graph.get(src);
			for (int child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1;
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		return list.size() != v ? newList() : newList(list);
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = newLinkedList();
		// all vertices with 0 in-degree
		Map<Integer, Integer> filtered = filterValues(map, v -> v == 0);
		queue.addAll(filtered.keySet());
		return queue;
	}

	private static void buildGraph(int v, int[][] m, Map<Integer, Integer> inDegree, 
			Map<Integer, List<Integer>> map) {
		for (int i = 0; i < m.length; i++) {
			int parent = m[i][0];
			int child = m[i][1];
			map.get(parent).add(child);
			int fc = inDegree.getOrDefault(child, 0);
			inDegree.put(child, fc + 1);
		}
	}

	private static Map<Integer, Integer> inDegreeInit(int v) {
		Map<Integer, Integer> map = newHashMap();
		for (int i = 0; i < v; i++) {
			map.put(i, 0);
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int v) {
		Map<Integer, List<Integer>> map = newHashMap();
		for (int i = 0; i < v; i++) {
			map.put(i, newArrayList());
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] m1 = { { 3, 2 }, { 3, 0 }, { 2, 0 }, { 2, 1 } };
		int[][] m2 = { { 4, 2 }, { 4, 3 }, { 2, 0 }, { 2, 1 }, { 3, 1 } };
		int[][] m3 = { { 6, 4 }, { 6, 2 }, { 5, 3 }, { 5, 4 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 4, 1 } };
		System.out.println(sort(4, m1)); // 3, 2, 0, 1
		System.out.println(sort(5, m2)); // 4, 2, 3, 0, 1
		System.out.println(sort(7, m3)); // 5, 6, 3, 4, 0, 2, 1
	}
}

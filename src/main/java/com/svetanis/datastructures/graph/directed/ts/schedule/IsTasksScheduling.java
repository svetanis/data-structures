package com.svetanis.datastructures.graph.directed.ts.schedule;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Queue;

// there are n tasks, labeled from 0 to n - 1
// each task can have some prerequisite tasks
// which need to be completed before it can
// be scheduled. given the number of tasks and
// a list of prerequisite pairs, find out if
// it is possible to schedule all the tasks

public final class IsTasksScheduling {
	// Time Complexity: O(V + E)

	public static boolean isSchedulingPossible(int tasks, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(tasks);
		Map<Integer, List<Integer>> graph = graphInit(tasks);
		buildGraph(tasks, m, inDegree, graph);
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
		// if topological ordering doesn't contain all tasks
		// then there is a cyclic dependency between tasks
		// therefore it will not be possible to schedule all tasks
		return list.size() == tasks;
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
		int[][] m0 = { { 0, 1 }, { 1, 2 } };
		int[][] m1 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
		int[][] m2 = { { 2, 5 }, { 0, 5 }, { 0, 4 }, { 1, 4 }, { 3, 2 }, { 1, 3 } };
		System.out.println(isSchedulingPossible(3, m0)); // true
		System.out.println(isSchedulingPossible(3, m1)); // false
		System.out.println(isSchedulingPossible(6, m2)); // true
	}
}

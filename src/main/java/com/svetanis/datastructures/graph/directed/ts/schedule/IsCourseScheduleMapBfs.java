package com.svetanis.datastructures.graph.directed.ts.schedule;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// there are a total of n courses a student
// has to take, numbered from 0 to n - 1
// a course may have prerequisites. The
// "depends on" relationship is expressed as
// a pair of numbers. For example, [0, 1] 
// means you need to take course 1 before 
// taking course 0. Given n and the list of
// prerequisites, decide if it is possible
// to take all the courses.

public final class IsCourseScheduleMapBfs {
	// Time Complexity: O(V + E)

	public static boolean isValidCourseSchedule(int n, List<List<Integer>> prerequisites) {
		Map<Integer, List<Integer>> graph = graph(n, prerequisites);
		return topoSort(graph);
	}

	private static boolean topoSort(Map<Integer, List<Integer>> graph) {
		Map<Integer, Integer> inDegree = inDegree(graph);
		Queue<Integer> queue = sources(inDegree);
		List<Integer> list = newArrayList();
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src);
			for (int neighbor : graph.get(src)) {
				int ind = inDegree.get(neighbor) - 1;
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor);
				}
			}
		}
		return graph.size() == list.size();
	}

	private static Queue<Integer> sources(Map<Integer, Integer> inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		Map<Integer, Integer> filtered = filterValues(inDegree, v -> v == 0);
		queue.addAll(filtered.keySet());
		return queue;
	}

	private static Map<Integer, List<Integer>> graph(int n, List<List<Integer>> prerequisites) {
		Map<Integer, List<Integer>> map = graphInit(n);
		for (List<Integer> prerequisite : prerequisites) {
			map.get(prerequisite.get(0)).add(prerequisite.get(1));
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int n) {
		Map<Integer, List<Integer>> map = newHashMap();
		for (int i = 0; i < n; i++) {
			map.put(i, newArrayList());
		}
		return map;
	}

	private static Map<Integer, Integer> inDegree(Map<Integer, List<Integer>> graph) {
		Map<Integer, Integer> map = inDegreeInit(graph);
		for (int node : graph.keySet()) {
			for (int neighbor : graph.get(node)) {
				map.put(neighbor, map.get(neighbor) + 1);
			}
		}
		return map;
	}

	private static Map<Integer, Integer> inDegreeInit(Map<Integer, List<Integer>> graph) {
		Map<Integer, Integer> map = newHashMap();
		for (Integer node : graph.keySet()) {
			map.put(node, 0);
		}
		return map;
	}

	public static void main(String[] args) {
		List<List<Integer>> prerequisites = newArrayList();
		prerequisites.add(asList(0, 1));
		System.out.println(isValidCourseSchedule(2, prerequisites)); // true

		List<List<Integer>> prerequisites2 = newArrayList();
		prerequisites2.add(asList(0, 1));
		prerequisites2.add(asList(1, 0));
		System.out.println(isValidCourseSchedule(2, prerequisites2)); // false

		List<List<Integer>> prerequisites3 = newArrayList();
		prerequisites3.add(asList(0, 1));
		prerequisites3.add(asList(1, 2));
		prerequisites3.add(asList(2, 3));
		prerequisites3.add(asList(3, 1));
		System.out.println(isValidCourseSchedule(4, prerequisites3)); // false
	}
}

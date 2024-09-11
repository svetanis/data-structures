package com.svetanis.datastructures.graph.directed.ts.kahn;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

// there are n tasks, labeled from 0 to n - 1
// each task can have some prerequisite tasks
// which need to be completed before it can be
// scheduled. given the number of tasks and a 
// list of prerequisite pairs, write a method
// to print all possible ordering of tasks
// meeting all prerequisites

public final class AllTasksScheduleOrdering {
	// Time Complexity: O(V! * E)

	public static ImmutableList<ImmutableList<Integer>> schedulingTasks(int tasks, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(tasks);
		Map<Integer, List<Integer>> graph = graphInit(tasks);
		buildGraph(tasks, m, inDegree, graph);
		List<Integer> list = newArrayList();
		Queue<Integer> queue = sources(inDegree);
		List<ImmutableList<Integer>> lists = newArrayList();
		allTopologicalSorts(queue, list, lists, inDegree, graph);
		return newList(lists);
	}

	private static void allTopologicalSorts(Queue<Integer> queue, 
			List<Integer> list, List<ImmutableList<Integer>> lists,
			Map<Integer, Integer> inDegree, Map<Integer, List<Integer>> graph) {
		if (!queue.isEmpty()) {
			for (int vertex : queue) {
				list.add(vertex);
				Queue<Integer> next = newLinkedList(queue);
				next.remove(vertex);
				List<Integer> children = graph.get(vertex);
				for (int child : children) {
					int f = inDegree.getOrDefault(child, 0) - 1;
					inDegree.put(child, f);
					if (f == 0) {
						next.add(child);
					}
				}
				// recursive call to print other orderings
				allTopologicalSorts(next, list, lists, inDegree, graph);
				// backtrack: remove the vertex from the topological sort
				// and put all of its children back to be considered in the
				// next source instead of the current vertex
				list.remove(list.size() - 1);
				for (int child : children) {
					inDegree.put(child, inDegree.get(child) + 1);
				}
			}
		}
		if (list.size() == inDegree.size()) {
			lists.add(newList(list));
		}
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
		int[][] m1 = { { 3, 2 }, { 3, 0 }, { 2, 0 }, { 2, 1 } };
		int[][] m2 = { { 2, 5 }, { 0, 5 }, { 0, 4 }, { 1, 4 }, { 3, 2 }, { 1, 3 } };
		System.out.println(schedulingTasks(3, m0)); // [0, 1, 2]
		System.out.println(schedulingTasks(4, m1)); // [3, 2, 0, 1], [3, 2, 1, 0]
		System.out.println(schedulingTasks(6, m2)); // 0, 1, 4, 3, 2, 5 ....
	}
}

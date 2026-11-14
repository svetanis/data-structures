package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Order of Tasks With Prerequisites (the same task as 210. Course Schedule II)
//
// Input: n tasks labelled 0..n-1 and int[][] m of pairs [a, b], meaning task a must be done
// before task b. Assumes distinct pairs with a != b.
// Returns one order that respects every pair, or an empty list when the pairs form a loop.
// More than one order is usually correct; this returns the one the queue produces.
//
// The one idea: a task is READY when no pair still points at it (its in-degree is 0). Take a
// ready task, append it to the answer, and cross off the pairs leaving it; any task whose count
// just reached 0 is now ready. A task is appended only after all the tasks that must precede it,
// so the answer is valid. If fewer than n tasks were appended, the rest wait on each other: a loop.
//
// Time: O(V + E) -- each task enters the queue once and each pair is crossed off once.
// Space: O(V + E) -- the graph, the in-degree map and the queue.
//
// Siblings: IsTasksScheduling is the same walk returning only yes/no; AllTasksScheduleOrdering
// returns every valid order; CourseScheduleSubmit is the array version for 210.

public final class TasksScheduleOrdering {

	public static List<Integer> schedulingTasks(int tasks, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(tasks);
		Map<Integer, List<Integer>> graph = graphInit(tasks);
		buildGraph(tasks, m, inDegree, graph);
		List<Integer> list = new ArrayList<>(); // ORDER: the tasks taken so far
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src); // TAKE
			List<Integer> children = graph.get(src);
			for (int child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1; // CROSS OFF: one pair into child is used up
				if (f == 0) {
					queue.add(child); // READY
				}
				inDegree.put(child, f);
			}
		}
		// RETURN: a topological order that misses some tasks means a loop between
		// them, so there is no valid order
		if (list.size() != tasks) {
			return new ArrayList<>();
		}
		return list;
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (int key : map.keySet()) {
			if (map.get(key) == 0) {
				queue.add(key);
			}
		}
		return queue;
	}

	private static void buildGraph(int v, int[][] m, Map<Integer, Integer> inDegree,
			Map<Integer, List<Integer>> map) {
		for (int i = 0; i < m.length; i++) {
			int parent = m[i][0];
			int child = m[i][1];
			map.get(parent).add(child); // ARROW: parent must come before child
			int fc = inDegree.getOrDefault(child, 0);
			inDegree.put(child, fc + 1);
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
		int[][] m0 = { { 0, 1 }, { 1, 2 } };
		int[][] m1 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
		int[][] m2 = { { 2, 5 }, { 0, 5 }, { 0, 4 }, { 1, 4 }, { 3, 2 }, { 1, 3 } };
		System.out.println(schedulingTasks(3, m0)); // [0, 1, 2]
		System.out.println(schedulingTasks(3, m1)); // []
		System.out.println(schedulingTasks(6, m2)); // [0, 1, 4, 3, 2, 5]
	}
}

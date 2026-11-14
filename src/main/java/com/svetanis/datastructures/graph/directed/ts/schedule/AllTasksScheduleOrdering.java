package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// All Orderings of Tasks With Prerequisites (every topological order)
//
// Input: n tasks labelled 0..n-1 and int[][] m of pairs [a, b], meaning task a must be done before
// task b. Assumes distinct pairs and no pair [a, a]. Intended for small n -- the answer can hold
// n! orders (n = 10 is already 3,628,800 when there are no pairs).
// Returns every order of the n tasks that respects all the pairs; empty if the pairs form a loop.
//
// The one idea: a task is READY when every task that must come before it is already placed. Any
// ready task may be placed next, so try each one in turn. Place it, mark its dependants as one
// step closer to ready, recurse on what is now ready, and when that returns undo the placement
// (BACKTRACK) so the next ready task gets its turn from the same starting state. When all n tasks
// are placed, copy the list out as one finished order.
//
// Time: O(n! * (n^2 + E)) -- up to n! orders; building one order takes n placements, and each
// placement copies the ready queue (at most n) and visits the placed task's arrows.
// Space: O(n + E) for the graph and the recursion, plus the output itself.
//
// Siblings: TasksScheduleOrdering returns just one order; this one returns all of them.

public final class AllTasksScheduleOrdering {

	public static List<List<Integer>> schedulingTasks(int tasks, int[][] m) {
		Map<Integer, Integer> inDegree = inDegreeInit(tasks);
		Map<Integer, List<Integer>> graph = graphInit(tasks);
		buildGraph(tasks, m, inDegree, graph);
		List<Integer> list = new ArrayList<>(); // PLACED: the order built so far
		Queue<Integer> queue = sources(inDegree);
		List<List<Integer>> lists = new ArrayList<>(); // RESULT: every finished order
		allTopologicalSorts(queue, list, lists, inDegree, graph);
		return lists;
	}

	private static void allTopologicalSorts(Queue<Integer> queue,
			List<Integer> list, List<List<Integer>> lists,
			Map<Integer, Integer> inDegree, Map<Integer, List<Integer>> graph) {
		if (!queue.isEmpty()) {
			for (int vertex : queue) { // TRY: each ready task as the next one
				list.add(vertex);
				Queue<Integer> next = new ArrayDeque<>(queue);
				next.remove(vertex); // SKIP: the placed task leaves the ready set
				List<Integer> children = graph.get(vertex);
				for (int child : children) { // RELEASE: each dependant is one step closer to ready
					int f = inDegree.getOrDefault(child, 0) - 1;
					inDegree.put(child, f);
					if (f == 0) {
						next.add(child);
					}
				}
				allTopologicalSorts(next, list, lists, inDegree, graph); // RECURSE
				// BACKTRACK: take the task back out and give each dependant its
				// waiting count back, so the next ready task starts from the same state
				list.remove(list.size() - 1);
				for (int child : children) {
					inDegree.put(child, inDegree.get(child) + 1);
				}
			}
		}
		if (list.size() == inDegree.size()) { // RECORD: every task placed, copy the order out
			lists.add(new ArrayList<>(list));
		}
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = new ArrayDeque<>();
		for (int key : map.keySet()) { // READY: every task with in-degree 0
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
		int[][] m1 = { { 3, 2 }, { 3, 0 }, { 2, 0 }, { 2, 1 } };
		int[][] m2 = { { 2, 5 }, { 0, 5 }, { 0, 4 }, { 1, 4 }, { 3, 2 }, { 1, 3 } };
		System.out.println(schedulingTasks(3, m0)); // [[0, 1, 2]]
		System.out.println(schedulingTasks(4, m1)); // [[3, 2, 0, 1], [3, 2, 1, 0]]
		System.out.println(schedulingTasks(6, m2)); // 13 orders, the first is [0, 1, 4, 3, 2, 5]
	}
}

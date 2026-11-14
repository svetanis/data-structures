package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 207. Course Schedule
//
// Input: n courses 0..n-1 and prerequisites as a list of pairs [a, b]. LeetCode reads [a, b] as
// "b before a"; this file draws the arrow a -> b instead, which is harmless because a loop is a
// loop whichever way every arrow points. On LeetCode: 1 <= n <= 2000, 0 <= pairs <= 5000,
// distinct pairs, a != b.
// Returns true when all n courses can be taken, which is exactly when the arrows form no loop.
//
// The one idea: a course is READY when no arrow still points at it (its in-degree is 0). Take a
// ready course and cross off the arrows leaving it; any course whose count just reached 0 is
// now ready. If every course gets taken there is no loop. If the queue runs dry first, the
// courses left each have an arrow from another one of them: a loop.
//
// Time: O(V + E) -- each course enters the queue once and each arrow is crossed off once.
// Space: O(V + E) -- the map of arrows, the in-degree map and the queue.
//
// Siblings: IsCourseScheduleSubmit is the same method on int arrays and int[][] pairs, with the
// arrow drawn the LeetCode way. IsCourseScheduleMapBfs is a line-for-line copy of this file.

public final class IsCourseScheduleMapSubmit {

	public static boolean isValidCourseSchedule(int n, List<List<Integer>> prerequisites) {
		Map<Integer, List<Integer>> graph = graph(n, prerequisites);
		return topoSort(graph);
	}

	private static boolean topoSort(Map<Integer, List<Integer>> graph) {
		Map<Integer, Integer> inDegree = inDegree(graph);
		Queue<Integer> queue = sources(inDegree);
		List<Integer> list = new ArrayList<>(); // TAKEN: the courses taken so far
		while (!queue.isEmpty()) {
			int src = queue.poll();
			list.add(src); // TAKE
			for (int neighbor : graph.get(src)) {
				int ind = inDegree.get(neighbor) - 1; // CROSS OFF: one arrow into neighbor is used up
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor); // READY
				}
			}
		}
		return graph.size() == list.size(); // RETURN: short == a loop
	}

	private static Queue<Integer> sources(Map<Integer, Integer> inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (int key : inDegree.keySet()) {
			if (inDegree.get(key) == 0) {
				queue.add(key);
			}
		}
		return queue;
	}

	private static Map<Integer, List<Integer>> graph(int n, List<List<Integer>> prerequisites) {
		Map<Integer, List<Integer>> map = graphInit(n);
		for (List<Integer> prerequisite : prerequisites) {
			map.get(prerequisite.get(0)).add(prerequisite.get(1)); // ARROW: a -> b
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int n) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			map.put(i, new ArrayList<>());
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
		Map<Integer, Integer> map = new HashMap<>();
		for (Integer node : graph.keySet()) {
			map.put(node, 0);
		}
		return map;
	}

	public static void main(String[] args) {
		List<List<Integer>> prerequisites = new ArrayList<>();
		prerequisites.add(Arrays.asList(0, 1));
		System.out.println(isValidCourseSchedule(2, prerequisites)); // true

		List<List<Integer>> prerequisites2 = new ArrayList<>();
		prerequisites2.add(Arrays.asList(0, 1));
		prerequisites2.add(Arrays.asList(1, 0));
		System.out.println(isValidCourseSchedule(2, prerequisites2)); // false

		List<List<Integer>> prerequisites3 = new ArrayList<>();
		prerequisites3.add(Arrays.asList(0, 1));
		prerequisites3.add(Arrays.asList(1, 2));
		prerequisites3.add(Arrays.asList(2, 3));
		prerequisites3.add(Arrays.asList(3, 1));
		System.out.println(isValidCourseSchedule(4, prerequisites3)); // false
	}
}

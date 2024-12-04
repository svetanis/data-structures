package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 207. Course Schedule

public final class IsCourseScheduleSubmit {
	// Time Complexity: O(V + E)

	public static boolean isValidCourseSchedule(int n, int[][] prerequisites) {
		int[] inDegree = new int[n];
		List<List<Integer>> graph = graphInit(n);
		for (int[] prerequisite : prerequisites) {
			int course = prerequisite[0];
			int prereq = prerequisite[1];
			graph.get(prereq).add(course);
			inDegree[course]++;
		}
		return topoSort(graph, inDegree);
	}

	private static boolean topoSort(List<List<Integer>> g, int[] inDegree) {
		int count = 0;
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			count++;
			int course = queue.poll();
			for (int neighbor : g.get(course)) {
				inDegree[neighbor]--;
				if (inDegree[neighbor] == 0) {
					queue.add(neighbor);
				}
			}
		}
		return count == g.size();
	}

	private static Queue<Integer> sources(int[] inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (int i = 0; i < inDegree.length; i++) {
			if (inDegree[i] == 0) {
				queue.add(i);
			}
		}
		return queue;
	}

	private static List<List<Integer>> graphInit(int n) {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(new ArrayList<>());
		}
		return list;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 0 } };
		System.out.println(isValidCourseSchedule(2, g1)); // true

		int[][] g2 = { { 1, 0 }, { 0, 1 } };
		System.out.println(isValidCourseSchedule(2, g2)); // false
	}
}

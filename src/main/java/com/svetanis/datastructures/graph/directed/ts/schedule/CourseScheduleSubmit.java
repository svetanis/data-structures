package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.java.base.utils.Print;

// 210. Course Schedule II

public final class CourseScheduleSubmit {
	// Time Complexity: O(V + E)

	public static int[] order(int n, int[][] prerequisites) {
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

	private static int[] topoSort(List<List<Integer>> g, int[] inDegree) {
		int count = 0;
		int[] a = new int[g.size()];
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int course = queue.poll();
			a[count++] = course;
			for (int neighbor : g.get(course)) {
				inDegree[neighbor]--;
				if (inDegree[neighbor] == 0) {
					queue.add(neighbor);
				}
			}
		}
		return count == g.size() ? a : new int[0];
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
		Print.print(order(2, g1)); // 0,1

		int[][] g2 = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
		Print.print(order(4, g2)); // 0,2,1,3
	}
}

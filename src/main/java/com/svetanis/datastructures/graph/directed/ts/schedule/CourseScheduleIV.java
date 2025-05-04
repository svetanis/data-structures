package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 1462. Course Schedule IV

public final class CourseScheduleIV {

	public static List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries){
		int[] inDegree = new int[n];
		boolean[][] f = new boolean[n][n];
		List<List<Integer>> graph = graphInit(n);
		for (int[] prerequisite : prerequisites) {
			int course = prerequisite[1];
			int prereq = prerequisite[0];
			graph.get(prereq).add(course);
			inDegree[course]++;
		}
		topoSort(n, graph, inDegree, f);
		List<Boolean> list = new ArrayList<>();
		for(int[] query : queries) {
			list.add(f[query[0]][query[1]]);
		}
		return list;
	}
	
	private static void topoSort(int n, List<List<Integer>> g, int[] inDegree, boolean[][] f) {
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int course = queue.poll();
			for (int neighbor : g.get(course)) {
				f[course][neighbor] = true;
				for(int pre = 0; pre < n; pre++) {
					f[pre][neighbor] |= f[pre][course];
				}
				inDegree[neighbor]--;
				if (inDegree[neighbor] == 0) {
					queue.add(neighbor);
				}
			}
		}
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
		int[][] p1 = {{1,0}};
		int[][] q1 = {{0,1},{1,0}};
		System.out.println(checkIfPrerequisite(2, p1, q1)); // false, true
		int[][] p3 = {{1,2},{1,0},{2,0}};
		int[][] q3 = {{1,0},{1,2}};
		System.out.println(checkIfPrerequisite(3, p3, q3)); // true, true
	}
}

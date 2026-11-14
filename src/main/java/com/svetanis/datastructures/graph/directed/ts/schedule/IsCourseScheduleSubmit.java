package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 207. Course Schedule
//
// Input: numCourses n (courses 0..n-1) and prerequisites, pairs [a, b] meaning b must be taken
// BEFORE a. On LeetCode: 1 <= n <= 2000, 0 <= prerequisites.length <= 5000, all pairs distinct,
// a != b.
// Returns true when all n courses can be taken, which is exactly when the pairs form no loop.
//
// The one idea: draw an arrow b -> a for each pair. A course is READY when every arrow into it has
// been used up (its in-degree is 0). Take a ready course and use up the arrows leaving it; any
// course whose count just reached 0 is now ready. If all n get taken, no loop. If the queue runs
// dry first, the courses left each wait on another one of them: a loop.
//
// Time: O(V + E) -- each course enters the queue once and each arrow is used up once.
// Space: O(V + E) -- the arrows, the in-degree counts and the queue.
//
// Siblings: CourseScheduleSubmit (210) writes the courses down in the order taken;
// IsCourseScheduleMapSubmit does the same with maps and List<List<Integer>> pairs.

public final class IsCourseScheduleSubmit {

	public static boolean isValidCourseSchedule(int n, int[][] prerequisites) {
		int[] inDegree = new int[n]; // CARDS: inDegree[c] -- prerequisites of c not yet taken
		List<List<Integer>> graph = graphInit(n);
		for (int[] prerequisite : prerequisites) {
			int course = prerequisite[0];
			int prereq = prerequisite[1];        // [a, b] means b BEFORE a
			graph.get(prereq).add(course);       // ARROW: so it runs b -> a
			inDegree[course]++;                  // and it is `a` that owes one more
		}
		return topoSort(graph, inDegree);
	}

	private static boolean topoSort(List<List<Integer>> g, int[] inDegree) {
		int count = 0;
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			count++;                             // TAKE: one more course taken
			int course = queue.poll();
			for (int neighbor : g.get(course)) {
				inDegree[neighbor]--;            // USE UP: one of its prerequisites is now done
				if (inDegree[neighbor] == 0) {
					queue.add(neighbor);         // READY: its last prerequisite just landed
				}
			}
		}
		return count == g.size();                // RETURN: ran out of queue early == a cycle
	}

	private static Queue<Integer> sources(int[] inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (int i = 0; i < inDegree.length; i++) {
			if (inDegree[i] == 0) {              // READY: owes nothing, so it can be taken now
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

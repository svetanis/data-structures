package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 210. Course Schedule II
//
// Input: numCourses n (courses 0..n-1) and prerequisites, pairs [a, b] meaning b must be taken
// BEFORE a. On LeetCode: 1 <= n <= 2000, 0 <= prerequisites.length <= n * (n - 1), all pairs
// distinct, a != b.
// Returns one order that takes every course after its prerequisites, or an empty array when the
// pairs form a loop. More than one order is usually correct; any valid one is accepted.
//
// The one idea: draw an arrow b -> a for each pair. A course is READY when every arrow into it has
// been used up (its in-degree is 0). Take a ready course, write it into the answer, and use up the
// arrows leaving it; any course whose count just reached 0 becomes ready. Whatever is written
// stays valid because a course is written only after all its prerequisites were. If the queue
// runs dry before n courses are written, the courses left all wait on each other: a loop.
//
// Names: count[c] is how many arrows still point into course c (its in-degree); g[c] lists the
// courses c's arrows point to; index is how many courses are taken, and result holds them in the
// order taken. For a pair [a, b]: count[a] goes up and a goes into g[b] -- the pair is written
// after-then-before, so the arrow ENDS at pair[0].
//
// Time: O(V + E) -- each course enters the queue once and each arrow is used up once.
// Space: O(V + E) -- the arrows, the counts and the queue.
//
// Siblings: IsCourseScheduleSubmit (207) counts the courses instead of writing them down;
// CourseScheduleIV (1462) records who reaches whom.

public final class CourseScheduleSubmit {

	@SuppressWarnings("unchecked") // a generic array cannot be created directly
	public static int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] count = new int[numCourses]; // arrows still pointing INTO each course
		List<Integer>[] g = new List[numCourses]; // the courses each course's arrows point TO
		Arrays.setAll(g, k -> new ArrayList<>()); // fills every cell once -- no loop around it
		for (int[] prerequisite : prerequisites) { // BUILD
			int a = prerequisite[0];
			int b = prerequisite[1]; // take b before a: the arrow is b -> a
			count[a] += 1; // a waits for one more course
			g[b].add(a); // taking b must lower a's count
		}
		Deque<Integer> queue = new ArrayDeque<>(); // free, not taken yet
		for (int node = 0; node < numCourses; node++) { // SEED
			if (count[node] == 0) {
				queue.offer(node); // nothing points in: free from the start
			}
		}
		int index = 0; // how many courses are taken
		int[] result = new int[numCourses];
		while (!queue.isEmpty()) {
			int course = queue.poll(); // TAKE
			result[index++] = course;
			for (int neighbor : g[course]) { // FREE: cross out course's arrows
				count[neighbor] -= 1;
				if (count[neighbor] == 0) {
					queue.offer(neighbor); // its last arrow: free now
				}
			}
		}
		return index == numCourses ? result : new int[0]; // CHECK: fewer taken -> a loop
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 0 } };
		System.out.println(Arrays.toString(findOrder(2, g1))); // [0, 1]

		// a topological order is NOT unique. 1 and 2 are mutually
		// unconstrained here, so 0,1,2,3 and 0,2,1,3 are both correct.
		// never assert string equality on this problem -- assert the
		// property: does any edge run backwards in the output?
		int[][] g2 = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
		System.out.println(Arrays.toString(findOrder(4, g2))); // any valid order, e.g. [0, 1, 2, 3]

		// 0 -> 1, then 1 and 2 wait on each other: course 0 is taken, then nothing is ever free
		int[][] g3 = { { 1, 0 }, { 2, 1 }, { 1, 2 } };
		System.out.println(Arrays.toString(findOrder(3, g3))); // []
	}
}

package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 1376. Time Needed to Inform All Employees
//
// Input: n employees numbered 0 to n - 1; hid -- the head of the company; manager[i] -- the direct
// manager of employee i (manager[hid] is -1); times[i] -- the minutes employee i needs to tell all
// of his or her direct subordinates (they are all told at the same moment). Returns the minutes
// until every employee knows. On LeetCode: 1 <= n <= 10^5, 0 <= hid < n, 0 <= times[i] <= 1000,
// times[i] == 0 for an employee with no subordinates, and the managers form a tree under the head.
//
// The one idea: one queue entry is {an employee, the minute at which that employee is told}. The
// head is told at minute 0. When an employee is taken from the queue, every direct subordinate is
// put in with that employee's minute plus times of that employee: the subordinates are all told
// when the manager has finished telling. The answer is the latest minute any entry carries. The
// queue replaces the recursion, so a very long chain of managers is no problem.
//
// Time: O(n) -- the lists are built in one pass, and each employee enters the queue once.
// Space: O(n) -- the lists and the queue.
//
// Siblings: TimeToInformAllEmployees is the recursive version of the same walk.

public final class TimeToInformAllEmployeesBfs {

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		List<Integer>[] graph = graph(n, manager);
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { hid, 0 }); // BASE: the head is told at minute 0
		int max = 0;
		while (!dq.isEmpty()) {
			int[] curr = dq.poll(); // TAKE
			int cid = curr[0];
			int time = curr[1];
			max = Math.max(max, time); // RECORD: the latest minute so far
			for (int eid : graph[cid]) { // WALK: each direct subordinate of cid
				dq.offer(new int[] { eid, time + times[cid] }); // told when cid has finished
			}
		}
		return max; // RETURN
	}

	private static List<Integer>[] graph(int n, int[] manager) {
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, g -> new ArrayList<>());
		for (int eid = 0; eid < n; eid++) { // BUILD: manager -> list of direct subordinates
			int mid = manager[eid];
			if (mid >= 0) { // SKIP: the head has no manager
				graph[mid].add(eid);
			}
		}
		return graph;
	}

	public static void main(String[] args) {
		int[] m1 = { -1 };
		int[] t1 = { 0 };
		System.out.println(totalTime(1, 0, m1, t1)); // 0

		int[] m2 = { 2, 2, -1, 2, 2, 2 };
		int[] t2 = { 0, 0, 1, 0, 0, 0 };
		System.out.println(totalTime(6, 2, m2, t2)); // 1
	}
}

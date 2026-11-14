package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1376. Time Needed to Inform All Employees
//
// Input: n employees numbered 0 to n - 1; hid -- the head of the company; manager[i] -- the direct
// manager of employee i (manager[hid] is -1); times[i] -- the minutes employee i needs to tell all
// of his or her direct subordinates (they are all told at the same moment). Returns the minutes
// until every employee knows. On LeetCode: 1 <= n <= 10^5, 0 <= hid < n, 0 <= times[i] <= 1000,
// times[i] == 0 for an employee with no subordinates, and the managers form a tree under the head.
//
// The one idea: one dfs call is one employee, and it returns the minutes that pass from the moment
// that employee is told until everyone under him or her is told. The employee spends times[src] to
// tell all the direct subordinates at once, and then each of them needs as long as his or her own
// call says. All of them work in parallel, so only the slowest counts: the answer for src is
// times[src] plus the largest answer among the subordinates (an employee with nobody under has no
// subordinate to wait for, so the answer is 0).
//
// Time: O(n) -- the lists are built in one pass, and each employee is visited once.
// Space: O(n) -- the lists of subordinates. The recursion is as deep as the chain of managers: a
// chain of 100,000 managers needs 100,000 nested calls, more than a default Java stack holds (run
// with a larger -Xss); LeetCode's stack is larger.
//
// Siblings: TimeToInformAllEmployeesBfs does it with a queue, so it has no deep recursion;
// TimeToInformAllEmployeesTopDown adds a memo table to this recursion (every employee is reached
// once, so the table is never read); TimeToInformAllEmployeesSimple goes up the manager array
// instead of down a list of subordinates.

public final class TimeToInformAllEmployees {

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		List<Integer>[] graph = graph(n, manager);
		return dfs(graph, hid, times); // RETURN: minutes until the whole company knows
	}

	private static int dfs(List<Integer>[] g, int src, int[] times) {
		int max = 0; // BASE: no subordinates, nothing more to wait for
		for (int eid : g[src]) { // WALK: each direct subordinate
			int curr = times[src] + dfs(g, eid, times); // the time src spends, then that subtree
			max = Math.max(max, curr); // TAKE: only the slowest subtree counts
		}
		return max;
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

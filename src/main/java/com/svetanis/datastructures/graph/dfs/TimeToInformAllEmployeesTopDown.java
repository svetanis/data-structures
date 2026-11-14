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
// call says; all work in parallel, so the answer for src is times[src] plus the largest answer
// among the subordinates. One entry of dp holds that answer for one employee, or null if it has not
// been worked out yet. Every employee has one manager, so the walk from the head reaches each
// employee exactly once and an entry of dp is never read a second time: the table changes no answer
// and no running time.
//
// Time: O(n) -- the lists are built in one pass, and each employee is visited once.
// Space: O(n) -- the lists and dp. The recursion is as deep as the chain of managers: a chain of
// 100,000 managers needs 100,000 nested calls, more than a default Java stack holds (run with a
// larger -Xss); LeetCode's stack is larger.
//
// Siblings: TimeToInformAllEmployees is the same recursion without the table;
// TimeToInformAllEmployeesBfs uses a queue, so it has no depth limit.

public final class TimeToInformAllEmployeesTopDown {

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		List<Integer>[] graph = graph(n, manager);
		Integer[] dp = new Integer[n];
		return dfs(graph, hid, times, dp); // RETURN: minutes until the whole company knows
	}

	private static int dfs(List<Integer>[] g, int src, int[] times, Integer[] dp) {
		if (dp[src] != null) { // MEMO: already worked out
			return dp[src];
		}
		int max = 0; // BASE: no subordinates, nothing more to wait for
		for (int eid : g[src]) { // WALK: each direct subordinate
			int curr = times[src] + dfs(g, eid, times, dp); // src's time, then that subtree
			max = Math.max(max, curr); // TAKE: only the slowest subtree counts
		}
		return dp[src] = max; // RECORD
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

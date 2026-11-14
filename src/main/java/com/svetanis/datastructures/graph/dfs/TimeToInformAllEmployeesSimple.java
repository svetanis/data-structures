package com.svetanis.datastructures.graph.dfs;

// 1376. Time Needed to Inform All Employees
//
// Input: n employees numbered 0 to n - 1; hid -- the head of the company (not used here: the head
// is the one employee whose manager is -1); manager[i] -- the direct manager of employee i;
// times[i] -- the minutes employee i needs to tell all of his or her direct subordinates (they are
// all told at the same moment). Returns the minutes until every employee knows. On LeetCode:
// 1 <= n <= 10^5, 0 <= times[i] <= 1000, times[i] == 0 for an employee with no subordinates, and
// the managers form a tree under the head.
//
// The one idea: look up the chain of managers instead of down the tree. After dfs(src), times[src]
// holds the minutes from the start until src has finished telling: its own times plus the same
// number for its manager, all the way up to the head. An employee with nobody under has times 0, so
// for such an employee this number is exactly the minute it is told; and the latest employee to be
// told is always one with nobody under, so the largest number over all employees is the answer.
// Once an employee's number is final, manager[src] is set to -1 to say "done", so each chain is
// climbed only once.
//
// This changes the arrays it is given: times becomes running totals and manager becomes all -1.
//
// Time: O(n) -- each employee is finished once; a finished employee stops the climb of everyone
// below it.
// Space: O(n) -- the recursion climbs one chain of managers, up to n calls deep: from a worker at
// the bottom of a chain of 100,000 managers it is 100,000 nested calls, more than a default Java
// stack holds (run with a larger -Xss); LeetCode's stack is larger. Nothing else is allocated.
//
// Siblings: TimeToInformAllEmployees goes down the tree of subordinates;
// TimeToInformAllEmployeesBfs does the same with a queue; TimeToInformAllEmployeesTopDown adds a
// memo table to the recursion.

public final class TimeToInformAllEmployeesSimple {

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		int max = 0;
		for (int id = 0; id < n; id++) { // WALK: every employee
			max = Math.max(max, dfs(id, manager, times)); // TAKE: the latest finish so far
		}
		return max; // RETURN
	}

	private static int dfs(int src, int[] manager, int[] times) {
		if (manager[src] != -1) { // BASE: the head, or an employee already finished
			times[src] += dfs(manager[src], manager, times); // add the manager's total
			manager[src] = -1; // RECORD: src is finished, never climb through it again
		}
		return times[src];
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

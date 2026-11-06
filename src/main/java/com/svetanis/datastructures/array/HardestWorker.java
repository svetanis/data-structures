package com.svetanis.datastructures.array;

// 2432. The Employee That Worked on the Longest Task
//
// Input: n -- the number of employees, ids 0 to n - 1; logs[i] = { id, leaveTime } -- the employee
// who did task i and the time it ended. Task 0 starts at time 0, and every other task starts the
// moment the one before it ended. Returns the id of the employee whose task took longest; on a
// tie, the smallest id. On LeetCode: 2 <= n <= 500, 1 <= logs.length <= 500, logs[i].length == 2,
// 0 <= id < n, 1 <= leaveTime <= 500, id differs between consecutive logs, leaveTime strictly
// increases.
//
// The one idea: one log is one task, and its length is its leave time minus the previous task's
// leave time (0 for the first). Walk the logs in order, remembering the longest length and who
// did it; a longer task replaces the holder, and an equal one replaces it only with a smaller id.
//
// Time: O(m) -- one pass over the m logs.
// Space: O(1) -- the best length, the best id, the previous leave time.

public final class HardestWorker {

	public static int hardestWorker(int n, int[][] logs) {
		int max = 0; // MAX: the longest task so far
		int eid = 0; // HOLDER: the employee who did it
		int start = 0; // START: when the current task began
		for (int[] log : logs) {
			int worker = log[0];
			int end = log[1];
			int time = end - start;
			boolean equals = time == max && eid > worker; // TIE: the smaller id wins
			if (time > max || equals) {
				eid = worker; // TAKE: a longer task, or an equal one with a smaller id
				max = time;
			}
			start = end;
		}
		return eid;
	}

	public static void main(String[] args) {
		int[][] logs1 = { { 0, 3 }, { 2, 5 }, { 0, 9 }, { 1, 15 } };
		System.out.println(hardestWorker(10, logs1)); // 1

		int[][] logs2 = { { 1, 1 }, { 3, 7 }, { 2, 12 }, { 7, 17 } };
		System.out.println(hardestWorker(26, logs2)); // 3

		int[][] logs3 = { { 0, 10 }, { 1, 20 } };
		System.out.println(hardestWorker(2, logs3)); // 0
	}
}

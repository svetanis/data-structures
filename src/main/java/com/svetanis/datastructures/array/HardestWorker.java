package com.svetanis.datastructures.array;

// 2432. The Employee That Worked on the Longest Task

public final class HardestWorker {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int hardestWorker(int n, int[][] logs) {
		int max = 0;
		int eid = 0;
		int start = 0;
		for (int[] log : logs) {
			int worker = log[0];
			int end = log[1];
			int time = end - start;
			boolean equals = time == max && eid > worker;
			if (time > max || equals) {
				eid = worker;
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

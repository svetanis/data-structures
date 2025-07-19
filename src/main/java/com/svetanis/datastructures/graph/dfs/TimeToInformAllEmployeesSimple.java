package com.svetanis.datastructures.graph.dfs;

// 1376. Time Needed to Inform All Employees

public final class TimeToInformAllEmployeesSimple {
	// Time Complexity: O(N)
	// Space Complexity: O(N)

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		int max = 0;
		for (int id = 0; id < n; id++) {
			max = Math.max(max, dfs(id, manager, times));
		}
		return max;
	}

	private static int dfs(int src, int[] manager, int[] times) {
		if (manager[src] != -1) {
			times[src] += dfs(manager[src], manager, times);
			manager[src] = -1;
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

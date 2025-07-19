package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1376. Time Needed to Inform All Employees

public final class TimeToInformAllEmployeesTopDown {
	// Time Complexity: O(N)
	// Space Complexity: O(N)

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		List<Integer>[] graph = graph(n, manager);
		Integer[] dp = new Integer[n];
		return dfs(graph, hid, times, dp);
	}

	private static int dfs(List<Integer>[] g, int src, int[] times, Integer[] dp) {
		if (dp[src] != null) {
			return dp[src];
		}
		int max = 0;
		for (int eid : g[src]) {
			int curr = times[src] + dfs(g, eid, times, dp);
			max = Math.max(max, curr);
		}
		return dp[src] = max;
	}

	private static List<Integer>[] graph(int n, int[] manager) {
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, g -> new ArrayList<>());
		for (int eid = 0; eid < n; eid++) {
			int mid = manager[eid];
			if (mid >= 0) {
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

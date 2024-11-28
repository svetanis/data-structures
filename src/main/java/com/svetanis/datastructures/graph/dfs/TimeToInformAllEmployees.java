package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1376. Time Needed to Inform All Employees

public final class TimeToInformAllEmployees {
	// Time Complexity: O(N)
	// Space Complexity: O(N)

	public static int totalTime(int n, int hid, int[] manager, int[] time) {
		List<Integer>[] graph = graph(n, manager);
		return dfs(graph, hid, time);
	}

	private static int dfs(List<Integer>[] g, int src, int[] time) {
		int max = 0;
		for (int eid : g[src]) {
			int curr = time[src] + dfs(g, eid, time);
			max = Math.max(max, curr);
		}
		return max;
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

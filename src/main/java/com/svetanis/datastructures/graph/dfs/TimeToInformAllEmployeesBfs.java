package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 1376. Time Needed to Inform All Employees

public final class TimeToInformAllEmployeesBfs {
	// Time Complexity: O(N)
	// Space Complexity: O(N)

	public static int totalTime(int n, int hid, int[] manager, int[] times) {
		List<Integer>[] graph = graph(n, manager);
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { hid, 0 });
		int max = 0;
		while (!dq.isEmpty()) {
			int[] curr = dq.poll();
			int cid = curr[0];
			int time = curr[1];
			max = Math.max(max, time);
			for (int eid : graph[cid]) {
				dq.offer(new int[] { eid, time + times[cid] });
			}
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

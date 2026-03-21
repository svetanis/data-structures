package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 582. Kill Process

public final class KillProcessBfs {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public List<Integer> killProcess(List<Integer> pid, 
			List<Integer> ppid, int kill) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < pid.size(); i++) {
			int parent = ppid.get(i);
			int child = pid.get(i);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
		}
		List<Integer> list = new ArrayList<>();
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(kill);
		while (!dq.isEmpty()) {
			int node = dq.poll();
			list.add(node);
			List<Integer> children = map.getOrDefault(node, new ArrayList<>());
			for (int child : children) {
				dq.offer(child);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		KillProcessBfs kp = new KillProcessBfs();
		List<Integer> pid = asList(1, 3, 10, 5);
		List<Integer> ppid = asList(3, 0, 5, 3);
		System.out.println(kp.killProcess(pid, ppid, 5)); // 5, 10
		System.out.println(kp.killProcess(asList(1), asList(0), 1)); // 1
	}
}

package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 582. Kill Process

public final class KillProcessDfs {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private Map<Integer, List<Integer>> map;

	public List<Integer> killProcess(List<Integer> pid, 
			List<Integer> ppid, int kill) {
		this.map = new HashMap<>();
		for (int i = 0; i < pid.size(); i++) {
			int parent = ppid.get(i);
			int child = pid.get(i);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
		}
		List<Integer> list = new ArrayList<>();
		dfs(kill, list);
		return list;
	}

	private void dfs(int kill, List<Integer> list) {
		list.add(kill);
		for (int child : map.getOrDefault(kill, new ArrayList<>())) {
			dfs(child, list);
		}
	}

	public static void main(String[] args) {
		KillProcessDfs kp = new KillProcessDfs();
		List<Integer> pid = asList(1, 3, 10, 5);
		List<Integer> ppid = asList(3, 0, 5, 3);
		System.out.println(kp.killProcess(pid, ppid, 5)); // 5, 10
		System.out.println(kp.killProcess(asList(1), asList(0), 1)); // 1
	}
}

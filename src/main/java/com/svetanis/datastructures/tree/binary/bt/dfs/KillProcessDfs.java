package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 582. Kill Process
//
// Input: pid -- the id of each process; ppid -- the id of each process's parent, so pid[i] has
// parent ppid[i] and the one process with no parent has ppid 0; kill -- the id of the process to
// kill. Killing a process kills all of its children, and theirs. Returns the ids of every killed
// process, in any order. On LeetCode: n == pid.length == ppid.length, 1 <= n <= 5 * 10^4,
// 1 <= pid[i] <= 5 * 10^4, all pid[i] are unique, 0 <= ppid[i] <= 5 * 10^4, there is exactly one
// root, and kill is one of the pids.
//
// The one idea: the two lists describe a tree, but only upward -- each process knows its parent.
// Killing needs the other direction, so first build a map from a parent's id to the list of its
// children's ids. The killed processes are then the subtree under kill: record kill, then do the
// same for each of its children.
//
// Time: O(n) -- the map is built in one pass; each process is visited once.
// Space: O(n) -- the map, and the recursion stack, which is as deep as the process tree.
//
// Known limit: a chain of about 5 * 10^4 processes (the LeetCode maximum) overflows the default
// stack. KillProcessDfsIterative is the same walk with an explicit stack and the same output
// order.
//
// Siblings: KillProcessBfs walks the same map breadth-first with a queue; the ids come out in a
// different order. KillProcessDfsIterative is this walk without recursion.

public final class KillProcessDfs {

	private Map<Integer, List<Integer>> map;

	public List<Integer> killProcess(List<Integer> pid, 
			List<Integer> ppid, int kill) {
		this.map = new HashMap<>();
		for (int i = 0; i < pid.size(); i++) {
			int parent = ppid.get(i);
			int child = pid.get(i);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(child); // MAP: parent id -> child ids
		}
		List<Integer> list = new ArrayList<>();
		dfs(kill, list); // WALK: the subtree under kill
		return list;
	}

	private void dfs(int kill, List<Integer> list) {
		list.add(kill); // RECORD: killed
		for (int child : map.getOrDefault(kill, new ArrayList<>())) {
			dfs(child, list); // WALK: its children die too
		}
	}

	public static void main(String[] args) {
		KillProcessDfs kp = new KillProcessDfs();
		List<Integer> pid = asList(1, 3, 10, 5);
		List<Integer> ppid = asList(3, 0, 5, 3);
		System.out.println(kp.killProcess(pid, ppid, 5)); // [5, 10]
		System.out.println(kp.killProcess(asList(1), asList(0), 1)); // [1]
	}
}

package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
// children's ids. The killed processes are then the subtree under kill: start a queue with kill,
// and for every id taken off the queue, record it and queue its children.
//
// Time: O(n) -- the map is built in one pass; each process enters the queue at most once.
// Space: O(n) -- the map and the queue.
//
// Siblings: KillProcessDfs walks the same map depth-first with recursion; the ids come out in a
// different order.

public final class KillProcessBfs {

	public List<Integer> killProcess(List<Integer> pid, 
			List<Integer> ppid, int kill) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < pid.size(); i++) {
			int parent = ppid.get(i);
			int child = pid.get(i);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(child); // MAP: parent id -> child ids
		}
		List<Integer> list = new ArrayList<>();
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(kill); // START: the process to kill
		while (!dq.isEmpty()) {
			int node = dq.poll();
			list.add(node); // RECORD: killed
			List<Integer> children = map.getOrDefault(node, new ArrayList<>());
			for (int child : children) {
				dq.offer(child); // TAKE: its children die too
			}
		}
		return list;
	}

	public static void main(String[] args) {
		KillProcessBfs kp = new KillProcessBfs();
		List<Integer> pid = asList(1, 3, 10, 5);
		List<Integer> ppid = asList(3, 0, 5, 3);
		System.out.println(kp.killProcess(pid, ppid, 5)); // [5, 10]
		System.out.println(kp.killProcess(asList(1), asList(0), 1)); // [1]
	}
}

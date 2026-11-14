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
// children's ids. The killed processes are then the subtree under kill. This file walks it
// depth-first with an explicit stack instead of recursion: pop an id, record it, push its
// children. The children go on in reverse, so the first child is popped next and the ids come
// out in the same order as KillProcessDfs, whose deepest chain would overflow the call stack.
//
// Time: O(n) -- the map is built in one pass; each process is pushed and popped once.
// Space: O(n) -- the map and the stack.
//
// Siblings: KillProcessDfs is the recursive version (and names this file for its stack limit);
// KillProcessBfs walks the same map with a queue, in a different order.

public final class KillProcessDfsIterative {

	public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < pid.size(); i++) {
			int parent = ppid.get(i);
			int child = pid.get(i);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(child); // MAP: parent id -> child ids
		}
		List<Integer> list = new ArrayList<>();
		Deque<Integer> stack = new ArrayDeque<>();
		stack.push(kill); // START: the process to kill
		while (!stack.isEmpty()) {
			int id = stack.pop();
			list.add(id); // RECORD: killed
			List<Integer> children = map.getOrDefault(id, new ArrayList<>());
			for (int i = children.size() - 1; i >= 0; i--) {
				stack.push(children.get(i)); // TAKE: its children die too; reversed, so the first is popped first
			}
		}
		return list;
	}

	public static void main(String[] args) {
		KillProcessDfsIterative kp = new KillProcessDfsIterative();
		List<Integer> pid = asList(1, 3, 10, 5);
		List<Integer> ppid = asList(3, 0, 5, 3);
		System.out.println(kp.killProcess(pid, ppid, 5)); // [5, 10]
		System.out.println(kp.killProcess(asList(1), asList(0), 1)); // [1]
	}
}

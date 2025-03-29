package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1345. Jump Game IV

public final class JumpGameVI {
	// Time Complexity: O(N + E)
	// Space Complexity: O(N)

	public static int minJumps(int[] a) {
		int n = a.length;
		Map<Integer, List<Integer>> map = indexes(a);
		Set<Integer> set = new HashSet<>();
		Deque<int[]> dq = new ArrayDeque<>();
		set.add(0);
		dq.offer(new int[] { 0, 0 });
		while (!dq.isEmpty()) {
			int[] node = dq.poll();
			int index = node[0];
			int count = node[1];
			if (index == n - 1) {
				return count;
			}
			count++;
			List<Integer> indexes = map.getOrDefault(a[index], new ArrayList<>());
			for (int i : indexes) {
				if (!set.contains(i)) {
					set.add(i);
					dq.offer(new int[] { i, count });
				}
			}
			map.remove(a[index]);
			if (index + 1 < n && !set.contains(index + 1)) {
				set.add(index + 1);
				dq.offer(new int[] { index + 1, count });
			}
			if (index - 1 >= 0 && !set.contains(index - 1)) {
				set.add(index - 1);
				dq.offer(new int[] { index - 1, count });
			}
		}
		return -1;
	}

	private static Map<Integer, List<Integer>> indexes(int[] a) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.computeIfAbsent(a[i], k -> new ArrayList<>()).add(i);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 100, -23, -23, 404, 100, 23, 23, 23, 3, 404 };
		System.out.println(minJumps(a1)); // 3

		int[] a2 = { 7 };
		System.out.println(minJumps(a2)); // 0

		int[] a3 = { 7, 6, 9, 6, 9, 6, 9, 7 };
		System.out.println(minJumps(a3)); // 1
	}
}

package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.svetanis.java.base.utils.Print;

// 1817. Finding the Users Active Minutes

public final class UsersActiveMinutes {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] uam(int[][] logs, int k) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (int[] log : logs) {
			map.computeIfAbsent(log[0], u -> new HashSet<>()).add(log[1]);
		}
		int[] result = new int[k];
		for (Set<Integer> set : map.values()) {
			result[set.size() - 1]++;
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] logs1 = { { 0, 5 }, { 1, 2 }, { 0, 2 }, { 0, 5 }, { 1, 3 } };
		Print.print(uam(logs1, 5)); // 0 2 0 0 0

		int[][] logs2 = { { 1, 1 }, { 2, 2 }, { 2, 3 } };
		Print.print(uam(logs2, 4)); // 1 1 0 0
	}
}
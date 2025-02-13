package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 594. Longest Harmonious Subsequence

public final class LongestHarmoniousSubseq {
	// Time Complexity: O(n)

	public static int lhs(int[] a) {
		int max = 0;
		int count = 0;
		Map<Integer, Integer> map = frequencies(a);
		for (int curr : a) {
			int next = curr + 1;
			if (map.containsKey(next)) {
				count = map.get(curr) + map.get(next);
				max = Math.max(max, count);
			}
		}
		return max;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int element : a) {
			map.put(element, map.getOrDefault(element, 0) + 1);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 2, 2, 5, 2, 3, 7 };
		System.out.println(lhs(a1)); // 5
		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(lhs(a2)); // 2
		int[] a3 = { 1, 1, 1, 1 };
		System.out.println(lhs(a3)); // 0
		int[] a4 = { 1, 2, 2, 3, 4, 5, 1, 1, 1, 1 };
		System.out.println(lhs(a4)); // 7
	}
}

package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 594. Longest Harmonious Subsequence
//
// lhs takes an int array and returns the length of its longest harmonious subsequence: one
// whose largest and smallest values differ by exactly 1; 0 if there is none. On LeetCode:
// 1 <= nums.length <= 2 * 10^4, -10^9 <= nums[i] <= 10^9.
//
// A subsequence may skip any elements, and the order of the ones it keeps does not change
// max - min, so only the counts matter. A harmonious one holds two values v and v + 1 and nothing else, and the
// longest such takes every copy of both: count(v) + count(v + 1). Try every v that has a
// v + 1 present. An array of one repeated value has no v + 1, so the answer is 0, not its
// length: max - min must be exactly 1, not at most 1.

public final class LongestHarmoniousSubseq {
	// Time complexity: O(n), one pass to count, one pass over the array with map lookups
	// Auxiliary Space: O(n) for the counts

	public static int lhs(int[] a) {
		int max = 0;
		int count = 0;
		Map<Integer, Integer> map = frequencies(a);
		for (int curr : a) {
			int next = curr + 1;
			if (map.containsKey(next)) { // PAIR: v and v + 1 both present
				count = map.get(curr) + map.get(next); // TAKE every copy of both
				max = Math.max(max, count);
			}
		}
		return max;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int element : a) {
			map.put(element, map.getOrDefault(element, 0) + 1); // COUNT
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

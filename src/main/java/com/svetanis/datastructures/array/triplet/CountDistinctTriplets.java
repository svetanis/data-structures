package com.svetanis.datastructures.array.triplet;

import java.util.HashMap;
import java.util.Map;

// 2475. Number of Unequal Triplets in Array

public final class CountDistinctTriplets {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int unequalTriplets(int[] nums) {
		int n = nums.length;
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : nums) {
			map.merge(num, 1, Integer::sum);
		}
		int prev = 0;
		int result = 0;
		for (int freq : map.values()) {
			int next = n - prev - freq;
			result += next * freq * prev;
			prev += freq;
		}
		return result;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 4, 2, 4, 3 };
		System.out.println(unequalTriplets(a1)); // 3

		int[] a2 = { 1, 1, 1, 1, 1 };
		System.out.println(unequalTriplets(a2)); // 0
	}
}

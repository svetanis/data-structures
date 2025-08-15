package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1814. Count Nice Pairs in an Array

// nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
// nums[i] - rev(nums[i]) == nums[j] - rev(nums[j])

public final class NicePairsInArray {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private static final int MOD = (int) 1e9 + 7;

	public static int cnp(int[] a) {
		long count = 0;
		Map<Integer, Integer> map = frequencies(a);
		for (int k : map.values()) {
			count = (count + (long) k * (k - 1) / 2) % MOD;
		}
		return (int) count;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			int rev = reverse(num);
			map.merge(num - rev, 1, Integer::sum);
		}
		return map;
	}

	private static int reverse(int n) {
		int rev = 0;
		while (n > 0) {
			rev = rev * 10 + n % 10;
			n /= 10;
		}
		return rev;
	}

	public static void main(String[] args) {
		int[] a1 = { 42, 11, 1, 97 };
		System.out.println(cnp(a1)); // 2

		int[] a2 = { 13, 10, 35, 24, 76 };
		System.out.println(cnp(a2)); // 4
	}
}
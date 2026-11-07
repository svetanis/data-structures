package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1814. Count Nice Pairs in an Array
//
// cnp(nums) takes non-negative ints and returns how many index pairs i < j are nice:
// nums[i] + rev(nums[j]) == nums[j] + rev(nums[i]), where rev reverses the digits
// (rev(120) = 21). The count is returned modulo 10^9 + 7. On LeetCode:
// 1 <= nums.length <= 10^5, 0 <= nums[i] <= 10^9.
//
// Move each number's two terms to one side and the test becomes
// nums[i] - rev(nums[i]) == nums[j] - rev(nums[j]). So every number reduces to one key,
// x - rev(x), and a pair is nice exactly when both numbers have the same key. A key shared
// by k numbers gives k * (k - 1) / 2 pairs, and the answer is the sum over all keys. The
// product is taken in long: k can be 10^5, and k * (k - 1) does not fit in an int.
//
// Siblings, same problem:
//   NicePairsInArraySimple  the same keys, written over the input array and sorted, then
//                           counted as runs of equal keys: no map, but O(n log n)

public final class NicePairsInArray {
	// Time complexity: O(n * d), d <= 10 digits: one reverse and one map update per number
	// Auxiliary Space: O(n), one map entry per distinct key

	private static final int MOD = (int) 1e9 + 7;

	public static int cnp(int[] a) {
		long count = 0;
		Map<Integer, Integer> map = frequencies(a);
		for (int k : map.values()) {
			count = (count + (long) k * (k - 1) / 2) % MOD; // PAIRS: any two of the k, in long
		}
		return (int) count;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			int rev = reverse(num);
			map.merge(num - rev, 1, Integer::sum); // KEY: num - rev(num), and COUNT it
		}
		return map;
	}

	private static int reverse(int n) {
		int rev = 0;
		while (n > 0) {
			rev = rev * 10 + n % 10; // MOVE: n's last digit onto the end of rev
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
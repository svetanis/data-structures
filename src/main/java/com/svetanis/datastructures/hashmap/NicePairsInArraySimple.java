package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1814. Count Nice Pairs in an Array
//
// cnp(nums) takes non-negative ints and returns how many index pairs i < j are nice:
// nums[i] + rev(nums[j]) == nums[j] + rev(nums[i]), where rev reverses the digits
// (rev(120) = 21). The count is returned modulo 10^9 + 7. On LeetCode:
// 1 <= nums.length <= 10^5, 0 <= nums[i] <= 10^9. This version overwrites nums.
//
// The test rearranges to nums[i] - rev(nums[i]) == nums[j] - rev(nums[j]), so each number
// reduces to one key, x - rev(x), and nice pairs are pairs of equal keys. Here each key is
// written over its number and the array is sorted, so equal keys sit next to each other: a
// run of k equal keys gives k * (k - 1) / 2 pairs, counted in long.
//
// Siblings, same problem:
//   NicePairsInArray  the same keys counted in a map: O(n), and the input is left alone

public final class NicePairsInArraySimple {
	// Time complexity: O(n log n), the sort; the key pass and the run pass are O(n) each
	// Auxiliary Space: O(log n), the sort's stack; the keys reuse the input array

	private static final int MOD = (int) 1e9 + 7;

	public static int cnp(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			a[i] = a[i] - reverse(a[i]); // KEY: written over the number
		}
		Arrays.sort(a); // SORT: equal keys become one run
		long count = 0;
		for (int i = 0; i < n - 1; i++) {
			long k = 1;
			while (i < n - 1 && a[i] == a[i + 1]) { // RUN: walk to the run's last key
				k++;
				i++;
			}
			count = (count % MOD + k * (k - 1) / 2) % MOD; // PAIRS: any two in the run

		}
		return (int) (count % MOD);
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
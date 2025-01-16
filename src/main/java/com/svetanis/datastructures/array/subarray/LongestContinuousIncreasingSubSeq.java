package com.svetanis.datastructures.array.subarray;

// 674. Longest Continuous Increasing Subsequence

public final class LongestContinuousIncreasingSubSeq {
	// Time Complexity: O(n)

	public static int lcis(int[] a) {
		int n = a.length;
		int len = 1;
		int max = len;
		for (int i = 1; i < n; i++) {
			if (a[i] > a[i - 1]) {
				len++;
			} else {
				len = 1;
			}
			max = Math.max(max, len);
		}
		return max;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 5, 4, 7 };
		System.out.println(lcis(a1)); // 3

		int[] a2 = { 2, 2, 2, 2, 2 };
		System.out.println(lcis(a2)); // 1

		int[] a3 = { 1 };
		System.out.println(lcis(a3)); // 1
	}
}

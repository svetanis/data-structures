package com.svetanis.datastructures.array.subarray;

// 674. Longest Continuous Increasing Subsequence
//
// Input: an unsorted integer array a. Returns the length of the longest strictly increasing run of
// neighbouring elements (a contiguous subarray). On LeetCode: 1 <= a.length <= 10^4, -10^9 <= a[i]
// <= 10^9.
//
// The one idea: a run can only end where a[i] <= a[i - 1]. So keep len, the length of the
// increasing run that ends at the current element: it grows by one when the element is larger
// than the one before it, and restarts at 1 when it is not. The answer is the largest len seen.
// An array of one element is a run of length 1.
//
// Time: O(n) -- one pass.
// Space: O(1) -- two integers.

public final class LongestContinuousIncreasingSubSeq {

	public static int lcis(int[] a) {
		int n = a.length;
		int len = 1; // RUN: length of the increasing run ending at i
		int max = len;
		for (int i = 1; i < n; i++) {
			if (a[i] > a[i - 1]) {
				len++; // EXTEND: still strictly increasing
			} else {
				len = 1; // RESTART: a[i] starts a new run
			}
			max = Math.max(max, len); // KEEP: the longest run so far
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

package com.svetanis.datastructures.array;

// 2016. Maximum Difference Between Increasing Elements
//
// Input: a -- an array of ints. Returns the largest a[j] - a[i] over all pairs i < j with
// a[i] < a[j], or -1 if there is no such pair. On LeetCode: n == a.length, 2 <= n <= 1000,
// 1 <= a[i] <= 10^9.
//
// The one idea: for each element as the right end j, the best left end is the smallest value
// before it, so keep the smallest value seen so far. An element above that minimum makes a valid
// pair, whose difference is a candidate; an element not above it is a new (or equal) minimum.
// -1 means no pair has been found yet.
//
// Time: O(n) -- one pass.
// Space: O(1) -- a minimum and the best difference.

public final class MaxDiffBetweenIncreasingElements {

	public static int maxDiff(int[] a) {
		int maxDiff = -1; // BEST: -1 until some pair is found
		int min = Integer.MAX_VALUE; // MIN: the smallest value before the current one
		for (int num : a) {
			if (num > min) {
				maxDiff = Math.max(maxDiff, num - min); // RECORD: num as the right end, min as the left
			} else {
				min = num; // MIN moves down (or stays level)
			}
		}
		return maxDiff;
	}

	public static void main(String[] args) {
		int[] a1 = { 7, 1, 5, 4 };
		System.out.println(maxDiff(a1)); // 4

		int[] a2 = { 9, 4, 3, 2 };
		System.out.println(maxDiff(a2)); // -1

		int[] a3 = { 1, 5, 2, 10 };
		System.out.println(maxDiff(a3)); // 9
	}
}

package com.svetanis.datastructures.array;

// 908. Smallest Range I
//
// Input: an integer array a and an integer k. Each number may be changed once, to a[i] + x for any
// x in [-k, k]. Returns the smallest possible value of (largest number - smallest number) after
// the changes. On LeetCode: 1 <= a.length <= 10^4, 0 <= a[i] <= 10^4, 0 <= k <= 10^4.
//
// The one idea: the gap between the largest and smallest number is what we want small, and every
// number may move by up to k either way. Raise the smallest by k and lower the largest by k and
// the gap shrinks by 2k; every number in between can be moved to land inside, so they never get
// in the way. If the gap is already at most 2k the two meet and the answer is 0; otherwise it is
// the gap minus 2k.
//
// Time: O(n) -- one pass for the minimum and maximum.
// Space: O(1) -- two integers.
//
// Siblings: SmallestRangeII is the harder version where each number moves by exactly +k or -k.

public final class SmallestRange {

	public static int smallestRange(int[] a, int k) {
		int max = a[0];
		int min = a[0];
		for (int val : a) {
			max = Math.max(max, val); // TRACK: the largest number
			min = Math.min(min, val); // TRACK: the smallest number
		}
		int diff = max - min;
		return diff <= 2 * k ? 0 : diff - 2 * k; // RETURN: the gap, less the 2k the ends can close
	}

	public static void main(String[] args) {
		int[] a1 = { 1 };
		System.out.println(smallestRange(a1, 0)); // 0

		int[] a2 = { 0, 10 };
		System.out.println(smallestRange(a2, 2)); // 6

		int[] a3 = { 1, 3, 6 };
		System.out.println(smallestRange(a3, 3)); // 0
	}
}

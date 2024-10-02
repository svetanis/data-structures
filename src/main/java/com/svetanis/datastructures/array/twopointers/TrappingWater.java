package com.svetanis.datastructures.array.twopointers;

import static java.lang.Math.max;
import static java.lang.Math.min;

// Given n non-negative integers representing an elevation map 
// where the width of each bar is 1, 
// compute how much water it is able to trap after raining.

public final class TrappingWater {

	public static int trapWater(int[] a) {
		// Time Complexity: O(n)

		int n = a.length;
		int total = 0;

		int[] left = onLeft(a);
		int[] right = onRight(a);
		for (int i = 0; i < n; i++) {
			total += min(left[i], right[i]) - a[i];
		}
		return total;
	}

	// left[i] contains height of tallest bar
	// to the left of i'th bar including itself
	private static int[] onLeft(int[] a) {
		int n = a.length;
		int[] left = new int[n];
		left[0] = a[0];
		for (int i = 1; i < n; i++) {
			left[i] = max(left[i - 1], a[i]);
		}
		return left;
	}

	// right[i] contains height of tallest bar
	// to the right of i-th bar including itself
	private static int[] onRight(int[] a) {
		int n = a.length;
		int[] right = new int[n];
		right[n - 1] = a[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			right[i] = max(right[i + 1], a[i]);
		}
		return right;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 0, 2 };
		System.out.println(trapWater(a1));

		int[] a2 = { 3, 0, 0, 2, 0, 4 };
		System.out.println(trapWater(a2));

		int[] a3 = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		System.out.println(trapWater(a3));

		int[] a4 = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
		System.out.println(trapWater(a4));

	}
}
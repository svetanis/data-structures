package com.svetanis.datastructures.array.pairs;

import java.util.Arrays;

// 1913. Maximum Product Difference Between Two Pairs

public final class MaxProductDifferenceSort {
	// Time Complexity: O(n log n)

	public static int maxDiff(int[] a) {
		Arrays.sort(a);
		int n = a.length;
		int max = a[n - 1] * a[n - 2];
		int min = a[0] * a[1];
		return max - min;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 6, 2, 7, 4 };
		System.out.println(maxDiff(a1)); // 34
		int[] a2 = { 4, 2, 5, 9, 7, 4, 8 };
		System.out.println(maxDiff(a2)); // 64
	}
}
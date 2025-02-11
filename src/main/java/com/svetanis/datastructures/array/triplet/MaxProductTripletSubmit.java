package com.svetanis.datastructures.array.triplet;

import java.util.Arrays;

// 628. Maximum Product of Three Numbers

public final class MaxProductTripletSubmit {
	// Time Complexity: O(n)

	public static int maxProduct(int[] a) {
		final int infinity = Integer.MAX_VALUE;
		int min1 = infinity;
		int min2 = infinity;
		int max1 = -infinity;
		int max2 = -infinity;
		int max3 = -infinity;
		for (int num : a) {
			if (num <= min1) {
				min2 = min1;
				min1 = num;
			} else if (num <= min2) {
				min2 = num;
			}
			if (num >= max1) {
				max3 = max2;
				max2 = max1;
				max1 = num;
			} else if (num >= max2) {
				max3 = max2;
				max2 = num;
			} else if (num >= max3) {
				max3 = num;
			}
		}
		int prod1 = min1 * min2 * max1;
		int prod2 = max1 * max2 * max3;
		return Math.max(prod1, prod2);
	}

	public static int maxProduct2(int[] a) {
		Arrays.sort(a);
		int n = a.length;
		int prod1 = a[n - 1] * a[n - 2] * a[n - 3];
		int prod2 = a[0] * a[1] * a[n - 1];
		return Math.max(prod1, prod2);
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3 };
		System.out.println(maxProduct(a1)); // 6
		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(maxProduct(a2)); // 24
		int[] a3 = { -1, -2, -3 };
		System.out.println(maxProduct(a3)); // -6
		int[] a4 = { -100, -98, -1, 2, 3, 4};
		System.out.println(maxProduct(a4)); // 39200
	}
}
package com.svetanis.datastructures.array;

// 1464. Maximum Product of Two Elements in an Array

public final class MaxProduct {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int maxProduct(int[] a) {
		int first = Integer.MIN_VALUE;
		int second = Integer.MIN_VALUE;
		for (int num : a) {
			if (num > first) {
				second = first;
				first = num;
			} else if (num > second) {
				second = num;
			}
		}
		return (first - 1) * (second - 1);
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 4, 5, 2 };
		System.out.println(maxProduct(a1)); // 12

		int[] a2 = { 1, 5, 4, 5 };
		System.out.println(maxProduct(a2)); // 16

		int[] a3 = { 3, 7 };
		System.out.println(maxProduct(a3)); // 12
	}
}

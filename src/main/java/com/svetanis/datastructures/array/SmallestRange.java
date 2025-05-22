package com.svetanis.datastructures.array;

// 908. Smallest Range I

public final class SmallestRange {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int smallestRange(int[] a, int k) {
		int max = a[0];
		int min = a[0];
		for (int val : a) {
			max = Math.max(max, val);
			min = Math.min(min, val);
		}
		int diff = max - min;
		return diff <= 2 * k ? 0 : diff - 2 * k;
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

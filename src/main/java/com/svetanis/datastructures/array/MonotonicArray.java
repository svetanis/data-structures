package com.svetanis.datastructures.array;

// 896. Monotonic Array

public final class MonotonicArray {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static boolean isMonotonic(int[] a) {
		boolean asc = false;
		boolean dsc = false;
		for (int i = 1; i < a.length; i++) {
			if (a[i - 1] < a[i]) {
				asc = true;
			} else if (a[i - 1] > a[i]) {
				dsc = true;
			}
			if (asc && dsc) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 2, 3 };
		System.out.println(isMonotonic(a1)); // true

		int[] a2 = { 6, 5, 4, 4 };
		System.out.println(isMonotonic(a2)); // true

		int[] a3 = { 1, 3, 2 };
		System.out.println(isMonotonic(a3)); // false
	}
}

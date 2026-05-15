package com.svetanis.datastructures.array;

// 941. Valid Mountain Array

public final class ValidMountainArray {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static boolean validMountainArray(int[] a) {
		int n = a.length;
		if (n < 3) {
			return false;
		}
		int index = 0;
		while (index + 1 < n && a[index] < a[index + 1]) {
			index += 1;
		}
		if (index == 0 || index == n - 1) {
			return false;
		}
		while (index + 1 < n && a[index] > a[index + 1]) {
			index += 1;
		}
		return index == n - 1;
	}

	public static void main(String[] args) {
		int[] a = { 2, 1 };
		System.out.println(validMountainArray(a)); // false

		int[] a1 = { 3, 5, 5 };
		System.out.println(validMountainArray(a1)); // false

		int[] a2 = { 0, 3, 2, 1 };
		System.out.println(validMountainArray(a2)); // true

		int[] a3 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(validMountainArray(a3)); // false

		int[] a4 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		System.out.println(validMountainArray(a4)); // false

	}
}
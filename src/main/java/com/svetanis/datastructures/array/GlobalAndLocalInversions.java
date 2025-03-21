package com.svetanis.datastructures.array;

// 775. Global and Local Inversions

public final class GlobalAndLocalInversions {
	// Time Complexity: O(n)

	public static boolean isIdealPermutation(int[] a) {
		if (a.length < 2) {
			return true;
		}
		int max = 0;
		for (int i = 2; i < a.length; i++) {
			max = Math.max(max, a[i - 2]);
			if (max > a[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 2 };
		System.out.println(isIdealPermutation(a1)); // true

		int[] a2 = { 1, 2, 0 };
		System.out.println(isIdealPermutation(a2)); // false
	}
}

package com.svetanis.datastructures.array;

import java.util.Arrays;

// 1502. Can Make Arithmetic Progression From Sequence

public final class CanMakeArithmeticProgression {
	// Time Complexity: O(n log n)

	public static boolean canMakeArithmeticProgression(int[] a) {
		Arrays.sort(a);
		int diff = Math.abs(a[1] - a[0]);
		for (int i = 2; i < a.length; i++) {
			if (a[i] - a[i - 1] != diff) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 5, 1 };
		System.out.println(canMakeArithmeticProgression(a1)); // true

		int[] a2 = { 1, 2, 4 };
		System.out.println(canMakeArithmeticProgression(a2)); // false
	}
}

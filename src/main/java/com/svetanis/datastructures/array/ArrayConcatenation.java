package com.svetanis.datastructures.array;

import com.svetanis.java.base.utils.Print;

// 1929. Concatenation of Array

public final class ArrayConcatenation {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] concatenation(int[] a) {
		int n = a.length;
		int[] concat = new int[2 * n];
		for (int i = 0; i < n; i++) {
			int curr = a[i];
			concat[i] = curr;
			concat[i + n] = curr;
		}
		return concat;
	}

	public static int[] concatenation2(int[] a) {
		int n = a.length;
		int[] concat = new int[2 * n];
		for (int i = 0; i < 2 * n; i++) {
			concat[i] = a[i % n];
		}
		return concat;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 1 };
		Print.print(concatenation2(a1)); // 1,2,1,1,2,1

		int[] a2 = { 1, 3, 2, 1 };
		Print.print(concatenation2(a2)); // 1,3,2,1,1,3,2,1
	}
}

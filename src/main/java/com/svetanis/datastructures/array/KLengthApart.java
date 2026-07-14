package com.svetanis.datastructures.array;

// 1437. Check If All 1's Are at Least Length K Places Away

public final class KLengthApart {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static boolean kLenApart(int[] a, int k) {
		int dist = 0;
		boolean first = true;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				dist += 1;
			} else {
				if (!first && dist < k) {
					return false;
				}
				dist = 0;
				first = false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 0, 0, 1, 0, 0, 1 };
		System.out.println(kLenApart(a1, 2)); // true

		int[] a2 = { 1, 0, 0, 1, 0, 1 };
		System.out.println(kLenApart(a2, 2)); // false

		int[] a3 = { 0, 1, 0, 0, 1, 0, 0, 1 };
		System.out.println(kLenApart(a3, 2)); // true

	}
}

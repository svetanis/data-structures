package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static org.apache.commons.lang3.ArrayUtils.toObject;

// given an array of integers; push
// all zeros to the end of the array

public final class MoveZerosToEnd {
	// Time Complexity: O(n)
	// Auxiliary Space: O(1)

	public static void segregate(int[] a) {
		int n = a.length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] != 0) {
				a[count++] = a[i];
			}
		}
		while (count < n) {
			a[count++] = 0;
		}
	}

	public static void main(String[] args) {
		int[] a = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0, 9 };
		segregate(a);
		print(toObject(a));
	}
}
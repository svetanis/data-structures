package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static org.apache.commons.lang3.ArrayUtils.toObject;

// 283. Move Zeros
// same as com.svetanis.algorithms.twopointers.MoveZeros

// given an array of integers; push
// all zeros to the end of the array

public final class MoveZerosToEnd {
	// Time Complexity: O(n)
	// Auxiliary Space: O(1)

	public static void segregate(int[] a) {
		int n = a.length;
		int slow = 0;
		for (int fast = 0; fast < n; fast++) {
			if (a[fast] != 0) {
				a[slow++] = a[fast];
			}
		}
		while (slow < n) {
			a[slow++] = 0;
		}
	}

	public static void moveZeros(int[] a) {
		int n = a.length;
		int slow = 0;
		for (int fast = 0; fast < n; fast++) {
			if (a[fast] != 0) {
				int temp = a[slow];
				a[slow] = a[fast];
				a[fast] = temp;
				slow++;
			}
		}
	}

	public static void main(String[] args) {
		int[] a = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0, 9 };
		segregate(a);
		print(toObject(a));
	}
}
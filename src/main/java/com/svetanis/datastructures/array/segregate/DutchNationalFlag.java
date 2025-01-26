package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;

// 75. Sort Colors

// given an unsorted array of 0s, 1s and 2s
// sort the array in-place.

public final class DutchNationalFlag {
	// Time Complexity: O(n)

	public static void dnf(int[] a) {
		int left = 0;
		int right = a.length - 1;
		for (int i = 0; i <= right;) {
			if (a[i] == 0) {
				swap(a, i, left);
				i++;
				left++;
			} else if (a[i] == 1) {
				i++;
			} else {
				swap(a, i, right);
				right--;
			}
		}
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		int[] a = { 0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1 };
		dnf(a);
		print(a);
	}
}
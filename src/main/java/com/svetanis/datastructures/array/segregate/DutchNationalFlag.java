package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

// given an unsorted array of 0s, 1s and 2s
// sort the array in-place.

public final class DutchNationalFlag {

	public static void dnf(int[] a) {
		// Time Complexity: O(n)

		int low = 0;
		int high = a.length - 1;

		for (int i = 0; i <= high;) {
			if (a[i] == 0) {
				swap(a, i, low);
				i++;
				low++;
			} else if (a[i] == 1) {
				i++;
			} else {
				swap(a, i, high);
				high--;
			}
		}
	}

	public static void main(String[] args) {
		int[] a = { 0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1 };
		dnf(a);
		print(a);
	}
}
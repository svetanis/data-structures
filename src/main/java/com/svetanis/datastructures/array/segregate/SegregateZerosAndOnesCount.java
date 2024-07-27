package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static org.apache.commons.lang3.ArrayUtils.toObject;

// given an unsorted binary array
// segregate 0s on left side and 
// 1s on right side of the array

public final class SegregateZerosAndOnesCount {

	public static void segregate(int[] a) {
		// Time Complexity: O(n)

		int count = countZeros(a);
		for (int i = 0; i < a.length; ++i) {
			if (i < count) {
				a[i] = 0;
			} else {
				a[i] = 1;
			}
		}
	}

	private static int countZeros(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; ++i) {
			if (a[i] == 0) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a = { 0, 1, 0, 1, 1, 1 };
		segregate(a);
		print(toObject(a));
	}
}
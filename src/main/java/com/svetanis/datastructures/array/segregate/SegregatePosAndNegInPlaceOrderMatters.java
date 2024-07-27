package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

// given unsorted array of both positive and negative numbers
// rearrange it such that all negative integers appear before 
// all the positive integers in place; order matters

public final class SegregatePosAndNegInPlaceOrderMatters {

	public static void segregate(int[] a) {
		// Time complexity: O(n)
		// Auxiliary Space: O(1)

		int count = countNegatives(a);

		// move negatives to front
		int i = 0;
		int j = i + 1;
		while (i < count) {
			if (a[i] < 0) {
				i++;
				j = i + 1;
			} else if (a[i] > 0 && j < a.length) {
				swap(a, i, j);
				j++;
			}
		}
	}

	private static int countNegatives(int[] a) {
		int count = 0;
		// count negatives
		for (int val : a) {
			if (val < 0) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a = { -12, 11, -13, -5, 6, -7, 5, -3, -6 };
		segregate(a);
		print(a);
	}
}

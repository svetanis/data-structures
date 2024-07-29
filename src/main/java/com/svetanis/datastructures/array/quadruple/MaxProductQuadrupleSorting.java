package com.svetanis.datastructures.array.quadruple;

import static java.lang.Math.max;
import static java.util.Arrays.sort;

// given an array of unsorted numbers
// find a max product of a quadruple

public final class MaxProductQuadrupleSorting {
	// Time complexity: O(n log n)

	public static int quadruple(int[] a) {
		int n = a.length;
		if (n < 4) {
			return -1;
		}
		sort(a);
		// x is a product of last four elements
		int x = a[0] * a[1] * a[2] * a[3];
		// y is a product of first four elements
		int y = a[n - 1] * a[n - 2] * a[n - 3] * a[n - 4];
		// z is the product of first two and last two elements
		int z = a[0] * a[1] * a[n - 1] * a[n - 2];
		return max(x, max(y, z));
	}

	public static void main(String[] args) {
		int[] a = { 10, 3, 5, 6, 20 };
		System.out.println(quadruple(a));

		int[] a1 = { -10, -3, -5, -6, -20 };
		System.out.println(quadruple(a1));

		int[] a2 = { 1, -4, 3, -6, 7, 0 };
		System.out.println(quadruple(a2));
	}
}

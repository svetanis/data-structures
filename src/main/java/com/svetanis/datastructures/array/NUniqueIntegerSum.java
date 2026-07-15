package com.svetanis.datastructures.array;

import com.svetanis.java.base.utils.Print;

// 1304. Find N Unique Integers Sum up to Zero

public final class NUniqueIntegerSum {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] sumZero(int n) {
		int[] a = new int[n];
		a[0] = -(n * (n - 1) / 2);
		for (int i = 1; i < n; i++) {
			a[i] = i;
		}
		return a;
	}

	public static void main(String[] args) {
		Print.print(sumZero(5));
		Print.print(sumZero(3));
		Print.print(sumZero(1));
	}
}

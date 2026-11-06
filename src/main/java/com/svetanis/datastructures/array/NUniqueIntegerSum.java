package com.svetanis.datastructures.array;

import java.util.Arrays;

// 1304. Find N Unique Integers Sum up to Zero
//
// Input: n. On LeetCode: 1 <= n <= 1000. Returns any array of n distinct integers whose sum is 0.
//
// The one idea: take the numbers 1, 2, ..., n - 1, which are already distinct, and add one more
// number that cancels their sum. Their sum is 1 + 2 + ... + (n - 1) = n * (n - 1) / 2, so the extra
// number is minus that. It is at most 0 and every other number is positive, so it differs from all
// of them (for n = 1 the array is just [0]).
//
// Time: O(n) -- one pass to fill the array.
// Space: O(n) -- the answer array.

public final class NUniqueIntegerSum {

	public static int[] sumZero(int n) {
		int[] a = new int[n];
		a[0] = -(n * (n - 1) / 2); // CANCEL: minus the sum of 1 .. n - 1
		for (int i = 1; i < n; i++) {
			a[i] = i; // TAKE: the distinct positives 1 .. n - 1
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(sumZero(5))); // [-10, 1, 2, 3, 4]
		System.out.println(Arrays.toString(sumZero(3))); // [-3, 1, 2]
		System.out.println(Arrays.toString(sumZero(1))); // [0]
	}
}

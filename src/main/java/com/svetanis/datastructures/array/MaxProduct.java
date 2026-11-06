package com.svetanis.datastructures.array;

// 1464. Maximum Product of Two Elements in an Array
//
// Input: a -- an array of positive ints. Returns the largest value of (a[i] - 1) * (a[j] - 1) over
// all pairs i != j. On LeetCode: 2 <= a.length <= 500, 1 <= a[i] <= 10^3.
//
// The one idea: every a[i] - 1 is non-negative and grows with a[i], so the product is largest when
// both factors are largest -- the two biggest values of the array (a repeated value counts twice,
// because it sits at two indexes). One pass keeps the biggest and the second biggest. The product is
// at most 999 * 999, which fits in an int.
//
// Time: O(n) -- one pass.
// Space: O(1) -- two values.

public final class MaxProduct {

	public static int maxProduct(int[] a) {
		int first = Integer.MIN_VALUE; // FIRST: the biggest value so far
		int second = Integer.MIN_VALUE; // SECOND: the biggest value other than that slot
		for (int num : a) {
			if (num > first) {
				second = first; // DEMOTE: the old biggest becomes the second
				first = num;
			} else if (num > second) {
				second = num;
			}
		}
		return (first - 1) * (second - 1); // RETURN: the product of the two biggest, each less 1
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 4, 5, 2 };
		System.out.println(maxProduct(a1)); // 12

		int[] a2 = { 1, 5, 4, 5 };
		System.out.println(maxProduct(a2)); // 16

		int[] a3 = { 3, 7 };
		System.out.println(maxProduct(a3)); // 12
	}
}

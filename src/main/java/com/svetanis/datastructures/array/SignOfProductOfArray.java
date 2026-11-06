package com.svetanis.datastructures.array;

// 1822. Sign of the Product of an Array
//
// Input: an integer array a. On LeetCode: 1 <= a.length <= 1000, -100 <= a[i] <= 100.
// Returns 1 if the product of all the numbers is positive, -1 if it is negative, 0 if it is zero.
//
// The one idea: only the sign matters, and multiplying all the numbers would overflow. The sign of
// a product is decided by two facts: any zero makes it zero, and otherwise each negative number
// flips it once. So walk the array keeping a sign (starting at 1), flip it on every negative, and
// stop with 0 at the first zero.
//
// Time: O(n) -- one pass.
// Space: O(1) -- one integer.

public final class SignOfProductOfArray {

	public static int arraySign(int[] a) {
		int sign = 1; // SIGN: of the product so far
		for (int num : a) {
			if (num == 0) { // ZERO: the product is zero whatever follows
				return 0;
			}
			if (num < 0) { // FLIP: a negative number changes the sign
				sign *= -1;
			}
		}
		return sign;
	}

	public static void main(String[] args) {
		int[] a1 = { -1, -2, -3, -4, 3, 2, 1 };
		System.out.println(arraySign(a1)); // 1

		int[] a2 = { 1, 5, 0, 2, -3 };
		System.out.println(arraySign(a2)); // 0

		int[] a3 = { -1, 1, -1, 1, -1 };
		System.out.println(arraySign(a3)); // -1

		int[] a4 = { 41, 65, 14, 80, 20, 10, 55, 58, 24, 56, 28, 86, 96, 10, 3, 84, 4, 41, 13, 32, 42, 43, 83, 78, 82, 70,
				15, -41 };
		System.out.println(arraySign(a4)); // -1

		int[] a5 = { 9, 72, 34, 29, -49, -22, -77, -17, -66, -75, -44, -30, -24 };
		System.out.println(arraySign(a5)); // -1
	}
}

package com.svetanis.datastructures.array;

// 1822. Sign of the Product of an Array

public final class SignOfProductOfArray {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int arraySign(int[] a) {
		int sign = 1;
		for (int num : a) {
			if (num == 0) {
				return 0;
			}
			if (num < 0) {
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

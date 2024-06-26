package com.svetanis.datastructures.array.pairs;

import static java.lang.Math.abs;

import com.svetanis.java.base.Pair;

// Given an array with both +ive and -ive integers, return a pair with highest product.
// to traverse the input array and keep track of following four values:
// a) Maximum positive value
// b) Second maximum positive value
// c) Maximum negative value i.e., a negative value with maximum absolute value
// d) Second maximum negative value.

// At the end of the loop, compare the products of first two and last two and print the maximum of two products.

public final class PairMaxProduct {

	private static int INT_MIN = 0;

	public static Pair<Integer, Integer> pair(int[] a) {
		// Time Complexity: O(n)

		int n = a.length;

		if (n < 2) {
			System.out.println("No such pair exists");
			return Pair.build(-1, -1);
		}

		if (n == 2) {
			return Pair.build(a[0], a[1]);
		}

		int firstMax = INT_MIN;
		int secondMax = INT_MIN;

		int firstMin = INT_MIN;
		int secondMin = INT_MIN;

		for (int i = 0; i < n; i++) {
			if (a[i] > firstMax) {
				secondMax = firstMax;
				firstMax = a[i];
			} else if (a[i] > secondMax) {
				secondMax = a[i];
			}

			if (a[i] < 0 && abs(a[i]) > abs(firstMin)) {
				secondMin = firstMin;
				firstMin = a[i];
			} else if (a[i] < 0 && abs(a[i]) > abs(secondMin)) {
				secondMin = a[i];
			}
		}

		if (firstMax * secondMax > firstMin * secondMin) {
			return Pair.build(firstMax, secondMax);
		} else {
			return Pair.build(firstMin, secondMin);
		}
	}

	public static void main(String[] args) {
		int[] X = { 1, 4, 3, 6, 7, 0 };
		System.out.println(pair(X));
		int[] Y = { -1, -3, -4, 2, 0, -5 };
		System.out.println(pair(Y));
	}
}
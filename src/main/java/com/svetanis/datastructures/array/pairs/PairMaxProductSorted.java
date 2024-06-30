package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// Given an array with both +ive and -ive integers, return a pair with highest product.
// 1. sort input array in increasing order
// 2. if all elements are positive, then return product of last two numbers
// 3. else return max of products of first two and last two numbers

public final class PairMaxProductSorted {

	public static Pair<Integer, Integer> pair(int[] a) {
		// Time Complexity: O(n log n)

		int n = a.length;

		if (n < 2) {
			System.out.println("No such pair exists");
			return Pair.build(-1, -1);
		}

		sort(a);

		int first = a[0];
		int second = a[1];

		if (n == 2) {
			return Pair.build(first, second);
		}

		int last = a[n - 1];
		int secondLast = a[n - 2];

		if (first > 0) {
			return Pair.build(last, secondLast);
		} else if (first * second > last * secondLast) {
			return Pair.build(first, second);
		} else {
			return Pair.build(last, secondLast);
		}
	}

	public static void main(String[] args) {
		int[] X = { 1, 4, 3, 6, 7, 0 };
		System.out.println(pair(X));
		int[] Y = { -1, -3, -4, 2, 0, -5 };
		System.out.println(pair(Y));
	}
}
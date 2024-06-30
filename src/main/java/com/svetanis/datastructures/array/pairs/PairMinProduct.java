package com.svetanis.datastructures.array.pairs;

import com.svetanis.java.base.Pair;

// given an array of positive integers
// find a minimum product pair

// linearly traverse given array and
// keep track of minimum two elements

public final class PairMinProduct {

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

		int first = a[0];
		int second = a[1];

		for (int i = 2; i < n; i++) {
			if (a[i] < first) {
				second = first;
				first = a[i];
			} else if (a[i] < second) {
				second = a[i];
			}
		}
		return Pair.build(first, second);
	}

	public static void main(String[] args) {
		int[] a = { 11, 8, 5, 7, 5, 100 };
		System.out.println(pair(a));
	}
}
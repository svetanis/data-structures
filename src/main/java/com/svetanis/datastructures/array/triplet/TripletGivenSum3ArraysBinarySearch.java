package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

import com.svetanis.java.base.utils.Triplet;

// given 3 integer arrays and a target, 
// find triplet such that a + b + c == target
// and a, b, c belong to three different arrays

public final class TripletGivenSum3ArraysBinarySearch {

	public static Triplet<Integer, Integer, Integer> triplet(int[] a1, int[] a2, int[] a3, int k) {
		// time complexity: O(n1 * n2 * log n3)

		sort(a3);
		int n1 = a1.length;
		int n2 = a2.length;

		for (int i = 0; i < n1; i++) {
			for (int j = 0; j < n2; j++) {
				int sum = a1[i] + a2[j];
				int diff = k - sum;
				if (binarySearch(a3, diff) != -1) {
					return Triplet.build(diff, a2[i], a3[j]);
				}
			}
		}
		return Triplet.build(-1, -1, -1);
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 4, 5 };
		int[] a2 = { 2, 3, 6, 1, 2 };
		int[] a3 = { 3, 2, 4, 5, 6 };

		System.out.println(triplet(a1, a2, a3, 9));
	}
}
package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

import com.svetanis.java.base.utils.Triplet;

// given three sorted arrays A[], B[] and C[], 
// find 3 elements i, j and k from A, B and C respectively 
// such that max(abs(A[i] – B[j]), abs(B[j] – C[k]), abs(C[k] – A[i])) is minimized. 

public final class Closest3Elements3Arrays {

	public static Triplet<Integer, Integer, Integer> triplet(int[] a, int[] b, int[] c) {
		// Time complexity: O(n + m + l)

		sort(a);
		sort(b);
		sort(c);

		int target = MAX_VALUE;

		int n = a.length;
		int m = b.length;
		int l = c.length;

		int first = 0;
		int second = 0;
		int third = 0;

		int i = 0;
		int j = 0;
		int k = 0;

		while (i < n && j < m && k < l) {
			int min = min(a[i], min(b[j], c[k]));
			int max = max(a[i], max(b[j], c[k]));
			int dif = max - min;
			// if current diff is less
			// than min diff so far
			if (dif < target) {
				target = dif;
				first = i;
				second = j;
				third = k;
			}

			if (target == 0) {
				break;
			}

			// increment index of array
			// with smallest value
			if (a[i] == min) {
				i++;
			} else if (b[j] == min) {
				j++;
			} else {
				k++;
			}
		}
		return Triplet.build(a[first], b[second], c[third]);
	}

	public static void main(String[] args) {
		int[] a = { 1, 4, 10 };
		int[] b = { 2, 15, 20 };
		int[] c = { 10, 12 };
		System.out.println(triplet(a, b, c));

		int[] a1 = { 5, 2, 8 };
		int[] a2 = { 10, 7, 12 };
		int[] a3 = { 9, 14, 6 };
		System.out.println(triplet(a1, a2, a3));

		int[] b1 = { 15, 12, 18, 9 };
		int[] b2 = { 10, 17, 13, 8 };
		int[] b3 = { 14, 16, 11, 5 };
		System.out.println(triplet(b1, b2, b3));
	}
}

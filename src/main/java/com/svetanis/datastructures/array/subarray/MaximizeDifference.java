package com.svetanis.datastructures.array.subarray;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.max;

// a non-empty array A consisting of N integers is given.
// any integer K, such taht 0<=K<N-1, splits array A into
// two non-empty parts:
// left part: a[0],a[1]...a[k] and
// right part: a[k+1],a[k+2], ... a[n-1]

// the difference between the two parts is the absolute difference
// of the max value in the left part and max val in the right part.

// find such division (integer k) that maximizes the difference between
// obtained parts.

// write a function that given an non-empty array A containing N integers
// returns the max difference between the two parts into which it can be split

// write an efficient algorithm for the following assumptions:
// n is an integer within the range [2... 100,000]
// each element of array A is an integer within the range
// [-1,000000000 .. 1,000000000]

public final class MaximizeDifference {

	public static int maxDiff(int[] a) {
		int maxDiff = MIN_VALUE;
		int[] leftMax = leftMax(a);
		int[] rightMax = rightMax(a);
		for (int i = 0; i < a.length - 1; i++) {
			int diff = abs(leftMax[i] - rightMax[i + 1]);
			maxDiff = max(maxDiff, diff);
		}
		return maxDiff;
	}

	private static int[] leftMax(int[] a) {
		int n = a.length;
		int max = a[0];
		int[] left = new int[n];
		left[0] = max;
		for (int i = 1; i < n; i++) {
			max = max(max, a[i]);
			left[i] = max;
		}
		return left;
	}

	private static int[] rightMax(int[] a) {
		int n = a.length;
		int max = a[n - 1];
		int[] right = new int[n];
		right[n - 1] = max;
		for (int i = n - 2; i >= 0; i--) {
			max = max(max, a[i]);
			right[i] = max;
		}
		return right;
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, -3 };
		System.out.println(maxDiff(a)); // 6 : k=1, 6=3-(-3)
		int[] a2 = { 4, 3, 2, 5, 1, 1 };
		System.out.println(maxDiff(a2)); // 4: k = 3, 4=5-1
	}
}
package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 697. Degree of an Array
//
// degreeOfArray(a) returns the length of the shortest contiguous subarray of a whose degree
// equals a's degree, where the degree is the largest number of copies of any one value. On
// LeetCode: 1 <= a.length <= 5 * 10^4, 0 <= a[i] < 5 * 10^4.
//
// A subarray reaches the full degree only by holding every copy of some value that has the
// most copies. The shortest one that holds every copy of value v runs from v's first index
// to its last. So one pass records, per value, its count and its first and last index, and
// the answer is the smallest last - first + 1 over the values whose count is the degree.
// The three arrays are indexed by value, so they are sized by the largest value + 1, not by
// the length: a value can be larger than a.length - 1, as in { 5 }.
//
// Siblings, same problem: none in this package.

public final class DegreeOfArray {
	// Time complexity: O(n + V), V = the largest value -- one pass over a, and the arrays of
	// V + 1 slots are filled once
	// Auxiliary Space: O(V) -- three arrays indexed by value

	public static int degreeOfArray(int[] a) {
		int n = a.length;
		int size = Arrays.stream(a).max().orElse(-1) + 1; // SIZE: by the largest value, not by n
		int degree = 0;
		int[] counts = new int[size]; // COUNT: copies of each value
		int[] left = new int[size]; // FIRST: index of each value's first copy
		int[] right = new int[size]; // LAST: index of each value's last copy
		Arrays.fill(left, -1);
		Arrays.fill(right, -1);
		for (int i = 0; i < n; i++) {
			int num = a[i];
			counts[num]++;
			degree = Math.max(degree, counts[num]); // DEGREE: the most copies so far
			if (left[num] == -1) {
				left[num] = i; // FIRST: only the first time num is met
			}
			right[num] = i; // LAST: overwritten every time
		}
		int min = Integer.MAX_VALUE;
		for (int num : a) {
			if (counts[num] == degree) { // TIED?: one of the most frequent values
				int len = right[num] - left[num] + 1; // SPAN: first copy to last, both included
				min = Math.min(min, len); // BEST
			}
		}
		return min;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 2, 3, 1 };
		System.out.println(degreeOfArray(a1)); // 2

		int[] a2 = { 1, 2, 2, 3, 1, 4, 2 };
		System.out.println(degreeOfArray(a2)); // 6
	}
}
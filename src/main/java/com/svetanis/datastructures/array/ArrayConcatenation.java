package com.svetanis.datastructures.array;

import java.util.Arrays;

// 1929. Concatenation of Array
//
// Input: a -- an array of n ints. Returns a new array of length 2n in which position i and position
// i + n both hold a[i], that is, a followed by a again. On LeetCode: n == a.length, 1 <= n <= 1000,
// 1 <= a[i] <= 1000.
//
// The one idea: one slot of the answer is a copy of one slot of a. Slot i of the answer reads
// a[i] for the left half, and slot i + n reads the same a[i] for the right half -- so one pass over
// a fills both halves. concatenation does exactly that. concatenation2 fills the answer slot by
// slot instead: slot i reads a[i % n], and the remainder wraps the right half back to the start.
//
// Time: O(n) -- 2n slots, one write each.
// Space: O(n) -- the answer of 2n ints; nothing else.
//
// Siblings: none for 1929. The two methods in this file are the same loop written two ways.

public final class ArrayConcatenation {

	public static int[] concatenation(int[] a) {
		int n = a.length;
		int[] concat = new int[2 * n];
		for (int i = 0; i < n; i++) {
			int curr = a[i];
			concat[i] = curr; // COPY: left half
			concat[i + n] = curr; // COPY: right half, same value
		}
		return concat;
	}

	public static int[] concatenation2(int[] a) {
		int n = a.length;
		int[] concat = new int[2 * n];
		for (int i = 0; i < 2 * n; i++) {
			concat[i] = a[i % n]; // COPY: i % n wraps the right half back to the start of a
		}
		return concat;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 1 };
		System.out.println(Arrays.toString(concatenation(a1))); // [1, 2, 1, 1, 2, 1]
		System.out.println(Arrays.toString(concatenation2(a1))); // [1, 2, 1, 1, 2, 1]

		int[] a2 = { 1, 3, 2, 1 };
		System.out.println(Arrays.toString(concatenation(a2))); // [1, 3, 2, 1, 1, 3, 2, 1]
		System.out.println(Arrays.toString(concatenation2(a2))); // [1, 3, 2, 1, 1, 3, 2, 1]
	}
}

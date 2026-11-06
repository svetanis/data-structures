package com.svetanis.datastructures.array;

import java.util.Arrays;

// 910. Smallest Range II
//
// Input: an integer array a and an integer k. Every number must change exactly once, either to
// a[i] + k or to a[i] - k. Returns the smallest possible value of (largest number - smallest
// number) after the changes. On LeetCode: 1 <= a.length <= 10^4, 0 <= a[i] <= 10^4, 0 <= k <= 10^4.
// Both methods sort the caller's array in place.
//
// The one idea: sort the array. The numbers that go up should be the small ones and the numbers
// that go down the big ones, because moving a big number up or a small number down only widens
// the gap. So the answer has a cut: a[0 .. i] all go up (+k) and a[i + 1 .. n - 1] all go down
// (-k). For a cut after i, the smallest result is the lesser of a[0] + k and a[i + 1] - k, and the
// largest is the greater of a[i] + k and a[n - 1] - k. Try every cut and keep the smallest gap;
// "nobody goes down" (a[n - 1] - a[0]) is the starting answer.
//
// Time: O(n log n) -- the sort; the cuts are one pass.
// Space: O(1) -- besides the sort's own stack; the array is sorted in place.
//
// Siblings: smallestRange and smallestRange2 are the same loop written with the cut before or at
// index i (i + 1 versus i). They agree on every input.

public final class SmallestRangeII {

	public static int smallestRange(int[] a, int k) {
		Arrays.sort(a); // SORT: ups are the small ones, downs the big ones
		int n = a.length;
		int score = a[n - 1] - a[0]; // START: nobody goes down
		for (int i = 0; i < n - 1; i++) { // CUT: a[0 .. i] go up, a[i + 1 ..] go down
			int min = Math.min(a[0] + k, a[i + 1] - k);
			int max = Math.max(a[i] + k, a[n - 1] - k);
			score = Math.min(score, max - min); // KEEP: the narrowest gap
		}
		return score;
	}

	public static int smallestRange2(int[] a, int k) {
		Arrays.sort(a);
		int n = a.length;
		int score = a[n - 1] - a[0];
		for (int i = 1; i < n; i++) { // CUT: a[0 .. i - 1] go up, a[i ..] go down
			int min = Math.min(a[0] + k, a[i] - k);
			int max = Math.max(a[i - 1] + k, a[n - 1] - k);
			score = Math.min(score, max - min);
		}
		return score;
	}

	public static void main(String[] args) {
		int[] a1 = { 1 };
		System.out.println(smallestRange(a1, 0)); // 0

		int[] a2 = { 0, 10 };
		System.out.println(smallestRange(a2, 2)); // 6

		int[] a3 = { 1, 3, 6 };
		System.out.println(smallestRange(a3, 3)); // 3
	}
}

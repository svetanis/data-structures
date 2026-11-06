package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 163. Missing Ranges
//
// Input: a sorted array a of distinct numbers, and two bounds lower and upper with every a[i] in
// [lower, upper]. Returns the shortest sorted list of ranges [from, to] that covers every number
// in [lower, upper] that is NOT in a, and nothing else. On LeetCode: -10^9 <= lower <= upper <=
// 10^9, 0 <= a.length <= 100.
//
// The one idea: a missing range lives in a gap between two neighbours of a. One gap is the numbers
// strictly between a[i - 1] and a[i], so it exists when a[i] - a[i - 1] > 1 and runs from
// a[i - 1] + 1 to a[i] - 1. The bounds work the same way if lower - 1 stands in as a number to the
// left of the array and upper + 1 as a number to the right: then the first and last gaps are
// ordinary gaps too. Because a is sorted and distinct, the gaps come out already in order and
// never touch each other.
//
// Time: O(n) -- one pass, one comparison per neighbouring pair.
// Space: O(n) -- the answer list, at most n + 1 ranges; nothing else is kept.
//
// Siblings: missingRangesUnified uses the two stand-in numbers and one loop. missingRanges handles
// the empty array, the first gap and the last gap as three separate cases around the loop. Same
// answers. Both are safe at the extreme bounds: lower - 1 and upper + 1 fit in an int.

public final class MissingRanges163 {

	public static List<List<Integer>> missingRangesUnified(int[] a, int lower, int upper) {
		int n = a.length;
		int prev = lower - 1; // STAND-IN: the number just left of the array
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			int curr = (i < n) ? a[i] : upper + 1; // STAND-IN: the number just right of the array
			if (curr - prev > 1) { // GAP: numbers strictly between prev and curr
				list.add(Arrays.asList(prev + 1, curr - 1)); // RECORD: the whole gap
			}
			prev = curr;
		}
		return list;
	}

	public static List<List<Integer>> missingRanges(int[] a, int lower, int upper) {
		int n = a.length;
		List<List<Integer>> list = new ArrayList<>();

		if (n == 0) { // EMPTY: everything is missing
			list.add(Arrays.asList(lower, upper));
			return list;
		}
		if (a[0] > lower) { // FIRST GAP: before the first number
			list.add(Arrays.asList(lower, a[0] - 1));
		}
		for (int i = 1; i < n; i++) {
			if (a[i] - a[i - 1] > 1) { // GAP: between two neighbours
				list.add(Arrays.asList(a[i - 1] + 1, a[i] - 1));
			}
		}
		if (a[n - 1] < upper) { // LAST GAP: after the last number
			list.add(Arrays.asList(a[n - 1] + 1, upper));
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a = { 2, 3, 7, 9 };
		System.out.println(missingRanges(a, 1, 10)); // [[1, 1], [4, 6], [8, 8], [10, 10]]
		System.out.println(missingRangesUnified(a, 1, 10)); // [[1, 1], [4, 6], [8, 8], [10, 10]]
		int[] a1 = { 0, 1, 3, 50, 75 };
		System.out.println(missingRanges(a1, 0, 99)); // [[2, 2], [4, 49], [51, 74], [76, 99]]
		int[] a2 = { -1 };
		System.out.println(missingRangesUnified(a2, -1, -1)); // []
	}
}
package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1200. Minimum Absolute Difference
//
// Input: a -- an array of distinct ints. Returns every pair [x, y] with x < y whose difference
// y - x equals the smallest difference between any two elements, in ascending order of x. On
// LeetCode: 2 <= a.length <= 10^5, -10^6 <= a[i] <= 10^6, all distinct. The method sorts a.
//
// The one idea: after sorting, the two elements with the smallest difference must be neighbours --
// if something sat between them it would be closer to each than they are to one another. So the
// smallest difference is the smallest gap between neighbours, found in one pass; a second pass
// collects every neighbouring pair with exactly that gap. Sorted order gives the answer ordered.
//
// Time: O(n log n) -- the sort; the two passes are O(n).
// Space: O(n) -- the answer list (up to n - 1 pairs), beyond the sort's own stack.
//
// Siblings: twopointers/pairs/PairsMinDiffSorted (in the algorithms repo) solves the same problem
// and returns the pairs as int[] arrays.

public final class MinAbsDiff {

	public static List<List<Integer>> minAbsDiff(int[] a) {
		Arrays.sort(a); // SORT: the closest pair is a neighbouring pair
		int n = a.length;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n - 1; i++) {
			min = Math.min(min, a[i + 1] - a[i]); // MIN: the smallest gap between neighbours
		}
		List<List<Integer>> lists = new ArrayList<>();
		for (int i = 0; i < n - 1; i++) {
			if (a[i + 1] - a[i] == min) {
				lists.add(Arrays.asList(a[i], a[i + 1])); // RECORD: a pair with the smallest gap
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 2, 1, 3 };
		System.out.println(minAbsDiff(a1)); // [[1, 2], [2, 3], [3, 4]]

		int[] a2 = { 1, 3, 6, 10, 15 };
		System.out.println(minAbsDiff(a2)); // [[1, 3]]

		int[] a3 = { 3, 8, -10, 23, 19, -4, -14, 27 };
		System.out.println(minAbsDiff(a3)); // [[-14, -10], [19, 23], [23, 27]]
	}
}

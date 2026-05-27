package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1200. Minimum Absolute Difference

public final class MinAbsDiff {
	// Time Complexity: O(n log n)
	// Space Complexity: O(n)

	public static List<List<Integer>> minAbsDiff(int[] a) {
		Arrays.sort(a);
		int n = a.length;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n - 1; i++) {
			min = Math.min(min, a[i + 1] - a[i]);
		}
		List<List<Integer>> lists = new ArrayList<>();
		for (int i = 0; i < n - 1; i++) {
			if (a[i + 1] - a[i] == min) {
				lists.add(Arrays.asList(a[i], a[i + 1]));
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 2, 1, 3 };
		System.out.println(minAbsDiff(a1));

		int[] a2 = { 1, 3, 6, 10, 15 };
		System.out.println(minAbsDiff(a2));

		int[] a3 = { 3, 8, -10, 23, 19, -4, -14, 27 };
		System.out.println(minAbsDiff(a3));
	}
}

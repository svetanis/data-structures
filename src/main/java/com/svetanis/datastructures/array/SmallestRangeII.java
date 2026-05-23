package com.svetanis.datastructures.array;

import java.util.Arrays;

// 910. Smallest Range II

public final class SmallestRangeII {
	// Time Complexity: O(n log n)
	// Space Complexity: O(1)

	public static int smallestRange(int[] a, int k) {
		Arrays.sort(a);
		int n = a.length;
		int score = a[n - 1] - a[0];
		for (int i = 0; i < n - 1; i++) {
			int min = Math.min(a[0] + k, a[i + 1] - k);
			int max = Math.max(a[i] + k, a[n - 1] - k);
			score = Math.min(score, max - min);
		}
		return score;
	}

	public static int smallestRange2(int[] a, int k) {
		Arrays.sort(a);
		int n = a.length;
		int score = a[n - 1] - a[0];
		for (int i = 1; i < n; i++) {
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

package com.svetanis.datastructures.array;

// 2016. Maximum Difference Between Increasing Elements

public final class MaxDiffBetweenIncreasingElements {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int maxDiff(int[] a) {
		int maxDiff = -1;
		int min = Integer.MAX_VALUE;
		for (int num : a) {
			if (num > min) {
				maxDiff = Math.max(maxDiff, num - min);
			} else {
				min = num;
			}
		}
		return maxDiff;
	}

	public static void main(String[] args) {
		int[] a1 = { 7, 1, 5, 4 };
		System.out.println(maxDiff(a1)); // 4

		int[] a2 = { 9, 4, 3, 2 };
		System.out.println(maxDiff(a2)); // -1

		int[] a3 = { 1, 5, 2, 10 };
		System.out.println(maxDiff(a3)); // 9
	}
}

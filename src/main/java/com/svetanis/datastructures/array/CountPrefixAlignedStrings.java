package com.svetanis.datastructures.array;

// 1375. Number of Times Binary String Is Prefix-Aligned

public final class CountPrefixAlignedStrings {
	// Time Complexity: O(n)

	public static int count(int[] flips) {
		int max = 0;
		int count = 0;
		for (int i = 0; i < flips.length; i++) {
			max = Math.max(max, flips[i]);
			if (max == i + 1) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 2, 4, 1, 5 };
		System.out.println(count(a1)); // 2

		int[] a2 = { 4, 1, 2, 3 };
		System.out.println(count(a2)); // 1
	}
}

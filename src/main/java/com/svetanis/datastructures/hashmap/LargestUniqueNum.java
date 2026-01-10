package com.svetanis.datastructures.hashmap;

// 1133. Largest Unique Number

public final class LargestUniqueNum {
	// Time Complexity: O(n)
	// Space Complexity: O(m)

	public static int largestUniqueNumber(int[] nums) {
		int[] counts = new int[1001];
		for (int num : nums) {
			counts[num]++;
		}

		for (int num = 1000; num >= 0; num--) {
			if (counts[num] == 1) {
				return num;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 7, 3, 9, 4, 9, 8, 3, 1 };
		System.out.println(largestUniqueNumber(a1)); // 8

		int[] a2 = { 9, 9, 8, 8 };
		System.out.println(largestUniqueNumber(a2)); // -1
	}
}
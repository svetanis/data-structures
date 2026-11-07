package com.svetanis.datastructures.hashmap;

// 1133. Largest Unique Number
//
// largestUniqueNumber takes an int array and returns the largest value that occurs in it
// exactly once, or -1 if every value repeats. On LeetCode: 1 <= nums.length <= 2000,
// 0 <= nums[i] <= 1000.
//
// The values are small, so an array of 1001 counters indexed by value replaces a map. After
// counting, walk the values from 1000 down: the first one counted once is the largest.

public final class LargestUniqueNum {
	// Time complexity: O(n + V), one pass to count, then at most V = 1001 values walked down
	// Auxiliary Space: O(V), one counter per possible value

	public static int largestUniqueNumber(int[] nums) {
		int[] counts = new int[1001]; // COUNTS: values run 0..1000
		for (int num : nums) {
			counts[num]++; // COUNT
		}

		for (int num = 1000; num >= 0; num--) {
			if (counts[num] == 1) {
				return num; // FIND: walking down, so the first unique value is the largest
			}
		}
		return -1; // NONE: every value repeats
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 7, 3, 9, 4, 9, 8, 3, 1 };
		System.out.println(largestUniqueNumber(a1)); // 8

		int[] a2 = { 9, 9, 8, 8 };
		System.out.println(largestUniqueNumber(a2)); // -1
	}
}
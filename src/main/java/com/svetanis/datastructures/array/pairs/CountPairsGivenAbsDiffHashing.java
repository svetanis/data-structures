package com.svetanis.datastructures.array.pairs;

// 2006. Count Number of Pairs With Absolute Difference K

public final class CountPairsGivenAbsDiffHashing {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int count(int[] a, int k) {
		int pairs = 0;
		int[] counts = new int[110];
		for (int num : a) {
			if (num + k <= 100) {
				pairs += counts[num + k];
			}
			if (num >= k) {
				pairs += counts[num - k];
			}
			counts[num]++;
		}
		return pairs;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 2, 1 };
		System.out.println(count(a1, 1)); // 4

		int[] a2 = { 1, 3 };
		System.out.println(count(a2, 3)); // 0

		int[] a3 = { 3, 2, 1, 5, 4 };
		System.out.println(count(a3, 2)); // 3
	}
}
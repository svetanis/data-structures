package com.svetanis.datastructures.array.subarray;

// 2348. Number of Zero-Filled Subarrays

public final class CountZeroFilledSubarrays {
	// Time Complexity: O(n)

	public static long zeroFilledSubarrays(int[] a) {
		int zeros = 0;
		int total = 0;
		for (int element : a) {
			zeros = (element != 0) ? 0 : zeros + 1;
			total += zeros;
		}
		return total;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 0, 0, 2, 0, 0, 4 };
		System.out.println(zeroFilledSubarrays(a1)); // 6
		int[] a2 = { 0, 0, 0, 2, 0, 0 };
		System.out.println(zeroFilledSubarrays(a2)); // 9
		int[] a3 = { 2, 10, 2019 };
		System.out.println(zeroFilledSubarrays(a3)); // 0
	}
}

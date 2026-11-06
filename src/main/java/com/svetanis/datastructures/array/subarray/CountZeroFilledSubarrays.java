package com.svetanis.datastructures.array.subarray;

// 2348. Number of Zero-Filled Subarrays
//
// Input: an integer array a. Returns the number of contiguous subarrays made only of zeros. On
// LeetCode: 1 <= a.length <= 10^5, -10^9 <= a[i] <= 10^9. The answer can reach 10^5 * (10^5 + 1) /
// 2, more than an int holds, so it is a long.
//
// The one idea: count the subarrays by where they END. Let zeros be the length of the run of
// zeros that ends at the current element (0 if the element is not zero). Each of the zeros
// subarrays that end here -- lengths 1 up to zeros -- is zero-filled, and no other subarray ending
// here is. So add zeros to the total at every element. A run of z zeros adds 1 + 2 + ... + z.
//
// Time: O(n) -- one pass.
// Space: O(1) -- two numbers.

public final class CountZeroFilledSubarrays {

	public static long zeroFilledSubarrays(int[] a) {
		int zeros = 0; // RUN: zeros ending at this element
		long total = 0; // TOTAL: long, since a run of 10^5 zeros gives 5 * 10^9
		for (int element : a) {
			zeros = (element != 0) ? 0 : zeros + 1; // EXTEND the run, or reset it
			total += zeros; // TAKE: one subarray per length 1 .. zeros ending here
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
		System.out.println(zeroFilledSubarrays(new int[100000])); // 5000050000
	}
}

package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1814. Count Nice Pairs in an Array

// nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
// nums[i] - rev(nums[i]) == nums[j] - rev(nums[j])

public final class NicePairsInArraySimple {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private static final int MOD = (int) 1e9 + 7;

	public static int cnp(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			a[i] = a[i] - reverse(a[i]);
		}
		Arrays.sort(a);
		long count = 0;
		for (int i = 0; i < n - 1; i++) {
			long k = 1;
			while (i < n - 1 && a[i] == a[i + 1]) {
				k++;
				i++;
			}
			count = (count % MOD + k * (k - 1) / 2) % MOD;

		}
		return (int) (count % MOD);
	}

	private static int reverse(int n) {
		int rev = 0;
		while (n > 0) {
			rev = rev * 10 + n % 10;
			n /= 10;
		}
		return rev;
	}

	public static void main(String[] args) {
		int[] a1 = { 42, 11, 1, 97 };
		System.out.println(cnp(a1)); // 2

		int[] a2 = { 13, 10, 35, 24, 76 };
		System.out.println(cnp(a2)); // 4
	}
}
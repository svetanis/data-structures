package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 219. Contains Duplicate II

// given an integer array and integer k
// return true if there are two distinct
// indices such that nums[i] == nums[j]
// and abs(i - j) <= k

public final class DuplicatesKDistAwaySW {
	// Time Complexity: O(n)

	public static boolean duplicates(int[] a, int k) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			if (i > k) {
				set.remove(a[i - k - 1]);
			}
			if (set.contains(a[i])) {
				return true;
			}
			set.add(a[i]);
		}
		return false;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 1 };
		int[] a2 = { 1, 0, 1, 1 };
		int[] a3 = { 1, 2, 3, 1, 2, 3 };
		System.out.println(duplicates(a1, 3));// true
		System.out.println(duplicates(a2, 1));// true
		System.out.println(duplicates(a3, 2)); // false
	}
}

package com.svetanis.datastructures.array.pairs;

import java.util.HashSet;
import java.util.Set;

// 532. K-diff Pairs in an Array

// given an array of distinct integers 
// and a positive integer k
// count all distinct pairs with difference = k

public final class CountPairsGivenDiffHashing {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int count(int[] a, int k) {
		Set<Integer> pairs = new HashSet<>();
		Set<Integer> visited = new HashSet<>();

		// for each element
		// look for a[i] + k in the hash set,
		// look for a[i] - k in the hash set,
		// add the smaller number to pairs
		for (int i = 0; i < a.length; i++) {
			int curr = a[i];
			if (visited.contains(curr - k)) {
				pairs.add(curr - k);
			}
			if (visited.contains(curr + k)) {
				pairs.add(curr);
			}
			// add curr to visited
			visited.add(curr);
		}
		return pairs.size();
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 5, 3, 4, 2 };
		System.out.println(count(a1, 3)); // 2

		int[] a2 = { 8, 12, 16, 4, 0, 20 };
		System.out.println(count(a2, 4)); // 5

		int[] a3 = { 3, 1, 4, 1, 5 };
		System.out.println(count(a3, 2)); // 2

		int[] a4 = { 1, 2, 3, 4, 5 };
		System.out.println(count(a4, 1)); // 4

		int[] a5 = { 1, 3, 1, 5, 4 };
		System.out.println(count(a5, 0)); // 1
	}
}
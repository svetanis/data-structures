package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1481. Least Number of Unique Integers after K Removals
//
// minimize takes an int array and a number k, and returns the fewest distinct values that
// can be left after removing exactly k elements. On LeetCode: 1 <= arr.length <= 10^5,
// 1 <= arr[i] <= 10^9, 0 <= k <= arr.length.
//
// A value stops counting only when every copy of it is removed; removing some copies
// changes nothing. So spend k on whole values, the ones with the fewest copies first: sort
// the counts and take them in order while k lasts. The first count that k cannot cover
// survives, and so does every value after it. Leftover removals that cover no whole value
// go on the survivors' extra copies and change nothing.

public final class MinimizeUniqueIntsKRemovals {
	// Time complexity: O(n log n), sorting the counts, up to n of them when all values differ
	// Auxiliary Space: O(n) for the counts map and the sorted list

	public static int minimize(int[] a, int k) {
		Map<Integer, Integer> map = frequencies(a);
		List<Integer> frequencies = new ArrayList<>(map.values());
		Collections.sort(frequencies); // SORT: fewest copies first, the cheapest to remove whole
		for (int i = 0, total = frequencies.size(); i < total; i++) {
			k -= frequencies.get(i); // REMOVE every copy of the i-th cheapest value
			if (k < 0) {
				return total - i; // SURVIVE: this value and every one after it
			}
		}
		return 0; // EMPTY: k covered every element
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			map.merge(num, 1, Integer::sum); // COUNT
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 5, 4 };
		System.out.println(minimize(a1, 1)); // 1
		int[] a2 = { 4, 3, 1, 1, 3, 3, 2 };
		System.out.println(minimize(a2, 3)); // 2
	}
}
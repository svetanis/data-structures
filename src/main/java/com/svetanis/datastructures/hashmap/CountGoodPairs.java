package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1512. Number of Good Pairs
//
// cgpSimple(a) and cgp(a) return how many index pairs i < j have a[i] == a[j]. On LeetCode:
// 1 <= a.length <= 100, 1 <= a[i] <= 100.
//
// Read left to right and let each element pair up with the equal values already read: an
// element whose value has been seen k times before makes k new pairs, each with itself as
// the j. So add the count seen so far, then count the element. Doing it in that order is what
// keeps an element from pairing with itself.
//
// cgpSimple keeps the counts in an array of 101 slots, which relies on 1 <= a[i] <= 100;
// cgp keeps them in a map and takes any int.
//
// Siblings, same problem: none in this package. The algorithms repo's
// twopointers/pairs/CountPairsEqualElements solves it too.

public final class CountGoodPairs {
	// Time complexity: O(n) -- one step per element
	// Auxiliary Space: cgpSimple O(1), a fixed 101-slot array; cgp O(d), d = distinct values

	public static int cgpSimple(int[] a) {
		int count = 0;
		int[] freq = new int[101]; // SEEN: copies of each value read so far; values 1..100
		for (int num : a) {
			count += freq[num]; // PAIR: with every earlier copy
			freq[num]++; // COUNT: after PAIR, so no element pairs with itself
		}
		return count;
	}

	public static int cgp(int[] a) {
		Map<Integer, Integer> map = new HashMap<>(); // SEEN: value -> copies read so far
		int count = 0;
		for (int num : a) {
			if (map.containsKey(num)) {
				count += map.get(num); // PAIR: with every earlier copy
			}
			map.put(num, map.getOrDefault(num, 0) + 1); // COUNT: after PAIR
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 1, 1, 3 };
		System.out.println(cgpSimple(a1)); // 4
		int[] a2 = { 1, 1, 1, 1 };
		System.out.println(cgpSimple(a2)); // 6
		int[] a3 = { 1, 2, 3 };
		System.out.println(cgpSimple(a3)); // 0
	}
}
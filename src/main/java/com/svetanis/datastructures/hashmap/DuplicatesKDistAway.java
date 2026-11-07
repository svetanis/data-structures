package com.svetanis.datastructures.hashmap;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 219. Contains Duplicate II
//
// duplicates(a, k) returns true if there are two different indices i and j with
// a[i] == a[j] and |i - j| <= k; the List overload does the same on a list. On LeetCode:
// 1 <= a.length <= 10^5, -10^9 <= a[i] <= 10^9, 0 <= k <= 10^5.
//
// The closest earlier copy of a value is its most recent one, so a map from value to the
// last index it was seen at is enough: at index i, if a[i]'s last index is within k, that
// pair answers yes; if not, no earlier copy can be closer, and i becomes the last index. A
// value not seen yet reads as index -1000000, which is never within k because k <= 10^5 --
// the method rests on that LeetCode limit: with k >= 1000000 a first sighting at index 0
// would count as a duplicate.
//
// Siblings, same problem:
//   DuplicatesKDistAwaySW  a sliding window: a set of only the last k values, the oldest
//                          dropped as each new one comes in; no default index needed

public final class DuplicatesKDistAway {
	// Time complexity: O(n), expected -- one map lookup and one write per element
	// Auxiliary Space: O(d), d = distinct values -- every value's last index stays in the map

	public static boolean duplicates(int[] a, int k) {
		Map<Integer, Integer> map = new HashMap<>(); // LAST: value -> the last index it was seen at
		for (int i = 0; i < a.length; i++) {
			int prev = map.getOrDefault(a[i], -1000000); // FIND: far enough back to fail for k <= 10^5
			if ((i - prev) <= k) { // CLOSE?: the nearest earlier copy is within k
				return true;
			}
			map.put(a[i], i); // MOVE: this copy is now the nearest for later ones
		}
		return false;
	}

	public static boolean duplicates(List<Integer> list, int k) {
		Map<Integer, Integer> map = new HashMap<>(); // LAST: value -> the last index it was seen at
		for (int i = 0; i < list.size(); i++) {
			int prev = map.getOrDefault(list.get(i), -1000000); // FIND
			if ((i - prev) <= k) { // CLOSE?
				return true;
			}
			map.put(list.get(i), i); // MOVE
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(duplicates(asList(1, 2, 3, 1), 3));// true
		System.out.println(duplicates(asList(1, 0, 1, 1), 1));// true
		System.out.println(duplicates(asList(1, 2, 3, 1, 2, 3), 2)); // false

		int[] a1 = { 1, 2, 3, 1 };
		int[] a2 = { 1, 0, 1, 1 };
		int[] a3 = { 1, 2, 3, 1, 2, 3 };
		System.out.println(duplicates(a1, 3));// true
		System.out.println(duplicates(a2, 1));// true
		System.out.println(duplicates(a3, 2)); // false
	}
}

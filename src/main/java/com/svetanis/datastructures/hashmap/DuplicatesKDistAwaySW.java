package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 219. Contains Duplicate II
//
// duplicates(a, k) returns true if there are two different indices i and j with
// a[i] == a[j] and |i - j| <= k. On LeetCode: 1 <= a.length <= 10^5, -10^9 <= a[i] <= 10^9,
// 0 <= k <= 10^5.
//
// A copy of a[i] close enough to count sits in one of the k positions just before i. So keep
// a set of exactly those values: before looking at a[i], drop a[i - k - 1], which has just
// moved out of reach. The window never holds two equal values -- the second would have been
// caught on arrival -- so removing a value by key never removes a copy still in reach. With
// k = 0 the window is always empty and the answer is false.
//
// Siblings, same problem:
//   DuplicatesKDistAway  a map of value -> last index seen, compared against k; it keeps
//                        every value, and its "never seen" default rests on k <= 10^5

public final class DuplicatesKDistAwaySW {
	// Time complexity: O(n), expected -- at most one add, one remove and one lookup per element
	// Auxiliary Space: O(min(n, k)) -- the window holds at most k values

	public static boolean duplicates(int[] a, int k) {
		Set<Integer> set = new HashSet<>(); // WINDOW: the values at the k positions before i
		for (int i = 0; i < a.length; i++) {
			if (i > k) {
				set.remove(a[i - k - 1]); // DROP: now k + 1 back, out of reach
			}
			if (set.contains(a[i])) { // FIND: a copy within k
				return true;
			}
			set.add(a[i]); // JOIN: in reach of the next k positions
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

package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 2215. Find the Difference of Two Arrays
//
// difference(a1, a2) returns two lists: the distinct values of a1 that are not in a2, and the
// distinct values of a2 that are not in a1. Each value appears once even if it repeats in its
// array; the order inside each list is free. On LeetCode: both lengths 1..1000,
// -1000 <= a1[i], a2[i] <= 1000.
//
// Turn each array into a set. That removes the repeats, and makes "is it in the other array"
// one lookup. Then walk each set and keep the values the other set lacks. Walking the sets,
// not the arrays, is what keeps a repeated value from being listed twice.
//
// Siblings, same problem: none in this package.

public final class DifferenceofTwoArrays2215 {
	// Time complexity: O(n + m), expected -- each array goes into a set once, each set is
	// walked once with one lookup per value
	// Auxiliary Space: O(n + m) -- the two sets

	public static List<List<Integer>> difference(int[] a1, int[] a2) {
		Set<Integer> set1 = toSet(a1); // DISTINCT: a1 without repeats
		Set<Integer> set2 = toSet(a2); // DISTINCT: a2 without repeats
		List<List<Integer>> lists = new ArrayList<>();
		List<Integer> unique1 = new ArrayList<>();
		List<Integer> unique2 = new ArrayList<>();
		for (int val : set1) { // WALK: the set, so a repeat is listed once
			if (!set2.contains(val)) { // MISSING?: in a1, not in a2
				unique1.add(val);
			}
		}
		for (int val : set2) {
			if (!set1.contains(val)) { // MISSING?: in a2, not in a1
				unique2.add(val);
			}
		}
		lists.add(unique1);
		lists.add(unique2);
		return lists;
	}

	private static Set<Integer> toSet(int[] a) {
		Set<Integer> set = new HashSet<>();
		for (int element : a) {
			set.add(element); // DROP: a repeat is not added twice
		}
		return set;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3 };
		int[] a2 = { 2, 4, 6 };
		System.out.println(difference(a1, a2)); // [[1, 3], [4, 6]]

		int[] a3 = { 1, 2, 3, 3 };
		int[] a4 = { 1, 1, 2, 2 };
		System.out.println(difference(a3, a4)); // [[3], []]
	}
}

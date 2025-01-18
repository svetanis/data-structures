package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.java.base.utils.Print;

// 2215. Find the Difference of Two Arrays

public final class DifferenceofTwoArrays2215 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> difference(int[] a1, int[] a2) {
		Set<Integer> set1 = toSet(a1);
		Set<Integer> set2 = toSet(a2);
		List<List<Integer>> lists = new ArrayList<>();
		List<Integer> unique1 = new ArrayList<>();
		List<Integer> unique2 = new ArrayList<>();
		for (int val : set1) {
			if (!set2.contains(val)) {
				unique1.add(val);
			}
		}
		for (int val : set2) {
			if (!set1.contains(val)) {
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
			set.add(element);
		}
		return set;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3 };
		int[] a2 = { 2, 4, 6 };
		Print.print(difference(a1, a2)); // [1,3],[4,6]

		int[] a3 = { 1, 2, 3, 3 };
		int[] a4 = { 1, 1, 2, 2 };
		Print.print(difference(a3, a4)); // [3],[]
	}
}

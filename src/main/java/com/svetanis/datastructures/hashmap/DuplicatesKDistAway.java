package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 219. Contains Duplicate II

// given an integer array and integer k
// return true if there are two distinct
// indices such that nums[i] == nums[j]
// and abs(i - j) <= k

public final class DuplicatesKDistAway {
	// Time Complexity: O(n)

	public static boolean duplicates(int[] a, int k) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			int prev = map.getOrDefault(a[i], -1000000);
			if ((i - prev) <= k) {
				return true;
			}
			map.put(a[i], i);
		}
		return false;
	}

	public static boolean duplicates(List<Integer> list, int k) {
		Map<Integer, Integer> map = newHashMap();
		for (int i = 0; i < list.size(); i++) {
			int prev = map.getOrDefault(list.get(i), -1000000);
			if ((i - prev) <= k) {
				return true;
			}
			map.put(list.get(i), i);
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

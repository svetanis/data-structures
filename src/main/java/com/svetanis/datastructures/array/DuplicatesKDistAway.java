package com.svetanis.datastructures.array;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

// given an integer array and integer k
// return true if there are two distinct
// indices such that nums[i] == nums[j]
// and abs(i - j) <= k

public final class DuplicatesKDistAway {
	// Time Complexity: O(n)

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
	}
}

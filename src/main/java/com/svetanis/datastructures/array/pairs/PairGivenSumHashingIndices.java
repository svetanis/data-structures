package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.java.base.Pair;
import com.svetanis.java.base.utils.Print;

// 1. Two Sum

// given an array of distinct integer,
// find indices of the two numbers 
// such that they add up to a specific target

public final class PairGivenSumHashingIndices {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] pair(int[] a, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			int diff = target - a[i];
			if (map.containsKey(diff)) {
				return new int[] { map.get(diff), i };
			}
			map.put(a[i], i);
		}
		throw new IllegalArgumentException("No such pair found");
	}

	public static Pair<Integer, Integer> pair(List<Integer> list, int target) {
		int n = list.size();
		Map<Integer, Integer> map = newHashMap();
		for (int i = 0; i < n; i++) {
			int diff = target - list.get(i);
			if (map.containsKey(diff)) {
				return Pair.build(map.get(diff), i);
			}
			map.put(list.get(i), i);
		}
		return Pair.build(-1, -1);
	}

	public static void main(String[] args) {
		List<Integer> list = newArrayList(1, 4, 45, 6, 10, -8);
		System.out.println(pair(list, 16));

		int[] a1 = { 2, 7, 11, 15 };
		Print.print(pair(a1, 9)); // 0,1

		int[] a2 = { 3, 2, 4 };
		Print.print(pair(a2, 6)); // 1,2

		int[] a3 = { 3, 3 };
		Print.print(pair(a3, 6)); // 0,1
	}
}

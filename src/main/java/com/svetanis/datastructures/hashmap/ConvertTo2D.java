package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2610. Convert an Array Into a 2D Array With Conditions

public final class ConvertTo2D {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> convert(int[] a) {
		Map<Integer, Integer> map = frequencies(a);
		List<List<Integer>> lists = new ArrayList<>();
		for (int key : map.keySet()) {
			int freq = map.get(key);
			for (int f = 0; f < freq; f++) {
				if (lists.size() <= f) {
					lists.add(new ArrayList<>());
				}
				lists.get(f).add(key);
			}
		}
		return lists;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			map.merge(num, 1, Integer::sum);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 4, 1, 2, 3, 1 };
		System.out.println(convert(a1)); // [1 3 4 2], [1 3], [1]

		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(convert(a2)); // 4 3 2 1
	}
}
package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1481. Least Number of Unique Integers after K Removals

public final class MinimizeUniqueIntsKRemovals {
	// Time Complexity: O(n log n)
	// Space Complexity: O(n)

	public static int minimize(int[] a, int k) {
		Map<Integer, Integer> map = frequencies(a);
		List<Integer> frequencies = new ArrayList<>(map.values());
		Collections.sort(frequencies);
		for (int i = 0, total = frequencies.size(); i < total; i++) {
			k -= frequencies.get(i);
			if (k < 0) {
				return total - i;
			}
		}
		return 0;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			map.merge(num, 1, Integer::sum);
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
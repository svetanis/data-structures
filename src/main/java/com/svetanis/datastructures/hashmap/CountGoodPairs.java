package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1512. Number of Good Pairs

public final class CountGoodPairs {
	// Time Complexity: O(n)

	public static int cgpSimple(int[] a) {
		int count = 0;
		int[] freq = new int[101];
		for (int num : a) {
			count += freq[num];
			freq[num]++;
		}
		return count;
	}

	public static int cgp(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		int count = 0;
		for (int num : a) {
			if (map.containsKey(num)) {
				count += map.get(num);
			}
			map.put(num, map.getOrDefault(num, 0) + 1);
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
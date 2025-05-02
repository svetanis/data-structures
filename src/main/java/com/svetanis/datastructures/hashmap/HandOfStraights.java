package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 846. Hand of Straights

public final class HandOfStraights {
	// Time Complexity: O(n log n)
	// Space Complexity: O(n)
	
	public static boolean straightHand(int[] hand, int size) {
		Map<Integer, Integer> map = counts(hand);
		Arrays.sort(hand);
		for (int card : hand) {
			if (map.containsKey(card)) {
				for (int curr = card; curr < card + size; curr++) {
					if (!map.containsKey(curr)) {
						return false;
					}
					map.merge(curr, -1, Integer::sum);
					if (map.get(curr) == 0) {
						map.remove(curr);
					}
				}
			}
		}
		return true;
	}

	private static Map<Integer, Integer> counts(int[] hand) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int card : hand) {
			map.merge(card, 1, Integer::sum);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] hand1 = { 1, 2, 3, 6, 2, 3, 4, 7, 8 };
		System.out.println(straightHand(hand1, 3)); // true
		int[] hand2 = { 1, 2, 3, 4, 5 };
		System.out.println(straightHand(hand2, 4)); // false
	}
}
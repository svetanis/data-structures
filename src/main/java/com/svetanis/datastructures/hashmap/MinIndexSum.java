package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 599. Minimum Index Sum of Two Lists
//
// mis takes two lists of distinct strings and returns the strings they have in common
// whose index in list1 plus index in list2 is smallest; every string tied for that sum is
// returned, in any order. On LeetCode: 1 <= list1.length, list2.length <= 1000, the strings
// in each list are unique, and the lists share at least one string.
//
// A map from each string of list2 to its index makes the second index one lookup. Walk
// list1, and for every shared string compare its index sum with the best so far: a smaller
// sum starts a new answer list, an equal sum joins the current one.

public final class MinIndexSum {
	// Time complexity: O(n + m), one map call per string of each list
	// Auxiliary Space: O(m) for the map of list2, plus the answer

	public static String[] mis(String[] list1, String[] list2) {
		int min = 2000; // START above any sum: two indexes below 1000 sum to at most 1998
		Map<String, Integer> map = indexes(list2);
		List<String> list = new ArrayList<>();
		for (int index = 0; index < list1.length; index++) {
			String s = list1[index];
			if (!map.containsKey(s)) {
				continue; // SKIP: not in list2
			}
			int curr = index + map.get(s);
			if (curr < min) {
				list = new ArrayList<>(); // BETTER: a smaller sum starts the answer again
				list.add(s);
				min = curr;
			} else if (curr == min) {
				list.add(s); // TIE: joins the answer
			}
		}
		return list.toArray(new String[0]);
	}

	private static Map<String, Integer> indexes(String[] a) {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i); // INDEX of each string of list2
		}
		return map;
	}

	public static void main(String[] args) {
		String[] list1 = { "Shogun", "Tapioca Express", "Burger King", "KFC" };
		String[] list2 = { "Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun" };
		System.out.println(Arrays.toString(mis(list1, list2))); // Shogun

		String[] list3 = { "Shogun", "Tapioca Express", "Burger King", "KFC" };
		String[] list4 = { "KFC", "Shogun", "Burger King" };
		System.out.println(Arrays.toString(mis(list3, list4))); // Shogun

		String[] list5 = { "happy", "sad", "good" };
		String[] list6 = { "sad", "happy", "good" };
		System.out.println(Arrays.toString(mis(list5, list6))); // happy, sad: list1's order of the tie, any order is accepted
	}
}

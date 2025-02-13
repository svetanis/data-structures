package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.java.base.utils.Print;

// 599. Minimum Index Sum of Two Lists

public final class MinIndexSum {
	// Time Complexity: O(n + m)

	public static String[] mis(String[] list1, String[] list2) {
		int min = 2000;
		Map<String, Integer> map = indexes(list2);
		List<String> list = new ArrayList<>();
		for (int index = 0; index < list1.length; index++) {
			String s = list1[index];
			if (!map.containsKey(s)) {
				continue;
			}
			int curr = index + map.get(s);
			if (curr < min) {
				list = new ArrayList<>();
				list.add(s);
				min = curr;
			} else if (curr == min) {
				list.add(s);
			}
		}
		return list.toArray(new String[0]);
	}

	private static Map<String, Integer> indexes(String[] a) {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i);
		}
		return map;
	}

	public static void main(String[] args) {
		String[] list1 = { "Shogun", "Tapioca Express", "Burger King", "KFC" };
		String[] list2 = { "Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun" };
		Print.print(mis(list1, list2)); // Shogun

		String[] list3 = { "Shogun", "Tapioca Express", "Burger King", "KFC" };
		String[] list4 = { "KFC", "Shogun", "Burger King" };
		Print.print(mis(list3, list4)); // Shogun

		String[] list5 = { "happy", "sad", "good" };
		String[] list6 = { "sad", "happy", "good" };
		Print.print(mis(list5, list6)); // "sad","happy"
	}
}

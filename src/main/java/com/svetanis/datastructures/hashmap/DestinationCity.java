package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1436. Destination City

public final class DestinationCity {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static String destinationCity(List<List<String>> paths) {
		Set<String> set = new HashSet<>();
		for (List<String> path : paths) {
			set.add(path.get(0));
		}
		for (List<String> path : paths) {
			String destination = path.get(1);
			if (!set.contains(destination)) {
				return destination;
			}
		}
		return "";
	}

	public static String destination(List<List<String>> paths) {
		Map<String, String> map = new HashMap<>();
		for (List<String> path : paths) {
			String from = path.get(0);
			String to = path.get(1);
			map.put(from, to);
		}
		for (String destination : map.values()) {
			if (!map.keySet().contains(destination)) {
				return destination;
			}
		}
		return "";
	}

	public static void main(String[] args) {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("London", "New York"));
		list.add(Arrays.asList("New York", "Lima"));
		list.add(Arrays.asList("Lima", "Sao Paulo"));
		System.out.println(destinationCity(list)); // Sao Paulo

		List<List<String>> list1 = new ArrayList<>();
		list1.add(Arrays.asList("B", "C"));
		list1.add(Arrays.asList("D", "B"));
		list1.add(Arrays.asList("C", "A"));
		System.out.println(destinationCity(list1)); // A

		List<List<String>> list2 = new ArrayList<>();
		list2.add(Arrays.asList("A", "Z"));
		System.out.println(destinationCity(list2)); // Z
	}
}
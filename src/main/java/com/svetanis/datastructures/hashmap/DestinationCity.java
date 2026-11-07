package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1436. Destination City
//
// destinationCity(paths) and destination(paths) take direct roads, each { from, to }, and
// return the city that no road leaves from. On LeetCode: 1 <= paths.length <= 100, the roads
// form one line with no loop, so exactly one such city exists.
//
// The destination is a city some road arrives at and no road leaves. So collect every
// starting city, then return the first arrival city that is not among them. The final ""
// is reached only on input the statement rules out.
//
// destinationCity keeps the starting cities in a set; destination keeps a map of from -> to
// and checks its values against its keys. On a line each city has at most one road out, so
// the map loses nothing.
//
// Siblings, same problem: none in this package.

public final class DestinationCity {
	// Time complexity: O(n), expected, n = roads -- one set or map step per road, each on a
	// city name of bounded length
	// Auxiliary Space: O(n)

	public static String destinationCity(List<List<String>> paths) {
		Set<String> set = new HashSet<>(); // LEAVES: every city some road starts from
		for (List<String> path : paths) {
			set.add(path.get(0));
		}
		for (List<String> path : paths) {
			String destination = path.get(1);
			if (!set.contains(destination)) { // END?: arrived at, never left
				return destination;
			}
		}
		return "";
	}

	public static String destination(List<List<String>> paths) {
		Map<String, String> map = new HashMap<>(); // ROAD: from -> to, one road out per city
		for (List<String> path : paths) {
			String from = path.get(0);
			String to = path.get(1);
			map.put(from, to);
		}
		for (String destination : map.values()) {
			if (!map.keySet().contains(destination)) { // END?: arrived at, never left
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
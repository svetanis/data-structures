package com.svetanis.datastructures.tree.binary.bt.lca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1257. Smallest Common Region

public final class SmallestCommonRegion {
	// Time Complexity: O(log n)

	public static String scr(List<List<String>> regions, String region1, String region2) {
		Map<String, String> map = parents(regions);
		Set<String> set1 = new HashSet<>();
		while (map.containsKey(region1)) {
			set1.add(region1);
			region1 = map.get(region1);
		}
		while (map.containsKey(region2)) {
			if (set1.contains(region2)) {
				return region2;
			}
			region2 = map.get(region2);
		}
		return region1;
	}

	private static Map<String, String> parents(List<List<String>> regions) {
		Map<String, String> map = new HashMap<>();
		for (List<String> region : regions) {
			String parent = region.get(0);
			for (int i = 1; i < region.size(); i++) {
				map.put(region.get(i), parent);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("Earth", "North America", "United States"));
		list.add(Arrays.asList("United States", "California"));
		list.add(Arrays.asList("United States", "New York"));
		list.add(Arrays.asList("California", "Los Angeles"));
		list.add(Arrays.asList("New York", "New York City"));
		System.out.println(scr(list, "Los Angeles", "New York City")); // United States
	}
}

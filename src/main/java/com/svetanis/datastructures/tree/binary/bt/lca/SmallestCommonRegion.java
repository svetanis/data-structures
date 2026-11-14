package com.svetanis.datastructures.tree.binary.bt.lca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1257. Smallest Common Region
//
// Input: regions -- rows of region names; in each row the first name is a region that contains all
// the others in the row; region1, region2 -- two different regions. Returns the smallest region that
// contains both (a region counts as containing itself). On LeetCode: 2 <= regions.length <= 10^4,
// 2 <= regions[i].length <= 20, 1 <= name length <= 20, no region is contained in two others,
// and both regions exist.
//
// The one idea: the rows describe a tree where each region has at most one parent, so a region's
// parent is found with one lookup in a map built from the rows. Climb from region1 to the top,
// writing down every region on the way (the top region has no parent, so it is not written down).
// Then climb from region2 until a region already written down is met -- that is the first region
// both climbs share, the smallest one. If region2's climb ends without meeting one, the only
// region left in common is the top, which is where region1's climb stopped, so that is returned.
//
// Time: O(R + d) -- R names in all the rows to build the parent map, then at most the depth d of
// the tree for each climb.
// Space: O(R) -- the parent map; the set of regions on region1's climb is at most d.
//
// Siblings: LcaBtParent, LcaBtParentLevel and LcaBtParentStack answer the same question for binary
// tree nodes that hold a parent pointer; here the parent pointers are the map.

public final class SmallestCommonRegion {

	public static String scr(List<List<String>> regions, String region1, String region2) {
		Map<String, String> map = parents(regions); // PARENT: region -> the region containing it
		Set<String> set1 = new HashSet<>();
		while (map.containsKey(region1)) { // CLIMB: from region1, noting every region on the way
			set1.add(region1);
			region1 = map.get(region1);
		}
		while (map.containsKey(region2)) { // CLIMB: from region2 until it meets region1's climb
			if (set1.contains(region2)) {
				return region2;
			}
			region2 = map.get(region2);
		}
		return region1; // TOP: no meeting below it, so the top region is the common one
	}

	private static Map<String, String> parents(List<List<String>> regions) {
		Map<String, String> map = new HashMap<>();
		for (List<String> region : regions) {
			String parent = region.get(0);
			for (int i = 1; i < region.size(); i++) {
				map.put(region.get(i), parent); // RECORD: this name sits inside the first one
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

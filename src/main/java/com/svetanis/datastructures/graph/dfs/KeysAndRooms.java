package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 841. Keys and Rooms

public final class KeysAndRooms {
	// Time Complexity: O(N + E)
	// Space Complexity: O(N)

	public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
		int n = rooms.size();
		Set<Integer> visited = new HashSet<>();
		dfs(rooms, 0, visited);
		return visited.size() == n;
	}

	private static void dfs(List<List<Integer>> g, int src, Set<Integer> visited) {
		visited.add(src);
		for (int key : g.get(src)) {
			if (!visited.contains(key)) {
				dfs(g, key, visited);
			}
		}
	}

	public static void main(String[] args) {
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(1));
		list1.add(asList(2));
		list1.add(asList(3));
		list1.add(asList());
		System.out.println(canVisitAllRooms(list1)); // true

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(1, 3));
		list2.add(asList(3, 0, 1));
		list2.add(asList(2));
		list2.add(asList(0));
		System.out.println(canVisitAllRooms(list2)); // false
	}
}

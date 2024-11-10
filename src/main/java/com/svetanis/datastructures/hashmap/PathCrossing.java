package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 1496. Path Crossing

public final class PathCrossing {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static boolean isCrossing(String s) {
		int x = 0;
		int y = 0;
		Set<Integer> set = new HashSet<>();
		set.add(0);
		for (char c : s.toCharArray()) {
			switch (c) {
			case 'N':
				y++;
				break;
			case 'S':
				y--;
				break;
			case 'E':
				x++;
				break;
			case 'W':
				x--;
				break;
			}
			int path = y * 20000 + x;
			if (set.contains(path)) {
				return true;
			}
			set.add(path);
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isCrossing("E")); // false
		System.out.println(isCrossing("NES")); // false
		System.out.println(isCrossing("NESWW")); // true
		System.out.println(isCrossing("NNSWWEWSSESSWENNW")); // true
	}
}
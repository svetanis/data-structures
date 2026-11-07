package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 1496. Path Crossing
//
// isCrossing(path) takes a string of 'N', 'S', 'E' and 'W', each one step north, south, east
// or west, starting at (0, 0). It returns true if the walk stands on any point twice, the
// start included, and false otherwise. On LeetCode: 1 <= path.length <= 10^4.
//
// Keep every point stood on in a set; the first step onto a point already in it is a
// crossing. Each point is stored as one int, y * 20000 + x. That code is safe only inside
// LeetCode's limit: two different points share a code only if their y differ by 1 and their
// x by 20000, so one point is at x = 10^4 or -10^4. With at most 10^4 steps, that point takes
// every step along y = 0, and a point one row up or down is out of reach. A longer walk
// breaks it: "E" * 10^4 + "N" + "W" * 2 * 10^4 reports a crossing that is not there.
//
// Siblings, same problem: none in this package.

public final class PathCrossing {
	// Time complexity: O(n), one set lookup and insert per step, on average
	// Auxiliary Space: O(n), one set entry per point stood on

	public static boolean isCrossing(String s) {
		int x = 0;
		int y = 0;
		Set<Integer> set = new HashSet<>();
		set.add(0); // VISIT: the start, (0, 0)
		for (char c : s.toCharArray()) {
			switch (c) { // STEP
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
			int path = y * 20000 + x; // CODE: one int per point, unique while |x|, |y| <= 10^4
			if (set.contains(path)) { // SEEN?
				return true;
			}
			set.add(path); // VISIT
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
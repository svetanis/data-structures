package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 554. Brick Wall
//
// leastBricks(wall) takes the wall row by row, each row the widths of its bricks from left
// to right; every row has the same total width. Returns the fewest bricks a vertical line
// from top to bottom must cross. A line along a gap between two bricks crosses neither, and
// a line along either outer edge is not allowed. On LeetCode: 1 <= rows <= 10^4, at most
// 2 * 10^4 bricks in all, each width 1..2^31 - 1, the total width fits in an int.
//
// A line at distance x from the left crosses every row except those with a gap at x. So
// count, for each distance, how many rows have a gap there: the running sum of a row's
// widths, stopping before its last brick, lists that row's gaps -- the last sum would be the
// right edge. The busiest distance is where the line goes, crossing rows - max bricks. A
// wall with no inner gaps leaves max at 0, and the line crosses every row.
//
// Siblings, same problem: none in this package. leastBricks2 is the same count, with the
// maximum taken after the loop instead of during it.

public final class BrickWall {
	// Time complexity: O(b), b = bricks in the whole wall -- each brick is added to a running
	// sum once
	// Auxiliary Space: O(g), g = distinct gap positions, at most b

	public static int leastBricks(List<List<Integer>> wall) {
		int max = 0;
		Map<Integer, Integer> map = new HashMap<>(); // GAPS: distance from the left -> rows with a gap there
		for (List<Integer> row : wall) {
			int width = 0;
			for (int i = 0; i < row.size() - 1; i++) { // STOP: before the last brick, its end is the outer edge
				width += row.get(i); // GAP: where this brick ends
				int freq = map.getOrDefault(width, 0) + 1; // COUNT: one more row with a gap here
				map.put(width, freq);
				max = Math.max(max, freq); // BEST: the busiest gap so far
			}
		}
		return wall.size() - max; // CROSS: every row without a gap at the best distance
	}

	public static int leastBricks2(List<List<Integer>> wall) {
		Map<Integer, Integer> map = new HashMap<>(); // GAPS: distance from the left -> rows with a gap there
		for (List<Integer> row : wall) {
			int width = 0;
			for (int i = 0; i < row.size() - 1; i++) { // STOP: before the last brick
				width += row.get(i); // GAP: where this brick ends
				map.merge(width, 1, Integer::sum); // COUNT
			}
		}
		int max = map.values().stream().max(Comparator.naturalOrder()).orElse(0); // BEST: 0 when no inner gap
		return wall.size() - max; // CROSS
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(Arrays.asList(1, 2, 2, 1));
		list.add(Arrays.asList(3, 1, 2));
		list.add(Arrays.asList(1, 3, 2));
		list.add(Arrays.asList(2, 4));
		list.add(Arrays.asList(3, 1, 2));
		list.add(Arrays.asList(1, 3, 1, 1));
		System.out.println(leastBricks(list)); // 2

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(Arrays.asList(1));
		list2.add(Arrays.asList(1));
		list2.add(Arrays.asList(1));
		System.out.println(leastBricks(list2)); // 3
	}
}
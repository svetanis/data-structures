package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

// 2817. Minimum Absolute Difference Between Elements With Constraint
//
// Each method takes a list of ints and a distance x, and returns the smallest
// |nums[i] - nums[j]| over all pairs of indexes at least x apart, |i - j| >= x. On
// LeetCode: 1 <= nums.length <= 10^5, 1 <= nums[i] <= 10^9, 0 <= x < nums.length.
//
// Walk i from x to the end. Just before looking at nums[i], add nums[i - x] to a sorted
// set: the set then holds exactly nums[0..i - x], every value at least x places before i.
// The closest of those to nums[i] is one of its two neighbours in sorted order, the
// largest at or below it (floor) and the smallest at or above it (ceiling), so two
// searches per i cover every pair once, from its right end.
//
// Three methods, same idea:
//   minAbsDiff   TreeSet, floor and ceiling
//   minAbsDiff2  TreeSet, the same two neighbours found as lower(curr + 1) and
//                higher(curr - 1)
//   minAbsDiff3  TreeMap counting copies; the counts are never read, so a TreeSet that
//                drops repeats gives the same answer

public final class MinAbsDiff {
	// Time complexity: O(n log n), one add and two searches on the sorted set per index
	// Auxiliary Space: O(n), the set holds up to n - x values

	public static int minAbsDiff(List<Integer> list, int x) {
		int mad = Integer.MAX_VALUE;
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = x; i < list.size(); i++) {
			int curr = list.get(i);
			set.add(list.get(i - x)); // ADD: the newest value at least x places back
			Integer ceil = set.ceiling(curr); // NEIGHBOUR at or above curr
			if (ceil != null) {
				mad = Math.min(mad, ceil - curr);
			}
			Integer floor = set.floor(curr); // NEIGHBOUR at or below curr
			if (floor != null) {
				mad = Math.min(mad, curr - floor);
			}
		}
		return mad;
	}

	public static int minAbsDiff2(List<Integer> list, int x) {
		int mad = Integer.MAX_VALUE;
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = x; i < list.size(); i++) {
			int curr = list.get(i);
			set.add(list.get(i - x)); // ADD: the newest value at least x places back
			Integer floor = set.lower(curr + 1); // NEIGHBOUR below curr + 1: at or below curr
			if (floor != null) {
				mad = Math.min(mad, Math.abs(floor - curr));
			}
			Integer ceil = set.higher(curr - 1); // NEIGHBOUR above curr - 1: at or above curr
			if (ceil != null) {
				mad = Math.min(mad, Math.abs(curr - ceil));
			}
		}
		return mad;
	}

	public static int minAbsDiff3(List<Integer> list, int x) {
		int mad = Integer.MAX_VALUE;
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = x; i < list.size(); i++) {
			int curr = list.get(i);
			map.merge(list.get(i - x), 1, Integer::sum); // ADD: the newest value at least x places back
			Integer ceil = map.ceilingKey(curr); // NEIGHBOUR at or above curr
			if (ceil != null) {
				mad = Math.min(mad, ceil - curr);
			}
			Integer floor = map.floorKey(curr); // NEIGHBOUR at or below curr
			if (floor != null) {
				mad = Math.min(mad, curr - floor);
			}
		}
		return mad;
	}

	public static void main(String[] args) {
		List<Integer> list1 = Arrays.asList(4, 3, 2, 4);
		System.out.println(minAbsDiff(list1, 2)); // 0
		List<Integer> list2 = Arrays.asList(5, 3, 2, 10, 15);
		System.out.println(minAbsDiff(list2, 1)); // 1
		List<Integer> list3 = Arrays.asList(1, 2, 3, 4);
		System.out.println(minAbsDiff(list3, 3)); // 3
	}
}

package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

// 2817. Minimum Absolute Difference Between Elements With Constraint

public final class MinAbsDiff {
	// Time Complexity: O(n log n)

	public static int minAbsDiff(List<Integer> list, int x) {
		int mad = Integer.MAX_VALUE;
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = x; i < list.size(); i++) {
			int curr = list.get(i);
			set.add(list.get(i - x));
			Integer ceil = set.ceiling(curr);
			if (ceil != null) {
				mad = Math.min(mad, ceil - curr);
			}
			Integer floor = set.floor(curr);
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
			set.add(list.get(i - x));
			Integer ceil = set.lower(curr + 1);
			if (ceil != null) {
				mad = Math.min(mad, Math.abs(ceil - curr));
			}
			Integer floor = set.higher(curr - 1);
			if (floor != null) {
				mad = Math.min(mad, Math.abs(curr - floor));
			}
		}
		return mad;
	}

	public static int minAbsDiff3(List<Integer> list, int x) {
		int mad = Integer.MAX_VALUE;
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = x; i < list.size(); i++) {
			int curr = list.get(i);
			map.merge(list.get(i - x), 1, Integer::sum);
			Integer ceil = map.ceilingKey(curr);
			if (ceil != null) {
				mad = Math.min(mad, ceil - curr);
			}
			Integer floor = map.floorKey(curr);
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

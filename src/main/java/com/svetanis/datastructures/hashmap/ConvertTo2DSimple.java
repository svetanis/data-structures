package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 2610. Convert an Array Into a 2D Array With Conditions

public final class ConvertTo2DSimple {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> convert(int[] a) {
		int[] counts = counts(a);
		List<List<Integer>> lists = new ArrayList<>();
		for (int num = 1; num <= a.length; num++) {
			int freq = counts[num];
			for (int f = 0; f < freq; f++) {
				if (lists.size() <= f) {
					lists.add(new ArrayList<>());
				}
				lists.get(f).add(num);
			}
		}
		return lists;
	}

	private static int[] counts(int[] a) {
		int[] counts = new int[a.length + 1];
		for (int num : a) {
			counts[num]++;
		}
		return counts;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 4, 1, 2, 3, 1 };
		System.out.println(convert(a1)); // [1 3 4 2], [1 3], [1]

		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(convert(a2)); // 4 3 2 1
	}
}
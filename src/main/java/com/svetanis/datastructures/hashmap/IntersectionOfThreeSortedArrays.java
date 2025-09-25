package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 1213. Intersection of Three Sorted Arrays

public final class IntersectionOfThreeSortedArrays {
	// Time Complexity: O(n)

	public static List<Integer> intersection(int[] a1, int[] a2, int[] a3) {
		int[] counts = new int[2001];
		for (int num : a1) {
			counts[num]++;
		}
		for (int num : a2) {
			counts[num]++;
		}
		List<Integer> list = new ArrayList<>();
		for (int num : a3) {
			counts[num]++;
			if (counts[num] == 3) {
				list.add(num);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 5 };
		int[] a2 = { 2, 5, 7 };
		int[] a3 = { 1, 5, 7 };
		System.out.println(intersection(a1, a2, a3)); // 5
	}
}
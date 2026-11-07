package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 1213. Intersection of Three Sorted Arrays
//
// intersection takes three strictly increasing int arrays and returns, in increasing
// order, the values that occur in all three. On LeetCode: 1 <= length of each <= 1000,
// 1 <= arr[i] <= 2000.
//
// Strictly increasing means no array holds a value twice, so a value's count over the
// three arrays reaches 3 only if it is in every one of them. Count the first two, then
// count the third and collect each value whose count becomes 3 there: the third array is
// increasing, so the answer comes out in increasing order with no sort. The counting relies
// on the no-repeat rule: an array holding a value twice would push its count up alone.

public final class IntersectionOfThreeSortedArrays {
	// Time complexity: O(n1 + n2 + n3), one pass over each array
	// Auxiliary Space: O(V), V = 2001 counters, one per possible value

	public static List<Integer> intersection(int[] a1, int[] a2, int[] a3) {
		int[] counts = new int[2001]; // COUNTS: values run 1..2000
		for (int num : a1) {
			counts[num]++; // COUNT: at most once per array, it is strictly increasing
		}
		for (int num : a2) {
			counts[num]++;
		}
		List<Integer> list = new ArrayList<>();
		for (int num : a3) {
			counts[num]++;
			if (counts[num] == 3) {
				list.add(num); // KEEP: in all three, and in increasing order, a3's order
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
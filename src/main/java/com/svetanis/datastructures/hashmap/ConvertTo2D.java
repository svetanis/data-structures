package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2610. Convert an Array Into a 2D Array With Conditions
//
// convert(a) puts every element of a into rows so that no row holds the same value twice,
// using as few rows as possible, and returns the rows. Any such answer is accepted, in any
// order. On LeetCode: 1 <= a.length <= 200, 1 <= a[i] <= a.length.
//
// A value with c copies needs c different rows, so the fewest rows is the largest count. And
// that many is always enough: give copy number f of every value to row f. Row f then holds
// each value that has more than f copies, once. Count the copies first, then deal each value's
// copies out to rows 0, 1, 2, ..., opening a row the first time a copy reaches it.
//
// Siblings, same problem:
//   ConvertTo2DSimple  counts in an array indexed by value instead of a map; relies on
//                      1 <= a[i] <= a.length, and lists each row in increasing order

public final class ConvertTo2D {
	// Time complexity: O(n) -- each element is counted once and placed once
	// Auxiliary Space: O(n) -- the count map; the rows themselves are the answer

	public static List<List<Integer>> convert(int[] a) {
		Map<Integer, Integer> map = frequencies(a);
		List<List<Integer>> lists = new ArrayList<>();
		for (int key : map.keySet()) {
			int freq = map.get(key);
			for (int f = 0; f < freq; f++) { // DEAL: copy f of this value goes to row f
				if (lists.size() <= f) {
					lists.add(new ArrayList<>()); // OPEN: the first copy to reach row f
				}
				lists.get(f).add(key); // PLACE: once per row, so no row repeats a value
			}
		}
		return lists;
	}

	private static Map<Integer, Integer> frequencies(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : a) {
			map.merge(num, 1, Integer::sum); // COUNT: copies of each value
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 4, 1, 2, 3, 1 };
		System.out.println(convert(a1)); // [[1, 2, 3, 4], [1, 3], [1]]

		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(convert(a2)); // [[1, 2, 3, 4]]
	}
}
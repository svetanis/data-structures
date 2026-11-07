package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 2610. Convert an Array Into a 2D Array With Conditions
//
// convert(a) puts every element of a into rows so that no row holds the same value twice,
// using as few rows as possible, and returns the rows. Any such answer is accepted, in any
// order. On LeetCode: 1 <= a.length <= 200, 1 <= a[i] <= a.length.
//
// A value with c copies needs c different rows, so the fewest rows is the largest count. And
// that many is always enough: give copy number f of every value to row f. Row f then holds
// each value that has more than f copies, once. The values are 1..n, so the counts fit in an
// array indexed by value, and walking it from 1 up deals the values out in increasing order.
// A value outside 1..n throws ArrayIndexOutOfBoundsException.
//
// Siblings, same problem:
//   ConvertTo2D  the same dealing, with the counts in a map: any int values, rows in the
//                map's order

public final class ConvertTo2DSimple {
	// Time complexity: O(n) -- each element is counted once and placed once, and the walk
	// over the count array is n steps
	// Auxiliary Space: O(n) -- the count array, n + 1 slots

	public static List<List<Integer>> convert(int[] a) {
		int[] counts = counts(a);
		List<List<Integer>> lists = new ArrayList<>();
		for (int num = 1; num <= a.length; num++) { // VALUES: 1..n, the LeetCode range
			int freq = counts[num];
			for (int f = 0; f < freq; f++) { // DEAL: copy f of this value goes to row f
				if (lists.size() <= f) {
					lists.add(new ArrayList<>()); // OPEN: the first copy to reach row f
				}
				lists.get(f).add(num); // PLACE: once per row, so no row repeats a value
			}
		}
		return lists;
	}

	private static int[] counts(int[] a) {
		int[] counts = new int[a.length + 1]; // SIZE: index n is the largest value allowed
		for (int num : a) {
			counts[num]++; // COUNT: copies of each value
		}
		return counts;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 4, 1, 2, 3, 1 };
		System.out.println(convert(a1)); // [[1, 2, 3, 4], [1, 3], [1]]

		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(convert(a2)); // [[1, 2, 3, 4]]
	}
}
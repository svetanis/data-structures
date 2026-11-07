package com.svetanis.datastructures.hashmap;

// 1426. Counting Elements
//
// countElements(a) returns how many elements x of a have x + 1 somewhere in a as well.
// Copies are counted separately: in { 1, 1, 2 } both 1s count. On LeetCode:
// 1 <= a.length <= 1000, 0 <= a[i] <= 1000.
//
// First record which values are present, then ask the question once per element. The values
// are 0..1000, so presence fits in an array indexed by value; it has 1002 slots so that the
// largest value, 1000, can look at index 1001 without going out of bounds. A value outside
// 0..1000 throws ArrayIndexOutOfBoundsException.
//
// Siblings, same problem: none in this package.

public final class CountingElements {
	// Time complexity: O(n) -- two passes over a; the 1002-slot array is a fixed cost
	// Auxiliary Space: O(1) -- the array is sized by the value range, not by n

	public static int countElements(int[] a) {
		int[] counts = new int[1002]; // PRESENT: copies of each value 0..1000, plus slot 1001
		for (int num : a) {
			counts[num] += 1;
		}
		int count = 0;
		for (int num : a) { // EACH: every element, copies included
			int next = num + 1;
			if (counts[next] > 0) { // FIND: is x + 1 in a?
				count += 1; // COUNT
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3 };
		System.out.println(countElements(a1)); // 2

		int[] a2 = { 1, 1, 3, 3, 5, 5, 7, 7 };
		System.out.println(countElements(a2)); // 0
	}
}
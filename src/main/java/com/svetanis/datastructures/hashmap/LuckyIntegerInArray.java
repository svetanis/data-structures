package com.svetanis.datastructures.hashmap;

// 1394. Find Lucky Integer in an Array
//
// luckyNum takes an int array and returns its largest lucky integer, one that occurs in
// the array exactly as many times as its own value (2 occurring twice), or -1 if there is
// none. On LeetCode: 1 <= arr.length <= 500, 1 <= arr[i] <= 500.
//
// The values are small, so counters indexed by value replace a map. A lucky value v has
// counts[v] == v. Walk the values from 500 down; the first lucky one is the largest.

public final class LuckyIntegerInArray {
	// Time complexity: O(n + V), one pass to count, then at most V = 500 values walked down
	// Auxiliary Space: O(V), one counter per possible value

	public static int luckyNum(int[] a) {
		int[] counts = counts(a);
		for (int index = 500; index > 0; index--) {
			if (index == counts[index]) {
				return index; // FIND: walking down, so the first lucky value is the largest
			}
		}
		return -1; // NONE: no value occurs as often as itself
	}

	private static int[] counts(int[] a) {
		int[] counts = new int[502]; // COUNTS: values run 1..500
		for (int num : a) {
			counts[num]++; // COUNT
		}
		return counts;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 2, 3, 4 };
		System.out.println(luckyNum(a1)); // 2
		int[] a2 = { 1, 2, 2, 3, 3, 3 };
		System.out.println(luckyNum(a2)); // 3
		int[] a3 = { 2, 2, 2, 3, 3 };
		System.out.println(luckyNum(a3)); // -1
	}
}
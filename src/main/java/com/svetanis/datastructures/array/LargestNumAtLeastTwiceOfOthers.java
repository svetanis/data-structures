package com.svetanis.datastructures.array;

// 747. Largest Number At Least Twice of Others
//
// Input: a -- an array of non-negative ints whose largest value is unique. Returns the index of the
// largest value if it is at least twice every other value, otherwise -1. On LeetCode:
// 2 <= a.length <= 50, 0 <= a[i] <= 100, the largest value is unique.
//
// The one idea: the largest is at least twice every other value exactly when it is at least twice
// the SECOND largest, because the second largest is the biggest of the others. So one pass keeps
// the largest, its index, and the second largest; a value above the largest pushes the old largest
// down into second place, and a value between the two replaces the second. Both start at 0, which
// no value is below.
//
// Time: O(n) -- one pass.
// Space: O(1) -- two values and an index.

public final class LargestNumAtLeastTwiceOfOthers {

	public static int dominantIndex(int[] a) {
		int max = 0; // MAX: the largest value so far
		int second = 0; // SECOND: the largest value among the others
		int index = -1; // WHERE: the index of max
		for (int i = 0; i < a.length; i++) {
			int curr = a[i];
			if (curr > max) {
				second = max; // DEMOTE: the old largest becomes the second
				max = curr;
				index = i;
			} else if (curr > second) {
				second = curr; // SECOND place changes hands
			}
		}
		return max >= 2 * second ? index : -1; // TEST: at least twice the biggest of the others
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 6, 1, 0 };
		System.out.println(dominantIndex(a1)); // 1
		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(dominantIndex(a2)); // -1
	}
}

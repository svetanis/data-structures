package com.svetanis.datastructures.array;

import java.util.Arrays;

// 1502. Can Make Arithmetic Progression From Sequence
//
// Input: a -- an array of ints. Returns true if the numbers can be reordered so that the
// difference between each two consecutive numbers is the same. On LeetCode: 2 <= a.length <= 1000,
// -10^6 <= a[i] <= 10^6. The method sorts a.
//
// The one idea: if any order works, sorted order works. An arithmetic progression has a fixed
// step, so its terms are all increasing (step > 0), all decreasing (step < 0) or all equal; sorted
// ascending puts the progression in its increasing order, and then every neighbouring pair must
// differ by the same amount. So sort, take the gap of the first pair, and check that every other
// neighbouring pair has that gap.
//
// Time: O(n log n) -- the sort; the check is one pass.
// Space: O(1) -- beyond the sort's own stack.

public final class CanMakeArithmeticProgression {

	public static boolean canMakeArithmeticProgression(int[] a) {
		Arrays.sort(a); // SORT: a progression, if one exists, is the sorted order
		int diff = Math.abs(a[1] - a[0]); // STEP: the gap every neighbouring pair must match
		for (int i = 2; i < a.length; i++) {
			if (a[i] - a[i - 1] != diff) {
				return false; // BREAK: two neighbours with a different gap
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 5, 1 };
		System.out.println(canMakeArithmeticProgression(a1)); // true

		int[] a2 = { 1, 2, 4 };
		System.out.println(canMakeArithmeticProgression(a2)); // false
	}
}

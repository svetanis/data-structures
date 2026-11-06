package com.svetanis.datastructures.array;

// 896. Monotonic Array
//
// Input: an integer array a. On LeetCode: 1 <= a.length <= 10^5, -10^5 <= a[i] <= 10^5.
// Returns true if a never goes down (non-decreasing) or never goes up (non-increasing).
//
// The one idea: compare each pair of neighbours. A pair that goes up proves the array is not
// non-increasing; a pair that goes down proves it is not non-decreasing; an equal pair proves
// nothing. So two flags, asc ("some pair went up") and dsc ("some pair went down"), are enough:
// the array is monotonic exactly when the two flags never end up both true. As soon as both are
// set no later element can undo it, so the loop stops.
//
// Time: O(n) -- one pass over the neighbouring pairs.
// Space: O(1) -- two booleans.

public final class MonotonicArray {

	public static boolean isMonotonic(int[] a) {
		boolean asc = false; // FLAG: some pair went up
		boolean dsc = false; // FLAG: some pair went down
		for (int i = 1; i < a.length; i++) {
			if (a[i - 1] < a[i]) {
				asc = true; // RECORD: a rise
			} else if (a[i - 1] > a[i]) {
				dsc = true; // RECORD: a fall
			}
			if (asc && dsc) { // BAD: went both ways, never monotonic again
				return false;
			}
		}
		return true; // RETURN: at most one direction was seen
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 2, 3 };
		System.out.println(isMonotonic(a1)); // true

		int[] a2 = { 6, 5, 4, 4 };
		System.out.println(isMonotonic(a2)); // true

		int[] a3 = { 1, 3, 2 };
		System.out.println(isMonotonic(a3)); // false
	}
}

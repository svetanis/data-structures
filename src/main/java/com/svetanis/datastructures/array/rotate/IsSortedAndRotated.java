package com.svetanis.datastructures.array.rotate;

// 1752. Check if Array Is Sorted and Rotated
//
// Input: an integer array a. Returns true if a was originally sorted in non-decreasing order and
// then rotated some number of positions (possibly zero); duplicates are allowed. On LeetCode:
// 1 <= a.length <= 100, 1 <= a[i] <= 100.
//
// The one idea: treat the array as a circle by letting the last element be followed by the first.
// A sorted circle goes down exactly once: at the place where the rotation cut it (from the largest
// number back to the smallest). A circle that is already sorted and not rotated goes down once
// too, between the last and the first element, unless every number is equal, when it goes down
// never. So count the places where a[i] > a[next], with next wrapping around, and the answer is
// true when the count is at most 1.
//
// Time: O(n) -- one pass over the n neighbouring pairs of the circle.
// Space: O(1) -- one counter.

public final class IsSortedAndRotated {

	public static boolean check(int[] a) {
		int n = a.length;
		if (n <= 1) {
			return true;
		}
		int inversionCount = 0; // DROPS: places on the circle where the next number is smaller
		for (int i = 0; i < n; i++) {
			int next = (i + 1) % n; // WRAP: the last element is followed by the first
			if (a[i] > a[next]) {
				inversionCount += 1; // RECORD: a drop
				if (inversionCount > 1) { // BAD: a sorted circle drops at most once
					return false;
				}
			}
		}
		return inversionCount <= 1; // RETURN: no drop, or the single drop at the cut
	}

	public static void main(String[] args) {
		int[] a = { 3, 4, 5, 1, 2 };
		System.out.println(check(a)); // true
		int[] a1 = { 2, 1, 3, 4 };
		System.out.println(check(a1)); // false
		int[] a2 = { 1, 2, 3 };
		System.out.println(check(a2)); // true
	}
}
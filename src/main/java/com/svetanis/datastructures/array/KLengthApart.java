package com.svetanis.datastructures.array;

// 1437. Check If All 1's Are at Least Length K Places Away
//
// Input: a -- an array of 0s and 1s; k -- a distance. Returns true if every two 1s have at least k
// 0s between them. On LeetCode: 1 <= a.length <= 10^5, 0 <= k <= a.length, a[i] is 0 or 1.
//
// The one idea: only neighbouring 1s can be too close -- if neighbouring 1s are at least k zeros
// apart, so is every farther pair. So walk left to right, counting the zeros since the last 1;
// at each 1 that is not the first, the count is the gap to its neighbour, and it must be >= k.
// Zeros before the first 1 do not count, which is why the first 1 is remembered separately.
//
// Time: O(n) -- one pass.
// Space: O(1) -- a gap counter and a flag.

public final class KLengthApart {

	public static boolean kLenApart(int[] a, int k) {
		int dist = 0; // GAP: zeros seen since the last 1
		boolean first = true; // FIRST: no 1 seen yet, so no gap to check
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				dist += 1; // COUNT the zero
			} else {
				if (!first && dist < k) {
					return false; // BREAK: two neighbouring 1s with fewer than k zeros between
				}
				dist = 0; // RESET: a new gap starts after this 1
				first = false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 0, 0, 1, 0, 0, 1 };
		System.out.println(kLenApart(a1, 2)); // true

		int[] a2 = { 1, 0, 0, 1, 0, 1 };
		System.out.println(kLenApart(a2, 2)); // false

		int[] a3 = { 0, 1, 0, 0, 1, 0, 0, 1 };
		System.out.println(kLenApart(a3, 2)); // true
	}
}

package com.svetanis.datastructures.array;

// 1375. Number of Times Binary String Is Prefix-Aligned
//
// Input: flips -- a permutation of 1 to n. A binary string of n bits starts all 0; at step i the
// bit at position flips[i] (counting from 1) is turned to 1. After a step the string is
// prefix-aligned if the 1s are exactly the first k bits for some k, with no 1 after a 0. Returns
// how many steps end prefix-aligned. On LeetCode: n == flips.length, 1 <= n <= 5 * 10^4, flips is
// a permutation of the numbers in [1, n].
//
// The one idea: after i + 1 steps exactly i + 1 distinct positions are 1. They fill the first
// i + 1 positions exactly when none of them lies beyond position i + 1, that is, when the largest
// position flipped so far is i + 1: i + 1 different positions that all fit in 1 .. i + 1 must be
// all of them. So track the running maximum of flips and count the steps where it equals the
// number of steps taken.
//
// Time: O(n) -- one pass.
// Space: O(1) -- a maximum and a count.

public final class CountPrefixAlignedStrings {

	public static int count(int[] flips) {
		int max = 0; // MAX: the largest position turned to 1 so far
		int count = 0;
		for (int i = 0; i < flips.length; i++) {
			max = Math.max(max, flips[i]);
			if (max == i + 1) { // ALIGNED: i + 1 ones, none beyond position i + 1
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 2, 4, 1, 5 };
		System.out.println(count(a1)); // 2

		int[] a2 = { 4, 1, 2, 3 };
		System.out.println(count(a2)); // 1
	}
}

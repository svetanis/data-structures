package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1647. Minimum Deletions to Make Character Frequencies Unique
//
// minDeletions takes a string of lowercase letters and returns the fewest characters to
// delete so that no two different letters occur the same number of times (a letter
// deleted entirely occurs 0 times and clashes with nothing). On LeetCode:
// 1 <= s.length <= 10^5.
//
// Sort the 26 counts and take them from the largest down. The largest keeps its count;
// each next one must end strictly below the count kept just before it, prev. A count
// already below prev is kept as it is. A count at or above prev is cut to prev - 1, which
// is the most it can keep, so the fewest deletions. Once the kept count reaches 0, every
// letter still to come is deleted entirely.

public final class MinDeletions {
	// Time complexity: O(n), one pass to count; sorting 26 counts is a fixed cost
	// Auxiliary Space: O(n) for the char[] copy of s; the counts are 26 ints whatever the length

	public static int minDeletions(String s) {
		int[] counts = new int[26];
		for (char c : s.toCharArray()) {
			counts[c - 'a']++; // COUNT
		}
		Arrays.sort(counts); // SORT: so the walk below goes from the largest count down
		int minDeletions = 0;
		int prev = Integer.MAX_VALUE; // KEPT just before: nothing yet, so no limit
		for (int i = counts.length - 1; i >= 0; i--) {
			int curr = counts[i];
			if (prev == 0) {
				minDeletions += curr; // DELETE all: no count above 0 is left to keep
			} else if (curr >= prev) {
				minDeletions += curr - prev + 1; // CUT to prev - 1
				prev--; // KEPT: prev - 1
			} else {
				prev = curr; // KEEP: already below prev
			}
		}
		return minDeletions;
	}

	public static void main(String[] args) {
		System.out.println(minDeletions("aab")); // 0
		System.out.println(minDeletions("aaabbbcc")); // 2
		System.out.println(minDeletions("ceabaacb")); // 2
	}
}
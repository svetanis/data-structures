package com.svetanis.datastructures.hashmap;

// 1189. Maximum Number of Balloons
//
// mnb takes a string of lowercase letters and returns how many copies of the word
// "balloon" can be spelled from its letters, each letter used at most once. On LeetCode:
// 1 <= text.length <= 10^4.
//
// One "balloon" uses b, a and n once each and l and o twice each. Halve the counts of l
// and o, rounding down, and each of b, a, l, o, n now says how many words it can supply;
// the letter in shortest supply sets the answer, so it is the smallest of the five.

public final class MaxNumOfBalloons {
	// Time complexity: O(n), one pass to count, then the 7 letters of "balloon"
	// Auxiliary Space: O(n) for the char[] copy of s; the counts are 26 ints whatever the length

	public static int mnb(String s) {
		int[] a = freq(s);
		a['l' - 'a'] /= 2; // HALVE: each word needs two l's
		a['o' - 'a'] /= 2; // HALVE: and two o's
		int maxBallons = Integer.MAX_VALUE;
		for (char c : "balloon".toCharArray()) {
			maxBallons = Math.min(maxBallons, a[c - 'a']); // LIMIT: the scarcest letter decides
		}
		return maxBallons;
	}

	private static int[] freq(String s) {
		int[] a = new int[26];
		for (char c : s.toCharArray()) {
			a[c - 'a']++; // COUNT
		}

		return a;
	}

	public static void main(String[] args) {
		System.out.println(mnb("nlaebolko")); // 1
		System.out.println(mnb("loonbalxballpoon")); // 2
		System.out.println(mnb("leetcode")); // 0
	}
}
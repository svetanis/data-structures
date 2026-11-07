package com.svetanis.datastructures.hashmap;

// 409. Longest Palindrome
//
// longestPalindrome takes a string of lowercase and uppercase letters and returns the
// length of the longest palindrome that can be built from its letters, each used at most
// once; case matters, so "Aa" is not a palindrome. On LeetCode: 1 <= s.length <= 2000.
//
// A palindrome reads the same both ways, so its letters pair up around the middle, and at
// most one letter can sit alone in the middle. Every letter gives all its pairs: count if
// even, count - 1 if odd. If any letter had an odd count, one of its spare copies takes
// the middle: + 1, once.

public final class LongestPalindrome409 {
	// Time complexity: O(n), one pass to count, then 128 counters
	// Auxiliary Space: O(1), 128 counters whatever the length

	public static int longestPalindrome(String s) {
		int len = 0;
		boolean odd = false;
		int[] counts = frequency(s);
		for (int count : counts) {
			if (count % 2 == 0) {
				len += count; // PAIRS: all of them
			} else {
				len += (count - 1); // PAIRS: all but one copy
				odd = true; // SPARE: a copy that could take the middle
			}
		}
		return odd ? len + 1 : len; // MIDDLE: at most one letter sits alone
	}

	private static int[] frequency(String s) {
		int[] counts = new int[128]; // COUNTS: by ASCII code, so 'A' and 'a' stay apart
		for (int i = 0; i < s.length(); i++) {
			counts[s.charAt(i)]++;
		}
		return counts;
	}

	public static void main(String[] args) {
		System.out.println(longestPalindrome("abccccdd")); // 7
		System.out.println(longestPalindrome("a")); // 1
	}
}
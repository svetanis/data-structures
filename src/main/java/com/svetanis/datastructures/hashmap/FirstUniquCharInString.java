package com.svetanis.datastructures.hashmap;

// 387. First Unique Character in a String
//
// firstUnique takes a string s and returns the index of its first character that occurs
// exactly once in s, or -1 if every character repeats. On LeetCode: 1 <= s.length <= 10^5,
// s holds lowercase English letters only.
//
// Whether a character is unique depends on the whole string, so it takes two passes: the
// first counts each letter, the second walks s from the left and stops at the first index
// whose letter was counted once. Walking the counts instead would find a unique letter, but
// not the one that comes first in s.

public final class FirstUniquCharInString {
	// Time complexity: O(n), two passes over s
	// Auxiliary Space: O(n) for the char[] copy of s; the counts are 26 ints whatever the length

	public static int firstUnique(String s) {
		char[] chars = s.toCharArray();
		int[] counts = counts(chars);
		for (int i = 0; i < s.length(); i++) {
			int index = chars[i] - 'a';
			if (counts[index] == 1) {
				return i; // FIND: walking s in order, so the first one found is the first in s
			}
		}
		return -1; // NONE: every letter repeats
	}

	private static int[] counts(char[] chars) {
		int[] counts = new int[26];
		for (char c : chars) {
			counts[c - 'a']++; // COUNT: lowercase letters only, so 'a'..'z' -> 0..25
		}
		return counts;
	}

	public static void main(String[] args) {
		System.out.println(firstUnique("leetcode")); // 0
		System.out.println(firstUnique("loveleetcode")); // 2
		System.out.println(firstUnique("aabb")); // -1
	}
}
package com.svetanis.datastructures.hashmap;

// 387. First Unique Character in a String

public final class FirstUniquCharInString {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int firstUnique(String s) {
		char[] chars = s.toCharArray();
		int[] counts = counts(chars);
		for (int i = 0; i < s.length(); i++) {
			int index = chars[i] - 'a';
			if (counts[index] == 1) {
				return i;
			}
		}
		return -1;
	}

	private static int[] counts(char[] chars) {
		int[] counts = new int[26];
		for (char c : chars) {
			counts[c - 'a']++;
		}
		return counts;
	}

	public static void main(String[] args) {
		System.out.println(firstUnique("leetcode")); // 0
		System.out.println(firstUnique("loveleetcode")); // 2
		System.out.println(firstUnique("aabb")); // -1
	}
}
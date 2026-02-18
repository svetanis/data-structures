package com.svetanis.datastructures.hashmap;

// 1160. Find Words That Can Be Formed by Characters

public final class CountChars {
	// Time Complexity: O(L), L - sum of the lengths of all strings
	// Space Complexity: O(C), C - size of char set

	public static int countChars(String[] words, String s) {
		int[] available = new int[26];
		for (char c : s.toCharArray()) {
			available[c - 'a'] += 1;
		}
		int total = 0;
		for (String word : words) {
			int[] chars = new int[26];
			boolean canForm = true;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				chars[index] += 1;
				if (chars[index] > available[index]) {
					canForm = false;
					break;
				}
			}
			if (canForm) {
				total += word.length();
			}
		}
		return total;
	}

	public static void main(String[] args) {
		String[] a1 = { "cat", "bt", "hat", "tree" };
		System.out.println(countChars(a1, "atach")); // 6
		String[] a2 = { "hello", "world", "leetcode" };
		System.out.println(countChars(a2, "welldonehoneyr")); // 10
	}
}
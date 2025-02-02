package com.svetanis.datastructures.hashmap;

// 409. Longest Palindrome

public final class LongestPalindrome409 {
	// Time Complexity: O(n)

	public static int longestPalindrome(String s) {
		int len = 0;
		boolean odd = false;
		int[] counts = frequency(s);
		for (int count : counts) {
			if (count % 2 == 0) {
				len += count;
			} else {
				len += (count - 1);
				odd = true;
			}
		}
		return odd ? len + 1 : len;
	}

	private static int[] frequency(String s) {
		int[] counts = new int[128];
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
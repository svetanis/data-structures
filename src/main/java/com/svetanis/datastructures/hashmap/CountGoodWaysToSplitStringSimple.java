package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 1525. Number of Good Ways to Split a String

public final class CountGoodWaysToSplitStringSimple {
	// Time Complexity: O(n)

	public static int count(String s) {
		int[] chars = freq(s);
		int total = right(chars);
		int count = 0;
		Set<Character> set = new HashSet<>();
		for(char c : s.toCharArray()) {
			set.add(c);
			chars[c - 'a']--;
			if(chars[c - 'a'] == 0) {
				total--;
			}
			if(set.size() == total) {
				count++;
			}
		}
		return count;
	}
	
	private static int right(int[] chars) {
		int count = 0;
		for(int val : chars) {
			if(val > 0) {
				count++;
			}
		}
		return count;
	}
	
	private static int[] freq(String s) {
		int[] chars = new int[26];
		for(char c : s.toCharArray()) {
			chars[c - 'a']++;
		}
		return chars;
	}

	public static void main(String[] args) {
		System.out.println(count("aacaba")); // 2
		System.out.println(count("abcd")); // 1
	}
}
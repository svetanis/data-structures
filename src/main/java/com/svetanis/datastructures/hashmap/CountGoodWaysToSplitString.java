package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1525. Number of Good Ways to Split a String

public final class CountGoodWaysToSplitString {
	// Time Complexity: O(n)

	public static int count(String s) {
		int count = 0;
		Set<Character> set = new HashSet<>();
		Map<Character, Integer> map = new HashMap<>();
		for (char c : s.toCharArray()) {
			map.merge(c, 1, Integer::sum);
		}
		for (char c : s.toCharArray()) {
			set.add(c);
			if (map.merge(c, -1, Integer::sum) == 0) {
				map.remove(c);
			}
			if (set.size() == map.size()) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(count("aacaba")); // 2
		System.out.println(count("abcd")); // 1
	}
}
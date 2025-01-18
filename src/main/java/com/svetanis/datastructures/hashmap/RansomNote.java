package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 383. Ransom Note

// given two strings ransomNote and magazine, 
// return true if ransomNote can be constructed
// by using the letters from magazine and false otherwise

public final class RansomNote {
	// Time Complexity: O(n + m)

	public static boolean canConstructSimple(String s, String p) {
		int[] counts = new int[26];
		for (int i = 0; i < p.length(); i++) {
			counts[p.charAt(i) - 'a']++;
		}
		for (int i = 0; i < s.length(); i++) {
			int f = counts[s.charAt(i) - 'a']--;
			if (f <= 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean canConstruct(String s, String p) {
		Map<Character, Integer> map = freqMap(p);
		for (char c : s.toCharArray()) {
			if (!map.containsKey(c) || map.get(c) <= 0) {
				return false;
			}
			map.put(c, map.get(c) - 1);
		}
		return true;
	}

	private static Map<Character, Integer> freqMap(String s) {
		Map<Character, Integer> map = new HashMap<>();
		for (char c : s.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(canConstruct("a", "b")); // false
		System.out.println(canConstruct("aa", "ab")); // false
		System.out.println(canConstruct("aa", "aab")); // true
	}
}

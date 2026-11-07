package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 383. Ransom Note
//
// canConstruct(ransomNote, magazine) returns true if the note can be built from the
// magazine's letters, each magazine letter used at most once, and false otherwise. Here s is
// the note and p the magazine. On LeetCode: 1 <= both lengths <= 10^5, lowercase English
// letters only.
//
// Count the magazine's letters, then spend one for each letter of the note. The first note
// letter whose count is already used up cannot be built, so the answer is false at once.
//
// Two methods, the same counting. They differ in where the counts live:
//   canConstructSimple  an int[26], one slot per lowercase letter: relies on the limits
//   canConstruct        a map from character to count: works for any characters

public final class RansomNote {
	// Time complexity: O(n + m), one pass over each string
	// Auxiliary Space: O(1) for the 26 slots; O(k) for the map, k distinct magazine characters

	public static boolean canConstructSimple(String s, String p) {
		int[] counts = new int[26];
		for (int i = 0; i < p.length(); i++) {
			counts[p.charAt(i) - 'a']++; // COUNT: the magazine's letters
		}
		for (int i = 0; i < s.length(); i++) {
			int f = counts[s.charAt(i) - 'a']--; // SPEND: f is the count before this letter
			if (f <= 0) { // SHORT: none left for it
				return false;
			}
		}
		return true;
	}

	public static boolean canConstruct(String s, String p) {
		Map<Character, Integer> map = freqMap(p);
		for (char c : s.toCharArray()) {
			if (!map.containsKey(c) || map.get(c) <= 0) { // SHORT: none left for it
				return false;
			}
			map.put(c, map.get(c) - 1); // SPEND
		}
		return true;
	}

	private static Map<Character, Integer> freqMap(String s) {
		Map<Character, Integer> map = new HashMap<>();
		for (char c : s.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1); // COUNT
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(canConstruct("a", "b")); // false
		System.out.println(canConstruct("aa", "ab")); // false
		System.out.println(canConstruct("aa", "aab")); // true
	}
}

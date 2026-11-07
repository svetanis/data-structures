package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 205. Isomorphic Strings
//
// isIsomorphic takes two strings and returns true if the characters of the first can be
// replaced to get the second: every copy of a character is replaced by the same character,
// the order stays, and no two different characters are replaced by the same one (a
// character may stay itself). On LeetCode: 1 <= s.length <= 5 * 10^4, t.length == s.length,
// any valid ASCII characters.
//
// Read both strings side by side. The first time a character c1 of s1 is met, it takes the
// character c2 at the same place in s2; every later c1 must meet that same c2. That alone
// lets two characters of s1 take one c2 ("ab" -> "xx"), so a set remembers the characters
// of s2 already taken, and a new c1 may not take one of those.

public final class IsomorphicStrings {
	// Time complexity: O(n), one pass with constant-time map and set calls
	// Auxiliary Space: O(k), k = distinct characters, one map entry and one set entry each

	public static boolean isIsomorphic(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();
		if (n != m) {
			return false; // LENGTH: a replacement keeps it
		}
		Set<Character> set = new HashSet<>(); // TAKEN: characters of s2 already given out
		Map<Character, Character> map = new HashMap<>(); // c1 -> the c2 it became
		for (int i = 0; i < n; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if (!map.containsKey(c1)) {
				if (set.contains(c2)) {
					return false; // CLASH: another character of s1 already became c2
				}
				set.add(c2);
				map.put(c1, c2); // PAIR c1 with c2 for the rest of the string
			} else {
				if (map.get(c1) != c2) { // CHECK: unboxed, so this compares the characters
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isIsomorphic("aab", "xxy")); // true
		System.out.println(isIsomorphic("aab", "xyz")); // false
		System.out.println(isIsomorphic("egg", "add")); // true
		System.out.println(isIsomorphic("foo", "bar")); // false
		System.out.println(isIsomorphic("paper", "title")); // true
	}
}
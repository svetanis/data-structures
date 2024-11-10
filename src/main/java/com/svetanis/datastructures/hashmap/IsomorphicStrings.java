package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 205. Isomorphic Strings

public final class IsomorphicStrings {
	// Time Complexity: O(n)

	public static boolean isIsomorphic(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();
		if (n != m) {
			return false;
		}
		Set<Character> set = new HashSet<>();
		Map<Character, Character> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if (!map.containsKey(c1)) {
				if (set.contains(c2)) {
					return false;
				}
				set.add(c2);
				map.put(c1, c2);
			} else {
				if (map.get(c1) != c2) {
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
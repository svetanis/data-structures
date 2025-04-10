package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 1003. Check If Word Is Valid After Substitutions

public final class IsValidWord {
	// Time Complexity: O(n)

	public static boolean valid(String s) {
		if (s.length() % 3 != 0) {
			return false;
		}
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == 'c') {
				int size = dq.size();
				if (size < 2) {
					return false;
				}
				if (dq.pop() != 'b' || dq.pop() != 'a') {
					return false;
				}
			} else {
				dq.push(c);
			}

		}
		return dq.isEmpty();
	}

	public static void main(String[] args) {
		System.out.println(valid("aabcbc")); // true
		System.out.println(valid("abcabcababcc")); // true
		System.out.println(valid("abccba")); // false
	}
}

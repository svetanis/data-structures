package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 1003. Check If Word Is Valid After Substitutions
//
// Input: a string of the letters 'a', 'b' and 'c'. Returns true when it can be built from the
// empty string by inserting "abc" at any position, again and again.
//
// Read left to right, every 'c' must finish an "abc" whose 'a' and 'b' are not yet used. The
// stack holds the unused letters, the latest on top, so at each 'c' the top two must be 'b'
// and then 'a'; both are popped. The word is valid when no letter is left unused.

public final class IsValidWord {
	// Time Complexity: O(n), each 'a' and 'b' is pushed once and popped at most once
	// Space Complexity: O(n) for the stack and the char array

	public static boolean valid(String s) {
		if (s.length() % 3 != 0) { // every insertion adds three letters
			return false;
		}
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == 'c') {
				int size = dq.size();
				if (size < 2) {
					return false;
				}
				if (dq.pop() != 'b' || dq.pop() != 'a') { // this 'c' closes the latest 'a', 'b'
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

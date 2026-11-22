package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 385. Mini Parser
//
// Input: a string holding either one integer or a nested list such as "[123,[456,[789]]]".
// Returns it as a NestedInteger.
//
// The stack holds the lists that are still open, the innermost on top. '[' opens a new list;
// digits build a number that is added to the top list at the next ',' or ']'; ']' closes the
// top list and adds it to the list beneath it. The outermost list is never popped, so it is
// the one left to return.
//
// Companion file: NestedInteger, in this package, is LeetCode's interface implemented so that
// this parser can build and print its result.

public final class MiniParser {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), the stack holds one list for each '[' still open

	public NestedInteger deserialize(String s) {
		if (s.charAt(0) != '[') { // PLAIN: the whole string is one integer
			return new NestedInteger(Integer.parseInt(s));
		}
		int num = 0;
		boolean negative = false;
		Deque<NestedInteger> dq = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) { // WALK: one character at a time
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				num = num * 10 + (c - '0'); // DIGIT: extend the number being read
			} else if (c == '-') {
				negative = true; // SIGN: the number being read is negative
			} else if (c == '[') {
				dq.push(new NestedInteger()); // OPEN: a new list on top
			} else if (c == ',' || c == ']') {
				if (Character.isDigit(s.charAt(i - 1))) { // ADD: a number just ended; "[]" adds none
					num = negative ? -num : num;
					dq.peek().add(new NestedInteger(num));
				}
				num = 0; // RESET: ready for the next number
				negative = false;
				if (c == ']' && dq.size() > 1) { // CLOSE: the outermost list stays, to be returned
					NestedInteger top = dq.pop();
					dq.peek().add(top);
				}
			}
		}
		return dq.peek(); // RETURN: the outermost list
	}

	public static void main(String[] args) {
		MiniParser mp = new MiniParser();
		System.out.println(mp.deserialize("324")); // 324
		System.out.println(mp.deserialize("[123,[456,[789]]]")); // [123,[456,[789]]]
	}
}

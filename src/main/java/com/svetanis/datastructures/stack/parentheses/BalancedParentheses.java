package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

// 20. Valid Parentheses
//
// Input: a string of the brackets ( ) [ ] { }. Returns true when every closing bracket
// closes the most recent still-open bracket of the same kind, and none is left open.
//
// The stack holds the opening brackets still waiting for their closer, innermost on top.
// A closer can only close the top one, so it must match it; openers left at the end fail.

public final class BalancedParentheses {
	// Time Complexity: O(n), each character is pushed and popped at most once
	// Space Complexity: O(n), the stack can hold every character

	public static boolean isBalanced(String s) {
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (startsWith(c)) {
				dq.push(c);
			}
			if (endsWith(c)) {
				if (dq.isEmpty()) {
					return false;
				} else if (!isMatch(dq.pop(), c)) {
					return false;
				}
			}
		}
		return dq.isEmpty();
	}

	private static boolean startsWith(char c) {
		return c == '{' || c == '[' || c == '(';
	}

	private static boolean endsWith(char c) {
		return c == '}' || c == ']' || c == ')';
	}

	private static boolean isMatch(char c1, char c2) {
		if (c1 == '{' && c2 == '}') {
			return true;
		} else if (c1 == '[' && c2 == ']') {
			return true;
		} else if (c1 == '(' && c2 == ')') {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(isBalanced("()")); // true
		System.out.println(isBalanced("()[]{}")); // true
		System.out.println(isBalanced("(]")); // false
		System.out.println(isBalanced("([])")); // true

		System.out.println(isBalanced("{()}[]")); // true
		System.out.println(isBalanced("[()]{}{[()()]()}")); // true
		System.out.println(isBalanced("[(])")); // false

		// EVERY case above either balances or fails inside the loop, so none of
		// them notices if the last line becomes "return true".  This one does:
		// each character is individually legal and the string is still unbalanced
		System.out.println(isBalanced("(((")); // false

		// non-bracket input, which LC 20 promises will not happen. This file skips
		// a letter entirely; BalancedParenthesesMap treats it as a closer and
		// answers false. Read the else before reusing either one
		System.out.println(isBalanced("(a)")); // true
	}
}

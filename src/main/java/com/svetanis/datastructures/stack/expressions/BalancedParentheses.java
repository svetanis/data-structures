package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 20. Valid Parentheses

public final class BalancedParentheses {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

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

		System.out.println(isBalanced("{()}[]"));
		System.out.println(isBalanced("[()]{}{[()()]()}"));
		System.out.println(isBalanced("[(])"));
	}
}

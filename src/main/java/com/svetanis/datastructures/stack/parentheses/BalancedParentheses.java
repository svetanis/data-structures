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
//
// Siblings -- 20 and its neighbours:
//   BalancedParenthesesMap    -- 20 again: a map from opener to closer; a non-bracket counts as a closer
//   BalancedParenthesesSimple -- round brackets only, so a counter can replace the stack
//   ValidParenthesisString    -- 678: round brackets plus '*', which may stand for either or nothing
//   MinNumParenthesesToBalanceExpression -- 921: how many brackets to add, not whether it is valid

public final class BalancedParentheses {
	// Time Complexity: O(n), each character is pushed and popped at most once
	// Space Complexity: O(n), the stack can hold every character

	public static boolean isBalanced(String s) {
		Deque<Character> dq = new ArrayDeque<>(); // STACK: openers still waiting, innermost on top
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (startsWith(c)) {
				dq.push(c); // OPEN: it waits for its closer
			}
			if (endsWith(c)) {
				if (dq.isEmpty()) {
					return false; // FAIL: a closer with nothing open
				} else if (!isMatch(dq.pop(), c)) { // MATCH: it can only close the innermost opener
					return false; // FAIL: the wrong kind of closer
				}
			}
		}
		return dq.isEmpty(); // RETURN: an opener left over fails here
	}

	private static boolean startsWith(char c) {
		return c == '{' || c == '[' || c == '('; // TEST: an opener
	}

	private static boolean endsWith(char c) {
		return c == '}' || c == ']' || c == ')'; // TEST: a closer
	}

	private static boolean isMatch(char c1, char c2) {
		if (c1 == '{' && c2 == '}') {
			return true; // MATCH: curly
		} else if (c1 == '[' && c2 == ']') {
			return true; // MATCH: square
		} else if (c1 == '(' && c2 == ')') {
			return true; // MATCH: round
		} else {
			return false; // FAIL: two different kinds
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

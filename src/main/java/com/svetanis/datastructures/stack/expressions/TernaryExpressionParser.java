package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 439. Ternary Expression Parser
//
// s is a nested ternary expression such as "T?2:F?4:5": each operand is one digit, 'T' or 'F',
// and expressions group right to left. Returns the one-character result as a String.
//
// Read s from the right, so the innermost expression is complete before its condition is seen.
// The stack holds the values of the finished expressions to the right, nearest on top. At a
// condition, the top two are the value after its '?' and the value after its ':'; the condition
// keeps one of them, and that one value stands for the whole expression from then on.

public final class TernaryExpressionParser {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n) for the stack

	public static String parseTernary(String s) {
		boolean condition = false;
		Deque<Character> dq = new ArrayDeque<>();
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == ':') {
				continue;
			}
			if (c == '?') {
				condition = true; // the next character to the left is a condition
				continue;
			}
			if (condition) {
				if (c == 'T') {
					char top = dq.pop(); // T: keep the value after '?', drop the one after ':'
					dq.pop();
					dq.push(top);
				} else {
					dq.pop(); // F: drop the value after '?', keep the one after ':'
				}
				condition = false;
			} else {
				dq.push(c);
			}
		}
		return dq.peek() + "";
	}

	public static void main(String[] args) {
		System.out.println(parseTernary("T?T?F:5:3")); // F
		System.out.println(parseTernary("F?1:T?4:5")); // 4
	}
}

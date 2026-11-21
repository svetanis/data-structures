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
//
// Siblings: none in this package. The nearest are the postfix evaluators
// (ReversePolishNotation150, PostfixExpressionEvaluation), where an operator also finds its
// operands already on the stack -- here the reading goes right to left to make that true.

public final class TernaryExpressionParser {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n) for the stack

	public static String parseTernary(String s) {
		boolean condition = false; // HOLD: true when the next character to the left is a condition
		Deque<Character> dq = new ArrayDeque<>(); // STACK: values of the finished expressions to the right
		for (int i = s.length() - 1; i >= 0; i--) { // WALK: right to left
			char c = s.charAt(i);
			if (c == ':') {
				continue; // SKIP: the two values on top already say which is which
			}
			if (c == '?') {
				condition = true; // HOLD: the next character to the left is a condition
				continue;
			}
			if (condition) {
				if (c == 'T') {
					char top = dq.pop(); // PICK: T: keep the value after '?', drop the one after ':'
					dq.pop(); // DROP: the value after ':'
					dq.push(top); // PUSH: the kept value stands for the whole expression
				} else {
					dq.pop(); // PICK: F: drop the value after '?', keep the one after ':'
				}
				condition = false; // HOLD: the condition is used up
			} else {
				dq.push(c); // PUSH: a value waits for its condition
			}
		}
		return dq.peek() + ""; // RETURN: the one value left
	}

	public static void main(String[] args) {
		System.out.println(parseTernary("T?T?F:5:3")); // F
		System.out.println(parseTernary("F?1:T?4:5")); // 4
	}
}

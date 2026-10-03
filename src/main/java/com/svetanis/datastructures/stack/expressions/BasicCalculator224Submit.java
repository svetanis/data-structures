package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 224. Basic Calculator
//
// s holds non-negative integers, '+', '-', brackets and spaces; a '-' may also negate what
// follows it. Returns the value of s.
//
// With only + and -, each number is added to a running result with a sign of +1 or -1, when the
// number ends: at the next '+', '-', ')' or the end of s. '(' pushes the result so far, then the
// sign in front of the bracket, and starts a fresh result; ')' multiplies the bracket's result by
// that sign and adds the saved result back. BasicCalculator224 reads each number in one go.

public final class BasicCalculator224Submit {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), two stack entries per open bracket

	public static int calculate(String s) {
		int sign = 1;
		int result = 0;
		int current = 0;
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				current = current * 10 + (c - '0');
			} else if (c == '+' || c == '-') {
				result += sign * current; // current just ended: add it with the sign before it
				sign = c == '+' ? 1 : -1;
				current = 0;
			} else if (c == '(') {
				stack.push(result);
				stack.push(sign);
				result = 0;
				sign = 1;
			} else if (c == ')') {
				result += sign * current; // the number before ')' belongs inside the bracket
				result = stack.pop() * result + stack.pop(); // the sign was pushed last, so it pops first
				current = 0;
			}
		}
		return result + sign * current; // the last number: nothing after it to end it
	}

	public static void main(String[] args) {
		System.out.println(calculate("1 + 1")); // 2
		System.out.println(calculate("2 - 1 + 2")); // 3
		System.out.println(calculate("(1 + (4 + 5 + 2) - 3) + (6 + 8)")); // 23
	}
}

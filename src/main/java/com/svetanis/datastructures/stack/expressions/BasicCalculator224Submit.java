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
//
// Siblings -- the same family of calculators:
//   BasicCalculator224        -- 224 again, same stack; a helper reads each whole number at once
//   BasicCalculatorII         -- 227: + - * / with no brackets; one stack of signed numbers
//   BasicCalculatorIIApply    -- 227 again, the last number applied after the loop
//   BasicCalculatorIII        -- 772: + - * / and brackets; a stack of numbers and one of operators
//   InfixExpressionEvaluation -- not LeetCode: 772 plus ^, with the operator rules in Expressions

public final class BasicCalculator224Submit {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), two stack entries per open bracket

	public static int calculate(String s) {
		int sign = 1; // SIGN: the sign in front of the number being read
		int result = 0; // START: the running result of the innermost open bracket
		int current = 0; // READ: the number being read, not added yet
		Deque<Integer> stack = new ArrayDeque<>(); // STACK: two entries per open bracket
		for (int i = 0; i < s.length(); i++) { // WALK: one character at a time
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				current = current * 10 + (c - '0'); // READ: one more digit
			} else if (c == '+' || c == '-') {
				result += sign * current; // ADD: current just ended: add it with the sign before it
				sign = c == '+' ? 1 : -1; // SIGN: c belongs to the next number
				current = 0; // READ: the next number starts empty
			} else if (c == '(') {
				stack.push(result); // OPEN: save the result before the bracket
				stack.push(sign); // OPEN: then the sign in front of the bracket
				result = 0; // START: the bracket's own result
				sign = 1; // SIGN: the bracket's first number is added
			} else if (c == ')') {
				result += sign * current; // ADD: the number before ')' belongs inside the bracket
				result = stack.pop() * result + stack.pop(); // CLOSE: the sign was pushed last, so it pops first
				current = 0; // READ: already added, so it must not be added again
			}
		}
		return result + sign * current; // RETURN: the last number: nothing after it to end it
	}

	public static void main(String[] args) {
		System.out.println(calculate("1 + 1")); // 2
		System.out.println(calculate("2 - 1 + 2")); // 3
		System.out.println(calculate("(1 + (4 + 5 + 2) - 3) + (6 + 8)")); // 23
	}
}

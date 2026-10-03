package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;
import static java.lang.Character.isDigit;

import java.util.ArrayDeque;
import java.util.Deque;

// 227. Basic Calculator II
//
// s holds non-negative integers, the operators + - * / and spaces, with no brackets.
// Returns the value of s, with * and / done before + and -, and / truncating toward zero.
//
// A number is applied when it ends, by the operator BEFORE it. + and - wait on the stack as
// signed numbers, since a * or / to their right might still take their number; * and / change
// the top at once. The last number has nothing after it, so it is applied at i == n - 1 -- which
// is why spaces are not skipped with a continue: a trailing space would skip that last step.
// BasicCalculatorIIApply is the same algorithm with the last step after the loop.

public final class BasicCalculatorII {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), one stack entry per number

	public static int calculate(String s) {
		int val = 0;
		char operator = '+';
		int n = s.length();
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			char curr = s.charAt(i);
			if (isDigit(curr)) {
				val = val * 10 + (curr - '0');
			}
			if (isOperator(curr) || i == n - 1) { // val just ended, or s ends here
				switch (operator) { // the STORED operator decides, not curr
				case '+':
					stack.push(val);
					break;
				case '-':
					stack.push(-1 * val);
					break;
				case '*':
					stack.push(stack.pop() * val);
					break;
				case '/':
					stack.push(stack.pop() / val);
					break;
				}
				operator = curr; // curr belongs to the NEXT number
				val = 0;
			}
		}
		int result = 0;
		while (!stack.isEmpty()) {
			result += stack.pop();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(calculate("3+2*2")); // 7
		System.out.println(calculate("3/2")); // 1
		System.out.println(calculate("3+5/2")); // 5
	}
}

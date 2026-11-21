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
//
// Siblings -- the same family of calculators:
//   BasicCalculatorIIApply    -- 227 again: skips spaces, and applies the last number after the loop
//   BasicCalculator224        -- 224: + - and brackets; the stack saves the result and sign per bracket
//   BasicCalculatorIII        -- 772: brackets as well; a stack of numbers and one of operators
//   ClumsyFactorial           -- 1006: this stack with the operators known in advance

public final class BasicCalculatorII {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), one stack entry per number

	public static int calculate(String s) {
		int val = 0; // READ: the number being read right now
		char operator = '+'; // HOLD: the operator before val; '+' for the first number
		int n = s.length();
		Deque<Integer> stack = new ArrayDeque<>(); // STACK: signed numbers waiting to be added
		for (int i = 0; i < n; i++) { // WALK: one character at a time
			char curr = s.charAt(i);
			if (isDigit(curr)) {
				val = val * 10 + (curr - '0'); // READ: one more digit
			}
			if (isOperator(curr) || i == n - 1) { // END: val just ended, or s ends here
				switch (operator) { // APPLY: the STORED operator decides, not curr
				case '+':
					stack.push(val); // WAIT: added at the end
					break;
				case '-':
					stack.push(-1 * val); // WAIT: added at the end, as a negative number
					break;
				case '*':
					stack.push(stack.pop() * val); // APPLY: at once, to the number before it
					break;
				case '/':
					stack.push(stack.pop() / val); // APPLY: at once; Java's / truncates toward zero
					break;
				}
				operator = curr; // HOLD: curr belongs to the NEXT number
				val = 0; // READ: the next number starts empty
			}
		}
		int result = 0; // SUM: only + is left
		while (!stack.isEmpty()) { // SUM: empty the stack
			result += stack.pop(); // SUM: every waiting number, with its sign
		}
		return result; // RETURN: the value of s
	}

	public static void main(String[] args) {
		System.out.println(calculate("3+2*2")); // 7
		System.out.println(calculate("3/2")); // 1
		System.out.println(calculate("3+5/2")); // 5
	}
}

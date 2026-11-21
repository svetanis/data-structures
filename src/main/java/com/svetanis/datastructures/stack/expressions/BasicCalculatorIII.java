package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 772. Basic Calculator III
//
// s holds non-negative integers, the operators + - * /, brackets and spaces; every operator has
// an operand on each side. Returns the value of s, with * and / done before + and -.
//
// One stack holds numbers, the other holds operators and '(' not yet applied. Applying an
// operator pops two numbers and pushes the result. Before an operator is pushed, every operator
// on top with the same or higher precedence is applied: it came earlier, so it goes first. A ')'
// applies the operators down to its '('. So between brackets, precedence rises bottom to top.
//
// Siblings -- the same family of calculators:
//   InfixExpressionEvaluation -- not LeetCode: this same two-stack method plus ^, which groups right
//                                to left; its operator rules live in Expressions
//   BasicCalculator224        -- 224: + - only, so one stack saving the result and sign per bracket
//   BasicCalculatorII         -- 227: no brackets, so one stack of signed numbers is enough
//   BasicCalculatorIIApply    -- 227 again, the last number applied after the loop

public final class BasicCalculatorIII {
	// Time Complexity: O(n), each operator is pushed once and applied once
	// Space Complexity: O(n) for the two stacks

	public static int calculate(String s) {
		Deque<Integer> dqd = new ArrayDeque<>(); // STACK: numbers, and results not used yet
		Deque<Character> dqc = new ArrayDeque<>(); // STACK: operators and '(' not applied yet
		for (int i = 0; i < s.length(); i++) { // WALK: one character, or one whole number
			char c = s.charAt(i);
			if (c == ' ') {
				continue; // SKIP: a space
			}
			if (Character.isDigit(c)) {
				int[] v = values(s, i); // READ: the whole number that starts at i
				int val = v[0]; // READ: the number itself
				i = v[1] - 1; // JUMP: v[1] is just past the number; the loop's i++ lands on it
				dqd.push(val); // PUSH: the number waits for its operator
			} else if (c == '(') {
				dqc.push(c); // OPEN: nothing above it is applied past it
			} else if (c == ')') {
				while (dqc.peek() != '(') { // CLOSE: apply everything inside this bracket
					int b = dqd.pop(); // POP: the right operand is on top
					int a = dqd.pop(); // POP: the left operand
					dqd.push(apply(dqc.pop(), a, b)); // APPLY: the result is one number again
				}
				dqc.pop(); // CLOSE: drop the '(' itself
			} else if (isOperator(c)) {
				while (!dqc.isEmpty() && dqc.peek() != '(' && precedence(dqc.peek()) >= precedence(c)) { // APPLY: an earlier operator, binding at least as tightly, goes first
					int b = dqd.pop(); // POP: the right operand is on top
					int a = dqd.pop(); // POP: the left operand
					dqd.push(apply(dqc.pop(), a, b)); // APPLY: the result is one number again
				}
				dqc.push(c); // PUSH: c waits for its right operand
			}
		}
		while (!dqc.isEmpty()) { // FINISH: apply what still waits, top first
			int b = dqd.pop(); // POP: the right operand is on top
			int a = dqd.pop(); // POP: the left operand
			dqd.push(apply(dqc.pop(), a, b)); // APPLY: the result is one number again
		}
		return dqd.peek(); // RETURN: the one number left is the value of s
	}

	private static int[] values(String s, int index) {
		int val = 0; // READ: the value of the digits read so far
		int i = index;
		while (i < s.length() && Character.isDigit(s.charAt(i))) { // WALK: up to the first non-digit
			val = val * 10 + s.charAt(i) - '0'; // READ: one more digit
			i++;
		}
		return new int[] { val, i }; // RETURN: the number, and the position just past it
	}

	private static int apply(char operator, int a, int b) {
		switch (operator) {
		case '+':
			return a + b; // APPLY: a is the left operand
		case '-':
			return a - b; // APPLY: a is the left operand, so a - b
		case '*':
			return a * b; // APPLY: a times b
		case '/':
			return a / b; // APPLY: truncates toward zero
		default:
			return -1; // NONE: not an operator; never reached
		}
	}

	private static int precedence(char c) {
		switch (c) {
		case '(':
		case ')':
			return 0; // RANK: a bracket is never applied
		case '+':
		case '-':
			return 1; // RANK: + and - bind loosest
		case '*':
		case '/':
			return 2; // RANK: * and / bind tighter
		default:
			return -1; // NONE: not an operator
		}
	}

	private static boolean isOperator(char c) {
		boolean one = c == '+'; // TEST: plus
		boolean two = c == '-'; // TEST: minus
		boolean three = c == '*'; // TEST: times
		boolean four = c == '/'; // TEST: divide
		return one || two || three || four; // RETURN: any of the four
	}

	public static void main(String[] args) {
		System.out.println(calculate("6-4/2")); // 4
		System.out.println(calculate("2*(5+5*2)/3+(6/2+8)")); // 21
		System.out.println(calculate("(2+6* 3+5- (3*14/7+2)*5)+3")); // -12
	}
}

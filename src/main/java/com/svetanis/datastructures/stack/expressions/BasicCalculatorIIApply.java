package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;

import java.util.ArrayDeque;
import java.util.Deque;

// 227. Basic Calculator II -- a number is applied when it ends, by the operator BEFORE it.
//
// s holds non-negative integers, the operators + - * / and spaces, with no brackets.
// Returns the value of s, with * and / done before + and -, and / truncating toward zero.
//
// Only + and - wait: a * or / to their right might still take their number. * and / happen
// the moment both numbers are there -- with no brackets, nothing later can take them away.
// So the stack holds signed numbers still waiting to be added, and a * or / only ever
// changes its top. A - is kept as a negative number, so the end is addition only.
// BasicCalculatorII is the same algorithm in one loop: it applies the last number at
// i == n - 1 instead of after the loop.
//
// Siblings -- the same family of calculators:
//   BasicCalculatorII         -- 227 again: no continue on spaces; the last number applied at i == n - 1
//   BasicCalculator224        -- 224: + - and brackets; the stack saves the result and sign per bracket
//   BasicCalculatorIII        -- 772: brackets as well; a stack of numbers and one of operators
//   ClumsyFactorial           -- 1006: this stack with the operators known in advance

public final class BasicCalculatorIIApply {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(n), one stack entry per number, and toCharArray copies s

	public static int calculate(String s) {
		int num = 0; // READ: the number being read right now
		char operator = '+'; // HOLD: the operator before num; '+' for the first number
		Deque<Integer> dq = new ArrayDeque<>(); // STACK: signed numbers waiting to be added
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (c == ' ') {
				continue; // SKIP: a space never ends anything
			}
			if (Character.isDigit(c)) {
				num = num * 10 + (c - '0'); // READ: numbers can have several digits
			} else if (isOperator(c)) { // END: num just ended...
				apply(dq, operator, num); // APPLY: ...so the STORED operator decides, not c
				num = 0; // READ: the next number starts empty
				operator = c; // HOLD: c belongs to the NEXT number
			}
		}
		apply(dq, operator, num); // APPLY: the last number: nothing after it to trigger it
		int result = 0; // SUM: only + is left
		while (!dq.isEmpty()) { // SUM: empty the stack
			result += dq.pop(); // SUM: only + is left: every - is inside a sign
		}
		return result; // RETURN: the value of s
	}

	// what the operator in front of a number does with it
	private static void apply(Deque<Integer> dq, char operator, int num) {
		if (operator == '*') {
			dq.push(dq.pop() * num); // APPLY: at once: nothing binds tighter
		} else if (operator == '/') {
			dq.push(dq.pop() / num); // APPLY: Java's / truncates toward zero: -7 / 2 is -3
		} else if (operator == '-') {
			dq.push(-1 * num); // WAIT: waits, with its sign
		} else {
			dq.push(num); // WAIT: waits
		}
	}

	public static void main(String[] args) {
		System.out.println(calculate("3+2*2")); // 7
		System.out.println(calculate(" 3/2 ")); // 1
		System.out.println(calculate(" 3+5 / 2 ")); // 5
		System.out.println(calculate("42")); // 42
		System.out.println(calculate("1-7/2")); // -2
	}
}

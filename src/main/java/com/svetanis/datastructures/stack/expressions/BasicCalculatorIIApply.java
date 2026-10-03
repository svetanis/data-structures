package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;

import java.util.ArrayDeque;
import java.util.Deque;

// 227. Basic Calculator II -- a number is applied when it ends, by the operator BEFORE it.
//
// Only + and - wait: a * or / to their right might still take their number. * and / happen
// the moment both numbers are there -- with no brackets, nothing later can take them away.
// So the stack holds signed numbers still waiting to be added, and a * or / only ever
// changes its top. A - is kept as a negative number, so the end is addition only.
// BasicCalculatorII is the same algorithm in one loop: it applies the last number at
// i == n - 1 instead of after the loop.

public final class BasicCalculatorIIApply {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int calculate(String s) {
		int num = 0; // the number being read right now
		char operator = '+'; // the operator before num; '+' for the first number
		Deque<Integer> dq = new ArrayDeque<>(); // signed numbers waiting to be added
		for (char c : s.toCharArray()) {
			if (c == ' ') {
				continue; // a space never ends anything
			}
			if (Character.isDigit(c)) {
				num = num * 10 + (c - '0'); // numbers can have several digits
			} else if (isOperator(c)) { // num just ended...
				apply(dq, operator, num); // ...so the STORED operator decides, not c
				num = 0;
				operator = c; // c belongs to the NEXT number
			}
		}
		apply(dq, operator, num); // the last number: nothing after it to trigger it
		int result = 0;
		while (!dq.isEmpty()) {
			result += dq.pop(); // only + is left: every - is inside a sign
		}
		return result;
	}

	// what the operator in front of a number does with it
	private static void apply(Deque<Integer> dq, char operator, int num) {
		if (operator == '*') {
			dq.push(dq.pop() * num); // at once: nothing binds tighter
		} else if (operator == '/') {
			dq.push(dq.pop() / num); // Java's / truncates toward zero: -7 / 2 is -3
		} else if (operator == '-') {
			dq.push(-1 * num); // waits, with its sign
		} else {
			dq.push(num); // waits
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

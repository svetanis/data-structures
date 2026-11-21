package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 1006. Clumsy Factorial
//
// clumsyFactorial(n) returns n * (n - 1) / (n - 2) + (n - 3) - (n - 4) * (n - 5) / ... 1: the
// numbers n down to 1 with *, /, +, - repeating in that order, * and / done before + and -,
// and / truncating toward zero. clumsySimple returns the same value from n % 4 once n > 4.
//
// This is the stack of Basic Calculator II with the operators known in advance: + and - wait on
// the stack as signed numbers, * and / change the top at once. A - is pushed as a negative
// number, so the end is a plain sum. Dividing a negative top is still right, because Java's /
// truncates toward zero: -a / b == -(a / b).
//
// Siblings:
//   BasicCalculatorII / BasicCalculatorIIApply -- 227: the same stack, reading the operators
//                                                 from a string instead of cycling through them

public final class ClumsyFactorial {
	// Time Complexity: clumsyFactorial O(n), one step per number; clumsySimple O(1)
	// Space Complexity: clumsyFactorial O(n) for the stack; clumsySimple O(1)

	public static int clumsyFactorial(int n) {
		Deque<Integer> dq = new ArrayDeque<>(); // STACK: signed numbers waiting to be added
		dq.push(n); // PUSH: n has no operator in front of it
		int operation = 0; // HOLD: 0 is *, 1 is /, 2 is +, 3 is -
		for (int num = n - 1; num > 0; num--) { // WALK: n - 1 down to 1
			if (operation == 0) {
				dq.push(num * dq.pop()); // APPLY: * and / apply to the top at once
			} else if (operation == 1) {
				dq.push(dq.pop() / num); // APPLY: truncates toward zero
			} else if (operation == 2) {
				dq.push(num); // WAIT: + waits as a positive number
			} else if (operation == 3) {
				dq.push(-num); // WAIT: - waits as a negative number
			}
			operation = (operation + 1) % 4; // HOLD: *, /, +, - repeat
		}
		int factorial = 0; // SUM: only + is left
		while (!dq.isEmpty()) { // SUM: empty the stack
			factorial += dq.pop(); // SUM: every waiting number, with its sign
		}
		return factorial; // RETURN: the clumsy factorial of n
	}

	public static int clumsySimple(int n) {
		if (n == 1) {
			return 1; // BASE: 1
		}
		if (n == 2) {
			return 2; // BASE: 2 * 1
		}
		if (n == 3) {
			return 6; // BASE: 3 * 2 / 1
		}
		if (n == 4) {
			return 7; // BASE: 4 * 3 / 2 + 1
		}
		if (n % 4 == 0) {
			return n + 1; // RULE: n % 4 == 0
		} else if (n % 4 == 1 || n % 4 == 2) {
			return n + 2; // RULE: n % 4 == 1 or 2
		} else {
			return n - 1; // RULE: n % 4 == 3
		}
	}

	public static void main(String[] args) {
		System.out.println(clumsyFactorial(4)); // 7
		System.out.println(clumsyFactorial(10)); // 12
	}
}

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

public final class ClumsyFactorial {
	// Time Complexity: clumsyFactorial O(n), one step per number; clumsySimple O(1)
	// Space Complexity: clumsyFactorial O(n) for the stack; clumsySimple O(1)

	public static int clumsyFactorial(int n) {
		Deque<Integer> dq = new ArrayDeque<>();
		dq.push(n);
		int operation = 0;
		for (int num = n - 1; num > 0; num--) {
			if (operation == 0) {
				dq.push(num * dq.pop()); // * and / apply to the top at once
			} else if (operation == 1) {
				dq.push(dq.pop() / num);
			} else if (operation == 2) {
				dq.push(num);
			} else if (operation == 3) {
				dq.push(-num); // - waits as a negative number
			}
			operation = (operation + 1) % 4; // *, /, +, - repeat
		}
		int factorial = 0;
		while (!dq.isEmpty()) {
			factorial += dq.pop();
		}
		return factorial;
	}

	public static int clumsySimple(int n) {
		if (n == 1) {
			return 1;
		}
		if (n == 2) {
			return 2;
		}
		if (n == 3) {
			return 6;
		}
		if (n == 4) {
			return 7;
		}
		if (n % 4 == 0) {
			return n + 1;
		} else if (n % 4 == 1 || n % 4 == 2) {
			return n + 2;
		} else {
			return n - 1;
		}
	}

	public static void main(String[] args) {
		System.out.println(clumsyFactorial(4)); // 7
		System.out.println(clumsyFactorial(10)); // 12
	}
}

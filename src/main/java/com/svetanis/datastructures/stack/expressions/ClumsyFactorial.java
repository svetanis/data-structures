package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 1006. Clumsy Factorial

public final class ClumsyFactorial {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int clumsyFactorial(int n) {
		Deque<Integer> dq = new ArrayDeque<>();
		dq.push(n);
		int operation = 0;
		for (int num = n - 1; num > 0; num--) {
			if (operation == 0) {
				dq.push(num * dq.pop());
			} else if (operation == 1) {
				dq.push(dq.pop() / num);
			} else if (operation == 2) {
				dq.push(num);
			} else if (operation == 3) {
				dq.push(-num);
			}
			operation = (operation + 1) % 4;
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

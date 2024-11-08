package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;
import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;

import java.util.ArrayDeque;
import java.util.Deque;

// 227. Basic Calculator II

public final class BasicCalculatorII {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int calculate(String s) {
		int val = 0;
		char operator = '+';
		int n = s.length();
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			char curr = s.charAt(i);
			if (isDigit(curr)) {
				val = val * 10 + parseInt(curr + "");
			}
			if (isOperator(curr) || i == n - 1) {
				switch (operator) {
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
				operator = curr;
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

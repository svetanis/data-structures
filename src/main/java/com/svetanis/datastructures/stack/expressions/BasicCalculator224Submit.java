package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 224. Basic Calculator

public final class BasicCalculator224Submit {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int calculate(String s) {
		int sign = 1;
		int result = 0;
		int current = 0;
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				current = current * 10 + (c - '0');
			} else if (c == '+' || c == '-') {
				result += sign * current;
				sign = c == '+' ? 1 : -1;
				current = 0;
			} else if (c == '(') {
				stack.addLast(result);
				stack.addLast(sign);
				result = 0;
				sign = 1;
			} else if (c == ')') {
				result += sign * current;
				result = stack.pollLast() * result + stack.pollLast();
				current = 0;
			}
		}
		return result + sign * current;
	}

	public static void main(String[] args) {
		System.out.println(calculate("1 + 1")); // 2
		System.out.println(calculate("2 - 1 + 2")); // 3
		System.out.println(calculate("(1 + (4 + 5 + 2) - 3) + (6 + 8)")); // 23
	}
}

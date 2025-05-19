package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 772. Basic Calculator III

public final class BasicCalculatorIII {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int calculate(String s) {
		Deque<Integer> dqd = new ArrayDeque<>();
		Deque<Character> dqc = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') {
				continue;
			}
			if (Character.isDigit(c)) {
				int[] v = values(s, i);
				int val = v[0];
				i = v[1] - 1;
				dqd.push(val);
			} else if (c == '(') {
				dqc.push(c);
			} else if (c == ')') {
				while (dqc.peek() != '(') {
					int b = dqd.pop();
					int a = dqd.pop();
					dqd.push(apply(dqc.pop(), a, b));
				}
				dqc.pop();
			} else if (isOperator(c)) {
				while (!dqc.isEmpty() && dqc.peek() != '(' && precedence(dqc.peek()) >= precedence(c)) {
					int b = dqd.pop();
					int a = dqd.pop();
					dqd.push(apply(dqc.pop(), a, b));
				}
				dqc.push(c);
			}
		}
		while (!dqc.isEmpty()) {
			int b = dqd.pop();
			int a = dqd.pop();
			dqd.push(apply(dqc.pop(), a, b));
		}
		return dqd.peek();
	}

	private static int[] values(String s, int index) {
		int val = 0;
		int i = index;
		while (i < s.length() && Character.isDigit(s.charAt(i))) {
			val = val * 10 + s.charAt(i) - '0';
			i++;
		}
		return new int[] { val, i };
	}

	private static int apply(char operator, int a, int b) {
		switch (operator) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			return a / b;
		default:
			return -1;
		}
	}

	private static int precedence(char c) {
		switch (c) {
		case '(':
		case ')':
			return 0;
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return -1;
		}
	}

	private static boolean isOperator(char c) {
		boolean one = c == '+';
		boolean two = c == '-';
		boolean three = c == '*';
		boolean four = c == '/';
		return one || two || three || four;
	}

	public static void main(String[] args) {
		System.out.println(calculate("6-4/2")); // 4
		System.out.println(calculate("2*(5+5*2)/3+(6/2+8)")); // 21
		System.out.println(calculate("(2+6* 3+5- (3*14/7+2)*5)+3")); // -12
	}
}

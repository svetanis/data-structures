package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 439. Ternary Expression Parser

public final class TernaryExpressionParser {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static String parseTernary(String s) {
		boolean condition = false;
		Deque<Character> dq = new ArrayDeque<>();
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == ':') {
				continue;
			}
			if (c == '?') {
				condition = true;
				continue;
			}
			if (condition) {
				if (c == 'T') {
					char top = dq.pop();
					dq.pop();
					dq.push(top);
				} else {
					dq.pop();
				}
				condition = false;
			} else {
				dq.push(c);
			}
		}
		return dq.peek() + "";
	}

	public static void main(String[] args) {
		System.out.println(parseTernary("T?T?F:5:3")); // F
		System.out.println(parseTernary("F?1:T?4:5")); // 1
	}
}

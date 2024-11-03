package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 1249. Min Remove to Make Valid Parentheses

public final class MinRemoveToBalanceParentheses {
	// Time Complexity: O(n)

	public static String minRemove(String s) {
		// remove invalid closing parentheses
		Deque<Character> stack = removeInvalidClosed(s);
		// remove invalid opening parentheses
		return removeInvalidOpened(stack);
	}

	private static Deque<Character> removeInvalidClosed(String s) {
		int open = 0;
		Deque<Character> stack = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == ')' && open == 0) {
				continue;
			}
			if (c == '(') {
				open++;
			} else if (c == ')') {
				open--;
			}
			stack.push(c);
		}
		return stack;
	}

	private static String removeInvalidOpened(Deque<Character> stack) {
		int close = 0;
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			char c = stack.poll();
			if (c == '(' && close == 0) {
				continue;
			}
			if (c == ')') {
				close++;
			} else if (c == '(') {
				close--;
			}
			sb.append(c);
		}
		return sb.reverse().toString();
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

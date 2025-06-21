package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

public final class BalancedParenthesesSimple {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static boolean valid(String s) {
		int count = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				count++;
			} else if (c == ')') {
				count--;
			}
			if(count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static boolean isBalanced(String s) {
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == '(') {
				dq.push(c);
			} else if (c == ')') {
				if (dq.isEmpty()) {
					return false;
				}
				dq.pop();
			}
		}
		return dq.isEmpty();
	}

	public static void main(String[] args) {
		System.out.println(isBalanced("()")); // true
		System.out.println(isBalanced("()()()")); // true

		System.out.println(isBalanced("(())()"));
		System.out.println(valid("(())()((()())())"));
		System.out.println(valid("))(("));
	}
}

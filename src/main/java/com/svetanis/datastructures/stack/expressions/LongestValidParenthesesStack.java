package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 32. Longest Valid Parentheses

public final class LongestValidParenthesesStack {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lvp(String s) {
		int max = 0;
		Deque<Integer> dq = new ArrayDeque<>();
		dq.push(-1);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				dq.push(i);
			} else if (c == ')') {
				dq.pop();
				if (dq.isEmpty()) {
					dq.push(i);
				} else {
					int current = i - dq.peek();
					max = Math.max(max, current);
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		System.out.println(lvp("")); // 0
		System.out.println(lvp("(()")); // 2
		System.out.println(lvp(")()())")); // 4
		System.out.println(lvp("()(()))))"));// 6 ()(())
	}
}
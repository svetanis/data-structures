package com.svetanis.datastructures.stack.expressions;

// 1541. Minimum Insertions to Balance a Parentheses String

public final class MinInsertionsToBalanceParentheses {
	// Time Complexity: O(n)

	public static int minInsertions(String s) {
		int count = 0;
		int opened = 0;
		int n = s.length();
		for (int index = 0; index < n; index++) {
			char c = s.charAt(index);
			if (c == '(') {
				opened++;
			} else {
				if (index < n - 1 && s.charAt(index + 1) == ')') {
					index++;
				} else {
					count++;
				}
				if (opened == 0) {
					count++;
				} else {
					opened--;
				}
			}
		}
		if (opened > 0) {
			count += 2 * opened;
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(minInsertions("(()))")); // 1
		System.out.println(minInsertions("())")); // 0
		System.out.println(minInsertions("))())(")); // 3
	}
}

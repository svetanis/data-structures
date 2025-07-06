package com.svetanis.datastructures.stack.expressions;

// 856. Score of Parentheses

public final class ScoreOfParentheses {
	// Time complexity: O(n)

	public static int score(String s) {
		int depth = 0;
		int score = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				depth++;
			} else {
				depth--;
				if (i > 0 && s.charAt(i - 1) == '(') {
					score += 1 << depth;
				}
			}
		}
		return score;
	}

	public static void main(String[] args) {
		System.out.println(score("()")); // 1
		System.out.println(score("(())")); // 2
		System.out.println(score("()()")); // 2
	}
}

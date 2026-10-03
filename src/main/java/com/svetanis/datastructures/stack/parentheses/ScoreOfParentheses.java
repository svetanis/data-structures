package com.svetanis.datastructures.stack.parentheses;

// 856. Score of Parentheses
//
// Input: a balanced string of '(' and ')'. Returns its score: each innermost "()" is worth
// 1, doubled once for every pair of brackets around it, and those values are added up.
//
// No stack: only the depth matters. depth is the number of '(' still open. At a ')' right
// after a '(', depth once that pair is closed is the number of pairs around it, so it adds
// 2^depth. Any other ')' adds nothing: its doubling is already in the innermost pairs it holds.

public final class ScoreOfParentheses {
	// Time complexity: O(n), one pass over the characters
	// Space Complexity: O(1), two counters

	public static int score(String s) {
		int depth = 0;
		int score = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				depth++;
			} else {
				depth--;
				if (i > 0 && s.charAt(i - 1) == '(') { // an innermost "()"
					score += 1 << depth; // 1 doubled once per enclosing pair
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

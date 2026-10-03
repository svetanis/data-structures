package com.svetanis.datastructures.stack.parentheses;

// 1541. Minimum Insertions to Balance a Parentheses String
//
// Input: a string of '(' and ')', where each '(' must be closed by two consecutive ')'.
// Returns the fewest '(' or ')' characters to insert to make that true for every bracket.
//
// Two counters, no stack. opened is the number of '(' still waiting for their "))"; count
// is the number of characters inserted so far. A ')' is read together with the next ')'
// when there is one; a lone ')' needs one more ')' inserted, and a ')' pair with no '('
// open needs one '(' inserted. Each '(' still open at the end needs two ')'.

public final class MinInsertionsToBalanceParentheses {
	// Time Complexity: O(n), each character is read once
	// Space Complexity: O(1), two counters

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
					index++; // the next ')' is the second half of this pair
				} else {
					count++; // a lone ')': insert its second ')'
				}
				if (opened == 0) {
					count++; // no '(' open for this pair: insert one
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

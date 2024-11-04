package com.svetanis.datastructures.stack.expressions;

// given a string of parentheses
// find min number of parentheses
// to add to make the resulting
// parentheses string valid

public final class MinNumParenthesesToBalanceExpression {
	// Time Complexity: O(n)

	public static int count(String s) {
		int balance = 0;
		int min = 0;

		for (int i = 0; i < s.length(); i++) {
			balance += s.charAt(i) == '(' ? 1 : -1;
			if (balance == -1) {
				min += 1;
				balance += 1;
			}
		}
		return balance + min;
	}

	public static void main(String[] args) {
		String s1 = "())";
		System.out.println(count(s1)); // 1

		String s2 = "(((";
		System.out.println(count(s2)); // 3
	}
}
package com.svetanis.datastructures.stack.expressions;

// 678. Valid Parenthesis String

public final class ValidParenthesis {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static boolean isValid(String s) {
		int balance = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ')') {
				balance++;
			} else if (balance > 0) {
				balance--;
			} else {
				return false;
			}
		}
		balance = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c != '(') {
				balance++;
			} else if (balance > 0) {
				balance--;
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isValid("()")); // true
		System.out.println(isValid("(*)")); // true
		System.out.println(isValid("(*))")); // true
	}
}

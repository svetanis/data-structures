package com.svetanis.datastructures.stack.parentheses;

// 678. Valid Parenthesis String
//
// Input: a string of '(', ')' and '*'. Returns true when each '*' can be read as '(', ')'
// or nothing so that every '(' is closed by a later ')' and every ')' closes an earlier '('.
//
// Two passes, one counter, no stack. Left to right, balance is the number of '(' and '*'
// read so far that no ')' has used up; a ')' that finds it at 0 can never be closed. Right
// to left, the same with ')' and '*' against each '('. If neither pass fails, some reading
// of the '*' balances the string.

public final class ValidParenthesisString {
	// Time Complexity: O(n), two passes over the characters
	// Space Complexity: O(1), one counter

	public static boolean isValid(String s) {
		int balance = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ')') {
				balance++;
			} else if (balance > 0) {
				balance--;
			} else {
				return false; // a ')' with no '(' or '*' before it left to pair with
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
				return false; // a '(' with no ')' or '*' after it left to pair with
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

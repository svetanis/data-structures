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
//
// Siblings:
//   BalancedParenthesesSimple -- round brackets with no '*': one counter, one pass
//   BalancedParentheses       -- 20: three bracket kinds, so a stack

public final class ValidParenthesisString {
	// Time Complexity: O(n), two passes over the characters
	// Space Complexity: O(1), one counter

	public static boolean isValid(String s) {
		int balance = 0; // COUNT: '(' and '*' that no ')' has used up
		for (int i = 0; i < s.length(); i++) { // WALK: left to right
			char c = s.charAt(i);
			if (c != ')') {
				balance++; // OPEN: a '(' or a '*' that may close a later ')'
			} else if (balance > 0) {
				balance--; // CLOSE: this ')' uses one up
			} else {
				return false; // FAIL: a ')' with no '(' or '*' before it left to pair with
			}
		}
		balance = 0; // COUNT: ')' and '*' that no '(' has used up
		for (int i = s.length() - 1; i >= 0; i--) { // WALK: right to left
			char c = s.charAt(i);
			if (c != '(') {
				balance++; // OPEN: a ')' or a '*' that may close an earlier '('
			} else if (balance > 0) {
				balance--; // CLOSE: this '(' uses one up
			} else {
				return false; // FAIL: a '(' with no ')' or '*' after it left to pair with
			}
		}
		return true; // RETURN: neither direction failed
	}

	public static void main(String[] args) {
		System.out.println(isValid("()")); // true
		System.out.println(isValid("(*)")); // true
		System.out.println(isValid("(*))")); // true
	}
}

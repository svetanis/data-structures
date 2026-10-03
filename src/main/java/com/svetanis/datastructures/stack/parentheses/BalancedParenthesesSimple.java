package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

// Valid parentheses with round brackets only -- the one-bracket-type case of 20. Valid Parentheses
//
// Given a string, returns true when every ')' closes an earlier '(' that is still open and
// no '(' is left open at the end. Only round brackets count; other characters are skipped.
//
// ONE bracket type, so the stack's contents are always "some number of '('" --
// and a single number describes that completely.  Both methods below are correct
// and they agree on every input; the point of the file is the SPACE they use.
// With two or more bracket types the counter dies: it cannot tell "([)]" from
// "([])" because it counts three opens and three closes either way.

public final class BalancedParenthesesSimple {

	// valid():      Time O(n), Space O(1)   -- the counter
	// isBalanced(): Time O(n), Space O(n)   -- the stack, kept for comparison

	public static boolean valid(String s) {
		int count = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				count++;
			} else if (c == ')') {
				count--;
			}
			if (count < 0) {
				return false; // a closer with nothing open. WITHOUT this check ")(" counts
			}                 // down to -1 and back to 0 and is declared valid
		}
		return count == 0; // leftover openers: "(((" never fails inside the loop
	}

	public static boolean isBalanced(String s) {
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == '(') {
				dq.push(c);
			} else if (c == ')') {
				if (dq.isEmpty()) {
					return false; // a ')' with no open '(' to close
				}
				dq.pop();
			}
		}
		return dq.isEmpty();
	}

	public static void main(String[] args) {
		System.out.println(isBalanced("()")); // true
		System.out.println(isBalanced("()()()")); // true

		System.out.println(isBalanced("(())()")); // true
		System.out.println(valid("(())()((()())())")); // true
		System.out.println(valid("))((")); // false -- caught inside the loop

		// the only case the final check catches: every character is individually
		// legal and the string is still unbalanced
		System.out.println(valid("(((")); // false
		System.out.println(isBalanced("(((")); // false
	}
}

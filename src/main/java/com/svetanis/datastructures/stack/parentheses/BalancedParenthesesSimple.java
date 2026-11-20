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
//
// Siblings:
//   BalancedParentheses / BalancedParenthesesMap -- 20 with three bracket types, where only the
//                                                   stack works
//   MaxDepth / MaxDepthStack                     -- the same counter-or-stack pair, returning
//                                                   the deepest nesting instead of true or false
//   MinNumParenthesesToBalanceExpression         -- 921: the same counter, counting what to add

public final class BalancedParenthesesSimple {

	// Time: O(n) -- both methods read every character once
	// Space: valid() O(1) -- the counter; isBalanced() O(n) -- the stack, kept for comparison.
	// valid()'s O(1) is the counting: toCharArray still copies s, O(n) extra space, in both.

	public static boolean valid(String s) {
		int count = 0; // COUNT: '(' open right now
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (c == '(') {
				count++; // OPEN: one more '(' waits
			} else if (c == ')') {
				count--; // CLOSE: it closes one waiting '('
			}
			if (count < 0) {
				return false; // FAIL: a closer with nothing open. WITHOUT this check ")(" counts
			}                 // down to -1 and back to 0 and is declared valid
		}
		return count == 0; // RETURN: leftover openers: "(((" never fails inside the loop
	}

	public static boolean isBalanced(String s) {
		Deque<Character> dq = new ArrayDeque<>(); // STACK: '(' still open
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (c == '(') {
				dq.push(c); // OPEN: one more '(' waits
			} else if (c == ')') {
				if (dq.isEmpty()) {
					return false; // FAIL: a ')' with no open '(' to close
				}
				dq.pop(); // CLOSE: it closes the innermost '('
			}
		}
		return dq.isEmpty(); // RETURN: a '(' left open fails here
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

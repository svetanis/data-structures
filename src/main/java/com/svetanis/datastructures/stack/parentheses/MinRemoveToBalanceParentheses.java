package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

// 1249. Min Remove to Make Valid Parentheses
//
// Input: a string of letters and parentheses. Returns it with the fewest parentheses removed
// so that every '(' is closed by a later ')' and every ')' closes an earlier '('.
//
// Two passes, one counter each. Left to right, a ')' is dropped when no kept '(' before it
// is still open. Right to left, a '(' is dropped when no kept ')' after it is still
// unmatched. The deque carries the kept characters between the passes: push adds at the
// front, so poll reads them back last to first, and the result is reversed at the end.
//
// Siblings:
//   MinRemoveToBalanceParenthesesSet  -- 1249 again: a stack of '(' positions and a set of
//                                        positions to skip, in one pass plus a copy
//   RemoveMinInvalidParenthesisBfs/Dfs -- 301: EVERY string with the fewest removals, not just one
//   MinNumParenthesesToBalanceExpression -- 921: add partners instead of removing

public final class MinRemoveToBalanceParentheses {
	// Time Complexity: O(n), each character is pushed, polled and appended once
	// Space Complexity: O(n), the deque and the builder

	public static String minRemove(String s) {
		// remove invalid closing parentheses
		Deque<Character> stack = removeInvalidClosed(s); // DROP: every ')' with nothing open before it
		// remove invalid opening parentheses
		return removeInvalidOpened(stack); // RETURN: after dropping every '(' that no ')' after it closes
	}

	private static Deque<Character> removeInvalidClosed(String s) {
		int open = 0; // COUNT: kept '(' still open
		Deque<Character> stack = new ArrayDeque<>(); // KEEP: the kept characters, last on top
		for (char c : s.toCharArray()) { // WALK: left to right
			if (c == ')' && open == 0) { // DROP: nothing open before it to close
				continue;
			}
			if (c == '(') {
				open++; // OPEN: one more '(' waits
			} else if (c == ')') {
				open--; // CLOSE: it closes one waiting '('
			}
			stack.push(c); // KEEP: letters always, and every bracket not dropped
		}
		return stack; // RETURN: the kept characters, the last one at the front
	}

	private static String removeInvalidOpened(Deque<Character> stack) {
		int close = 0; // COUNT: kept ')' after this point still unmatched
		StringBuilder sb = new StringBuilder(); // BUILD: the answer, back to front
		while (!stack.isEmpty()) { // WALK: right to left
			char c = stack.poll(); // POP: the front holds the last character kept
			if (c == '(' && close == 0) { // DROP: no unmatched ')' after it to close it
				continue;
			}
			if (c == ')') {
				close++; // WAIT: seen from the right, a ')' waits for its '('
			} else if (c == '(') {
				close--; // CLOSE: it matches one waiting ')'
			}
			sb.append(c); // KEEP: letters always, and every bracket not dropped
		}
		return sb.reverse().toString(); // RETURN: built back to front, so reversed
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

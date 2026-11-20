package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 1249. Min Remove to Make Valid Parentheses
//
// Input: a string of letters and parentheses. Returns it with the fewest parentheses removed
// so that every '(' is closed by a later ')' and every ')' closes an earlier '('.
//
// The stack holds the positions of the '(' not yet closed. A ')' that finds it empty has
// nothing to close, so its position goes into the set to remove; the positions still on
// the stack at the end were never closed and go in too. The answer skips those positions.
//
// Siblings:
//   MinRemoveToBalanceParentheses      -- 1249 again: two counter passes, left to right then
//                                         right to left, with no positions kept
//   RemoveMinInvalidParenthesisBfs/Dfs -- 301: EVERY string with the fewest removals, not just one

public final class MinRemoveToBalanceParenthesesSet {
	// Time Complexity: O(n), one pass to mark and one to copy; set lookups are O(1) on average
	// Space Complexity: O(n), the stack, the set and the builder

	public static String minRemove(String s) {
		Set<Integer> set = indexesToRemove(s); // MARK: the positions to drop
		StringBuilder sb = new StringBuilder(); // BUILD: the answer
		for (int i = 0; i < s.length(); i++) { // WALK: copy every position not marked
			if (!set.contains(i)) {
				sb.append(s.charAt(i)); // KEEP: not marked
			}
		}
		return sb.toString(); // RETURN: s without the marked positions
	}

	private static Set<Integer> indexesToRemove(String s) {
		Set<Integer> set = new HashSet<>(); // MARK: positions to drop
		Deque<Integer> dq = new ArrayDeque<>(); // STACK: positions of '(' not closed yet
		for (int i = 0; i < s.length(); i++) { // WALK: left to right
			char c = s.charAt(i);
			if (c == '(') {
				dq.push(i); // OPEN: it waits for a ')'
			} else if (c == ')') {
				if (dq.isEmpty()) {
					set.add(i); // MARK: a ')' with no '(' open before it
				} else {
					dq.pop(); // CLOSE: it closes the innermost open '('
				}
			}
		}
		while (!dq.isEmpty()) { // WALK: the '(' never closed
			set.add(dq.pop()); // MARK: a '(' that was never closed
		}
		return set; // RETURN: every position to drop
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

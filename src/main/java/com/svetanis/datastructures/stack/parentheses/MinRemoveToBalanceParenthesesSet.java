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

public final class MinRemoveToBalanceParenthesesSet {
	// Time Complexity: O(n), one pass to mark and one to copy; set lookups are O(1) on average
	// Space Complexity: O(n), the stack, the set and the builder

	public static String minRemove(String s) {
		Set<Integer> set = indexesToRemove(s);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (!set.contains(i)) {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	private static Set<Integer> indexesToRemove(String s) {
		Set<Integer> set = new HashSet<>();
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				dq.push(i);
			} else if (c == ')') {
				if (dq.isEmpty()) {
					set.add(i); // a ')' with no '(' open before it
				} else {
					dq.pop();
				}
			}
		}
		while (!dq.isEmpty()) {
			set.add(dq.pop()); // a '(' that was never closed
		}
		return set;
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

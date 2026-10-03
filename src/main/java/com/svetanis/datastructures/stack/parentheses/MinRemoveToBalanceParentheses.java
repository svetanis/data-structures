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

public final class MinRemoveToBalanceParentheses {
	// Time Complexity: O(n), each character is pushed, polled and appended once
	// Space Complexity: O(n), the deque and the builder

	public static String minRemove(String s) {
		// remove invalid closing parentheses
		Deque<Character> stack = removeInvalidClosed(s);
		// remove invalid opening parentheses
		return removeInvalidOpened(stack);
	}

	private static Deque<Character> removeInvalidClosed(String s) {
		int open = 0;
		Deque<Character> stack = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (c == ')' && open == 0) { // nothing open before it to close
				continue;
			}
			if (c == '(') {
				open++;
			} else if (c == ')') {
				open--;
			}
			stack.push(c);
		}
		return stack;
	}

	private static String removeInvalidOpened(Deque<Character> stack) {
		int close = 0;
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			char c = stack.poll(); // the front holds the last character kept
			if (c == '(' && close == 0) { // no unmatched ')' after it to close it
				continue;
			}
			if (c == ')') {
				close++;
			} else if (c == '(') {
				close--;
			}
			sb.append(c);
		}
		return sb.reverse().toString();
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

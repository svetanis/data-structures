package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

// 32. Longest Valid Parentheses
//
// Input: a string of '(' and ')'. Returns the length of the longest substring in which
// every '(' is closed by a later ')' and every ')' closes an earlier '('.
//
// The stack holds positions, not brackets. Its top is the last position a balanced run
// cannot reach back past, so the run ending at i has length i minus the top.

public final class LongestValidParenthesesStack {
	// Time Complexity: O(n), each position is pushed and popped at most once
	// Space Complexity: O(n), the stack can hold every position

	// The stack holds POSITIONS THAT BREAK A VALID RUN -- not open brackets.
	// The top is therefore the last position a run cannot cross, so the run
	// ending at i is i - dq.peek(), with no +1: peek sits BEFORE the run starts.
	//
	// INVARIANT: dq is never empty. push(-1) seeds it with one entry, '(' only
	// adds, and the ')' branch removes one entry but pushes i straight back when
	// that emptied it. Size stays >= 1 at the top of every iteration, which is
	// why the pop below needs no isEmpty guard and cannot throw.

	public static int lvp(String s) {
		int max = 0;
		Deque<Integer> dq = new ArrayDeque<>();
		dq.push(-1); // the position just before the string: "()" gives 1 - (-1) = 2
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				dq.push(i); // an unmatched '(' is itself a breaker
			} else if (c == ')') {
				dq.pop(); // safe by the invariant above; may empty the stack
				if (dq.isEmpty()) {
					dq.push(i); // matched nothing: i is the new breaker, and the invariant is restored
				} else {
					int current = i - dq.peek();
					max = Math.max(max, current);
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		System.out.println(lvp("")); // 0
		System.out.println(lvp("(()")); // 2
		System.out.println(lvp(")()())")); // 4
		System.out.println(lvp("()(()))))"));// 6 ()(())
	}
}
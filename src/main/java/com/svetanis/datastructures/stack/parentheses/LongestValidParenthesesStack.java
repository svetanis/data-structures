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
//
// Siblings:
//   LongestValidParenthesesTwoPointers -- algorithms repo, twopointers: the same answer with two
//                                         counters and two passes, O(1) space and no stack

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
		int max = 0; // ANSWER: the longest run seen so far
		Deque<Integer> dq = new ArrayDeque<>(); // STACK: positions that break a valid run
		dq.push(-1); // START: the position just before the string: "()" gives 1 - (-1) = 2
		for (int i = 0; i < s.length(); i++) { // WALK: one position at a time
			char c = s.charAt(i);
			if (c == '(') {
				dq.push(i); // OPEN: an unmatched '(' is itself a breaker
			} else if (c == ')') {
				dq.pop(); // CLOSE: safe by the invariant above; may empty the stack
				if (dq.isEmpty()) {
					dq.push(i); // BREAK: matched nothing: i is the new breaker, and the invariant is restored
				} else {
					int current = i - dq.peek(); // MEASURE: the run from just after the top to i
					max = Math.max(max, current); // RECORD: keep the longest
				}
			}
		}
		return max; // RETURN: the longest valid run
	}

	public static void main(String[] args) {
		System.out.println(lvp("")); // 0
		System.out.println(lvp("(()")); // 2
		System.out.println(lvp(")()())")); // 4
		System.out.println(lvp("()(()))))"));// 6 ()(())
	}
}
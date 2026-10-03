package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 20. Valid Parentheses
//
// Input: a string of the brackets ( ) [ ] { }. Returns true when every closing bracket
// closes the most recent still-open bracket of the same kind, and none is left open.
//
// The map sends each opening bracket to its closer. The stack holds the opening brackets
// still waiting, innermost on top; a closer is accepted only if it is the one the top expects.

public final class BalancedParenthesesMap {
	// Time Complexity: O(n), each character is pushed and popped at most once
	// Space Complexity: O(n), the stack can hold every character

	// The map collapses two of the three failures into one branch: an empty deque
	// and a wrong closer both fall into the same else, which is why this is the
	// shorter version to write.
	//
	// BUT THE ELSE DECIDES SOMETHING. Anything that is not a map key is treated as
	// a CLOSER, so a letter fails here while BalancedParentheses skips it: "a" is
	// false here and true there. Neither is wrong for LC 20, whose input is
	// brackets only -- read the else before reusing this for input with text in it.

	public static boolean isBalanced(String s) {
		Map<Character, Character> map = parentheses();
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (map.containsKey(c)) {
				dq.push(c); // an opener: cannot be resolved yet, so park it
			} else {
				if (!dq.isEmpty() && map.get(dq.peek()) == c) {
					dq.pop(); // the innermost opener's expected closer arrived
				} else {
					return false; // empty, or the wrong closer
				}
			}
		}
		return dq.isEmpty(); // leftover openers. "(((" fails ONLY here
	}

	private static Map<Character, Character> parentheses() {
		Map<Character, Character> map = new HashMap<>();
		map.put('(', ')');
		map.put('[', ']');
		map.put('{', '}');
		return map;
	}

	public static void main(String[] args) {
		System.out.println(isBalanced("()")); // true
		System.out.println(isBalanced("()[]{}")); // true
		System.out.println(isBalanced("(]")); // false
		System.out.println(isBalanced("([])")); // true

		System.out.println(isBalanced("{()}[]")); // true
		System.out.println(isBalanced("[()]{}{[()()]()}")); // true
		System.out.println(isBalanced("[(])")); // false

		// the case that catches "return true" on the last line
		System.out.println(isBalanced("(((")); // false

		// where this file and BalancedParentheses disagree
		System.out.println(isBalanced("(a)")); // false -- BalancedParentheses says true
	}
}

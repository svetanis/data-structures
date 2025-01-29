package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 20. Valid Parentheses

public final class BalancedParenthesesMap {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static boolean isBalanced(String s) {
		Map<Character, Character> map = parentheses();
		Deque<Character> dq = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (map.containsKey(c)) {
				dq.addLast(c);
			} else {
				if (!dq.isEmpty() && map.get(dq.peekLast()) == c) {
					dq.pollLast();
				} else {
					return false;
				}
			}
		}
		return dq.isEmpty();
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
	}
}

package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 1249. Min Remove to Make Valid Parentheses

public final class MinRemoveToBalanceParenthesesSet {
	// Time Complexity: O(n)

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
					set.add(i);
				} else {
					dq.pop();
				}
			}
		}
		while (!dq.isEmpty()) {
			set.add(dq.pop());
		}
		return set;
	}

	public static void main(String[] args) {
		System.out.println(minRemove("lee(t(c)o)de)")); // lee(t(co)de), lee(t(c)ode)
		System.out.println(minRemove("a)b(c)d")); // ab(c)d
		System.out.println(minRemove("))((")); // ""
	}
}

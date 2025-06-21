package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.java.base.utils.Print;

// 301. Remove Invalid Parentheses

public final class RemoveMinInvalidParenthesisBfs {
	// Time Complexity: O(n * 2^n)
	// Space Complexity: O(n * 2^n)
	
	public static List<String> remove(String s) {
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		set.add(s);
		Queue<String> queue = new ArrayDeque<>();
		queue.offer(s);
		boolean found = false;
		while (!queue.isEmpty()) {
			String node = queue.poll();
			if (valid(node)) {
				list.add(node);
				found = true;
			}
			if (found) {
				continue;
			}
			List<String> substrs = substrs(node);
			for (String substr : substrs) {
				if (!set.contains(substr)) {
					queue.offer(substr);
					set.add(substr);
				}
			}
		}
		return list;
	}

	private static List<String> substrs(String s) {
		int n = s.length();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (isParanthesis(s.charAt(i))) {
				String substr = s.substring(0, i) + s.substring(i + 1);
				list.add(substr);
			}
		}
		return list;
	}

	private static boolean isParanthesis(char c) {
		return c == '(' || c == ')';
	}

	private static boolean valid(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				count++;
			} else if (s.charAt(i) == ')') {
				count--;
			}
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static void main(String[] args) {
		Print.print(remove("()())()")); // "(())()", "()()()"
		Print.print(remove("()v)")); // "(v)", "()v"

		Print.print(remove("()())()")); // "(())()","()()()"
		Print.print(remove("(a)())()")); // "(a())()","(a)()()"
		Print.print(remove(")(")); // ""
		Print.print(remove(")(f")); // "f"
	}
}

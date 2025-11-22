package com.svetanis.datastructures.stack.expressions;

// 1021. Remove Outermost Parentheses

public final class RemoveOutermostParentheses {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static String rop(String s) {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		for (char c : s.toCharArray()) {
			if (c == '(') {
				counter += 1;
				if (counter > 1) {
					sb.append(c);
				}
			} else if (c == ')') {
				counter -= 1;
				if (counter > 0) {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(rop("(()())(())")); // ()()()
		System.out.println(rop("(()())(())(()(()))")); // ()()()()(())
		System.out.println(rop("()()"));
	}
}

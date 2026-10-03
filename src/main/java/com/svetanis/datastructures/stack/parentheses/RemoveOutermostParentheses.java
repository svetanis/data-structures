package com.svetanis.datastructures.stack.parentheses;

// 1021. Remove Outermost Parentheses
//
// Input: a balanced string of '(' and ')', which splits into balanced pieces one after
// another. Returns it with the first '(' and the last ')' of every piece removed.
//
// counter is the number of '(' open after the current character is read. A '(' that makes
// it 1 opens a piece, and a ')' that makes it 0 closes that piece: those two are the
// outermost pair and are skipped. Every other bracket is appended.

public final class RemoveOutermostParentheses {
	// Time Complexity: O(n), one pass over the characters
	// Space Complexity: O(n), the builder and the char array copy

	public static String rop(String s) {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		for (char c : s.toCharArray()) {
			if (c == '(') {
				counter += 1;
				if (counter > 1) { // 1 would mean this '(' opens a piece
					sb.append(c);
				}
			} else if (c == ')') {
				counter -= 1;
				if (counter > 0) { // 0 would mean this ')' closes a piece
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

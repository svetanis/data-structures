package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 921. Minimum Add to Make Parentheses Valid

// given a string of parentheses
// find min number of parentheses
// to add to make the resulting
// parentheses string valid

public final class MinNumParenthesesToBalanceExpression {
	// Time Complexity: O(n)

	public static int count(String s) {
		int min = 0;
		int balance = 0;
		for (char c : s.toCharArray()) {
			balance += c == '(' ? 1 : -1;
			if (balance == -1) {
				min += 1;
				balance += 1;
			}
		}
		return balance + min;
	}

	public static int count2(String s) {
		int open = 0;
		int close = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				open++;
			} else {
				if (open > 0) {
					// match the curr ')' with prev '('
					open--;
				} else {
					// no matching '(' for this ')'
					close++;
				}
			}
		}
		// total unmatched = sum of open and close
		return open + close;
	}

	public static int count3(String s) {
		Deque<Character> dq = new ArrayDeque<>();
		int unmatched = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				dq.push(c);
			} else {
				if (!dq.isEmpty()) {
					dq.pop();
				} else {
					unmatched++;
				}
			}
		}
		return dq.size() + unmatched;
	}

	public static void main(String[] args) {
		String s1 = "())";
		System.out.println(count(s1)); // 1

		String s2 = "(((";
		System.out.println(count(s2)); // 3
	}
}

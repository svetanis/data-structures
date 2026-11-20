package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

// 921. Minimum Add to Make Parentheses Valid

// given a string of parentheses
// find min number of parentheses
// to add to make the resulting
// parentheses string valid
//
// Input: a string of '(' and ')'. Returns the fewest brackets to insert, anywhere, so that
// every '(' is closed by a later ')' and every ')' closes an earlier '('.
//
// Every bracket left unmatched needs exactly one partner inserted, and nothing else needs any:
// a ')' with no '(' open before it needs a '(' added, and a '(' still open at the end needs a
// ')' added. So the answer is the number of unmatched brackets. Three ways to count them:
//   count  -- one balance; when it would go to -1, that ')' gets a '(' at once and balance stays 0
//   count2 -- the same, with the unmatched '(' and the unmatched ')' in two separate counters
//   count3 -- a stack of the open '(' in place of the counter, kept for comparison
//
// Siblings:
//   MinInsertionsToBalanceParentheses -- 1541: each '(' needs two consecutive ')'
//   MinRemoveToBalanceParentheses     -- 1249: remove the unmatched brackets instead of adding partners
//   BalancedParenthesesSimple         -- the same counter, answering only valid or not

public final class MinNumParenthesesToBalanceExpression {
	// Time Complexity: O(n), each method reads every character once
	// Space Complexity: count and count2 O(1) for the counters, count3 O(n) for the stack;
	// toCharArray copies s, O(n), in all three

	public static int count(String s) {
		int min = 0; // ANSWER: '(' inserted for a ')' that had nothing open
		int balance = 0; // COUNT: '(' open right now
		for (char c : s.toCharArray()) { // WALK: one character at a time
			balance += c == '(' ? 1 : -1; // COUNT: '(' opens one, ')' closes one
			if (balance == -1) { // UNMATCHED: a ')' with nothing open
				min += 1; // INSERT: a '(' in front of it
				balance += 1; // CLOSE: which that ')' closes at once
			}
		}
		return balance + min; // RETURN: a ')' for every '(' still open, plus the '(' inserted
	}

	public static int count2(String s) {
		int open = 0; // COUNT: '(' not matched yet
		int close = 0; // COUNT: ')' that nothing before it can match
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (c == '(') {
				open++; // OPEN: it waits for a ')'
			} else {
				if (open > 0) {
					// match the curr ')' with prev '('
					open--; // CLOSE: one waiting '(' is matched
				} else {
					// no matching '(' for this ')'
					close++; // UNMATCHED: it needs a '(' inserted
				}
			}
		}
		// total unmatched = sum of open and close
		return open + close; // RETURN: one inserted bracket per unmatched one
	}

	public static int count3(String s) {
		Deque<Character> dq = new ArrayDeque<>(); // STACK: '(' not matched yet
		int unmatched = 0; // COUNT: ')' that nothing before it can match
		for (char c : s.toCharArray()) { // WALK: one character at a time
			if (c == '(') {
				dq.push(c); // OPEN: it waits for a ')'
			} else {
				if (!dq.isEmpty()) {
					dq.pop(); // CLOSE: one waiting '(' is matched
				} else {
					unmatched++; // UNMATCHED: it needs a '(' inserted
				}
			}
		}
		return dq.size() + unmatched; // RETURN: one inserted bracket per unmatched one
	}

	public static void main(String[] args) {
		String s1 = "())";
		System.out.println(count(s1)); // 1

		String s2 = "(((";
		System.out.println(count(s2)); // 3
	}
}

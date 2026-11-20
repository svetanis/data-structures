package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// 301. Remove Invalid Parentheses
//
// Input: a string of parentheses and letters. Returns every distinct string made by removing
// the fewest parentheses so that each '(' is closed by a later ')' and each ')' closes an
// earlier '('.
//
// Breadth-first search over strings: level k holds the strings left after removing k
// parentheses, each distinct string queued once. The first level with a valid string is
// the minimum number of removals, so that level is collected and nothing goes deeper.
// The cost is exponential: every string queued is the input with some parentheses removed,
// one string per choice of which to remove, so at most 2^n strings are ever queued.
//
// Siblings:
//   RemoveMinInvalidParenthesisDfs    -- 301 again: counts first how many '(' and ')' must go,
//                                        then searches only removals of exactly that many
//   MinRemoveToBalanceParentheses(Set) -- 1249: ONE string with the fewest removals, in O(n)

public final class RemoveMinInvalidParenthesisBfs {
	// Time Complexity: O(n^2 * 2^n), up to 2^n strings, each expanded into n copies of length n
	// Space Complexity: O(n * 2^n), the set and the queue hold up to 2^n strings of length n

	public static List<String> remove(String s) {
		List<String> list = new ArrayList<>(); // ANSWER: the valid strings of the first valid level
		Set<String> set = new HashSet<>(); // SEEN: every string ever queued
		set.add(s); // SEEN: level 0 is s itself
		Queue<String> queue = new ArrayDeque<>(); // QUEUE: strings waiting, level by level
		queue.offer(s); // START: nothing removed yet
		boolean found = false; // STOP: true once a level holds a valid string
		while (!queue.isEmpty()) { // WALK: level by level
			String node = queue.poll(); // TAKE: the next string, fewest removals first
			if (valid(node)) {
				list.add(node); // RECORD: valid with the fewest removals
				found = true; // STOP: go no deeper
			}
			if (found) { // STOP: this level has a valid string: never remove one more
				continue;
			}
			List<String> substrs = substrs(node); // EXPAND: one more parenthesis removed, every way
			for (String substr : substrs) { // WALK: each string one removal further
				if (!set.contains(substr)) { // SEEN: different removals can give the same string
					queue.offer(substr); // QUEUE: the next level
					set.add(substr); // SEEN: never queued twice
				}
			}
		}
		return list; // RETURN: every distinct answer
	}

	private static List<String> substrs(String s) {
		int n = s.length();
		List<String> list = new ArrayList<>(); // EXPAND: s with one parenthesis removed
		for (int i = 0; i < n; i++) { // WALK: every position
			if (isParanthesis(s.charAt(i))) {
				String substr = s.substring(0, i) + s.substring(i + 1); // REMOVE: the parenthesis at i
				list.add(substr); // RECORD: one candidate
			}
		}
		return list; // RETURN: one string per parenthesis in s
	}

	private static boolean isParanthesis(char c) {
		return c == '(' || c == ')'; // TEST: a letter is never removed
	}

	private static boolean valid(String s) {
		int count = 0; // COUNT: '(' open right now
		for (int i = 0; i < s.length(); i++) { // WALK: one character at a time
			if (s.charAt(i) == '(') {
				count++; // OPEN: one more '(' waits
			} else if (s.charAt(i) == ')') {
				count--; // CLOSE: it closes one waiting '('
			}
			if (count < 0) {
				return false; // FAIL: a ')' with nothing open
			}
		}
		return count == 0; // RETURN: no '(' left open
	}

	public static void main(String[] args) {
		System.out.println(remove("()())()")); // "(())()", "()()()"
		System.out.println(remove("()v)")); // "(v)", "()v"

		System.out.println(remove("()())()")); // "(())()","()()()"
		System.out.println(remove("(a)())()")); // "(a())()","(a)()()"
		System.out.println(remove(")(")); // "" -- printed as [], a list holding one empty string
		System.out.println(remove(")(f")); // "f"
	}
}

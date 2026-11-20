package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 301. Remove Invalid Parentheses
//
// Input: a string of parentheses and letters. Returns every distinct string made by removing
// the fewest parentheses so that each '(' is closed by a later ')' and each ')' closes an
// earlier '('.
//
// A first pass counts exactly how many '(' and ')' must go: each ')' with no '(' open before
// it, and each '(' still open at the end. The search then walks the string and at each
// parenthesis either removes it, while that count allows, or keeps it. lr and rr are the
// '(' and ')' still to remove; lc and rc are the '(' and ')' kept so far. The cost is
// exponential: at most 2^n keep-or-remove paths, each copying the kept prefix at every step.
//
// Siblings:
//   RemoveMinInvalidParenthesisBfs    -- 301 again: breadth-first over strings, one removal per
//                                        level, stopping at the first level with a valid string
//   MinRemoveToBalanceParentheses(Set) -- 1249: ONE string with the fewest removals, in O(n)

public final class RemoveMinInvalidParenthesisDfs {
	// Time Complexity: O(n * 2^n), at most 2^n paths, and each call copies a string of length n
	// Space Complexity: O(n * 2^n), the result set can hold up to 2^n strings of length n

	private int len;
	private String s;
	private Set<String> set;

	public List<String> remove(String s) {
		this.s = s;
		this.len = s.length();
		this.set = new HashSet<>(); // ANSWER: distinct strings, since two paths can give the same one
		int left = 0, right = 0; // COUNT: '(' and ')' that must be removed
		for (char c : s.toCharArray()) { // WALK: the counting pass
			if (c == '(') {
				left++; // OPEN: it must go unless a ')' closes it
			} else if (c == ')') {
				if (left > 0) {
					left--; // CLOSE: it closes one waiting '('
				} else {
					right++; // REMOVE: a ')' with nothing open must go
				}
			}
		}
		dfs(0, left, right, 0, 0, ""); // START: at position 0, nothing kept yet
		return new ArrayList<>(set); // RETURN: every distinct answer
	}

	private void dfs(int index, int lr, int rr, int lc, int rc, String curr) {
		if (index == len) {
			if (lr == 0 && rr == 0) { // RECORD: every required removal made, which also leaves lc == rc
				set.add(curr);
			}
			return;
		}
		if (len - index < lr + rr || lc < rc) { // STOP: too few left, or more ')' than '(' kept
			return;
		}
		char c = s.charAt(index);
		if (c == '(' && lr > 0) {
			dfs(index + 1, lr - 1, rr, lc, rc, curr); // REMOVE: this '('
		}
		if (c == ')' && rr > 0) {
			dfs(index + 1, lr, rr - 1, lc, rc, curr); // REMOVE: this ')'
		}
		int dl = c == '(' ? 1 : 0; // COUNT: one more '(' kept, if c is one
		int dr = c == ')' ? 1 : 0; // COUNT: one more ')' kept, if c is one
		dfs(index + 1, lr, rr, lc + dl, rc + dr, curr + c); // KEEP: c, letters included
	}

	public static void main(String[] args) {
		RemoveMinInvalidParenthesisDfs rmp = new RemoveMinInvalidParenthesisDfs();
		System.out.println(rmp.remove("()())()")); // "(())()","()()()"
		RemoveMinInvalidParenthesisDfs rmp2 = new RemoveMinInvalidParenthesisDfs();
		System.out.println(rmp2.remove("(a)())()")); // "(a())()","(a)()()"
		RemoveMinInvalidParenthesisDfs rmp3 = new RemoveMinInvalidParenthesisDfs();
		System.out.println(rmp3.remove(")(")); // "" -- printed as [], a list holding one empty string
		RemoveMinInvalidParenthesisDfs rmp4 = new RemoveMinInvalidParenthesisDfs();
		System.out.println(rmp4.remove(")(f")); // "f"
	}
}

package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.java.base.utils.Print;

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

public final class RemoveMinInvalidParenthesisDfs {
	// Time Complexity: O(n * 2^n), at most 2^n paths, and each call copies a string of length n
	// Space Complexity: O(n * 2^n), the result set can hold up to 2^n strings of length n

	private int len;
	private String s;
	private Set<String> set;

	public List<String> remove(String s) {
		this.s = s;
		this.len = s.length();
		this.set = new HashSet<>();
		int left = 0, right = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				left++;
			} else if (c == ')') {
				if (left > 0) {
					left--;
				} else {
					right++;
				}
			}
		}
		dfs(0, left, right, 0, 0, "");
		return new ArrayList<>(set);
	}

	private void dfs(int index, int lr, int rr, int lc, int rc, String curr) {
		if (index == len) {
			if (lr == 0 && rr == 0) { // every required removal made, which also leaves lc == rc
				set.add(curr);
			}
			return;
		}
		if (len - index < lr + rr || lc < rc) { // too few left, or more ')' than '(' kept
			return;
		}
		char c = s.charAt(index);
		if (c == '(' && lr > 0) {
			dfs(index + 1, lr - 1, rr, lc, rc, curr);
		}
		if (c == ')' && rr > 0) {
			dfs(index + 1, lr, rr - 1, lc, rc, curr);
		}
		int dl = c == '(' ? 1 : 0;
		int dr = c == ')' ? 1 : 0;
		dfs(index + 1, lr, rr, lc + dl, rc + dr, curr + c);
	}

	public static void main(String[] args) {
		RemoveMinInvalidParenthesisDfs rmp = new RemoveMinInvalidParenthesisDfs();
		Print.print(rmp.remove("()())()")); // "(())()","()()()"
		RemoveMinInvalidParenthesisDfs rmp2 = new RemoveMinInvalidParenthesisDfs();
		Print.print(rmp2.remove("(a)())()")); // "(a())()","(a)()()"
		RemoveMinInvalidParenthesisDfs rmp3 = new RemoveMinInvalidParenthesisDfs();
		Print.print(rmp3.remove(")(")); // ""
		RemoveMinInvalidParenthesisDfs rmp4 = new RemoveMinInvalidParenthesisDfs();
		Print.print(rmp4.remove(")(f")); // "f"
	}
}

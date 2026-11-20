package com.svetanis.datastructures.stack.parentheses;

// 1963. Minimum Number of Swaps to Make the String Balanced
//
// Input: a string with equal numbers of '[' and ']'. Returns the fewest swaps of two
// characters that make every '[' closed by a later ']' and every ']' close an earlier '['.
//
// One counter, no stack: count is the number of '[' read so far that no ']' has closed.
// A ']' closes one when count > 0 and is left unmatched otherwise. Without the matched
// pairs, the string is m unmatched ']' followed by m unmatched '[', m being count at the
// end. Swapping the first unmatched ']' with the last unmatched '[' lowers m by two (to
// zero when m is 1), so the answer is m / 2 rounded up.
//
// Siblings:
//   MinNumParenthesesToBalanceExpression -- 921: the same count of unmatched brackets, but each
//                                           one costs an insertion instead of half a swap
//   MinInsertionsToBalanceParentheses    -- 1541: insertions, with each '(' needing "))"

public final class MinSwapsToBalanceStr {
	// Time complexity: O(n), one pass over the characters
	// Space Complexity: O(1), one counter

	public static int minSwaps(String s) {
		int count = 0; // COUNT: '[' read so far that no ']' has closed
		for (int i = 0; i < s.length(); i++) { // WALK: one character at a time
			char c = s.charAt(i);
			if (c == '[') {
				count++; // OPEN: it waits for a ']'
			} else if (count > 0) {
				count--; // CLOSE: this ']' closes one waiting '['
			}
		}
		return (count + 1) >> 1; // RETURN: m / 2 rounded up
	}

	public static void main(String[] args) {
		System.out.println(minSwaps("][][")); // 1
		System.out.println(minSwaps("]]][[[")); // 2
		System.out.println(minSwaps("[]")); // 0
	}
}

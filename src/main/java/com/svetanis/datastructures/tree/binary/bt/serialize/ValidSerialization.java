package com.svetanis.datastructures.tree.binary.bt.serialize;

import java.util.ArrayList;
import java.util.List;

// 331. Verify Preorder Serialization of a Binary Tree
//
// Input: a string of comma-separated tokens, each an integer or "#" (a missing child), meant to be
// a pre-order serialization of a binary tree. On LeetCode: 1 <= length <= 10^4, integers are
// 0 to 100, and the string is never empty or malformed in format.
// Returns: true if some binary tree has exactly this serialization, false otherwise. The tree
// itself is never built.
//
// The one idea (isValid): count the open slots, the places where the next token may go. At the
// start there is one slot, for the root. Every token fills one slot. A number then opens two new
// slots (its left and right child); a "#" opens none. So the count goes down by one for each token
// and up by two for each number. The text is a complete tree exactly when a token never arrives
// with no free slot (count would go below 0) and the last token fills the last slot (count is 0
// at the end).
//
// The one idea (valid): reduce the text the way a tree finishes. Whenever the last three tokens
// are "number, #, #", that number is a finished leaf, so the three tokens become one "#" -- a
// finished subtree stands in the place of a missing child for its parent. A valid text collapses
// to a single "#".
//
// Time: O(n) for both -- isValid reads each token once; valid pushes each token once and each
// collapse removes net two tokens, so there are at most n/2 collapses.
// Space: O(n) -- the split tokens, and for valid the token list as well.
//
// Siblings: none in the repo; the two methods here answer the same question by different counting
// (open slots) and reducing (collapsing leaves).

public final class ValidSerialization {

	private static final String DEL = "#";

	public static boolean isValid(String s) {
		int count = 1; // SLOTS: free places for the next token; one, for the root
		String[] nodes = s.split(",");
		for (String node : nodes) {
			count--; // FILL: this token takes a slot
			if (count < 0) { // no free slot: extra tokens
				return false;
			}
			if (!node.equals(DEL)) { // a number opens its two child slots
				count += 2;
			}
		}
		return count == 0; // every slot filled
	}

	public static boolean valid(String s) {
		List<String> list = new ArrayList<>();
		String[] nodes = s.split(",");
		for (String node : nodes) {
			list.add(node); // PUSH: the next token
			while (list.size() >= 3
					&& list.get(list.size() - 1).equals(DEL)
					&& list.get(list.size() - 2).equals(DEL)
					&& !list.get(list.size() - 3).equals(DEL)) { // COLLAPSE: "number # #" is a finished leaf
				list.remove(list.size() - 1);
				list.remove(list.size() - 1);
				list.remove(list.size() - 1);
				list.add(DEL); // the finished subtree stands as one "#"
			}
		}
		return list.size() == 1 && list.get(0).equals(DEL);
	}

	public static void main(String[] args) {
		System.out.println(valid("9,3,4,#,#,1,#,#,2,#,6,#,#")); // true
		System.out.println(valid("1,#")); // false
		System.out.println(valid("9,#,#,1")); // false
		System.out.println(isValid("9,3,4,#,#,1,#,#,2,#,6,#,#")); // true
		System.out.println(isValid("1,#")); // false
		System.out.println(isValid("9,#,#,1")); // false
	}
}

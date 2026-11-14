package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 536. Construct Binary Tree from String
//
// Input: a string s made of digits, '-', '(' and ')'. A node is its integer value followed by up
// to two parenthesized groups: the first group is the left child, the second the right child; a
// node with only a right child writes an empty first group "()". On LeetCode:
// 0 <= s.length <= 3 * 10^4, and s is always a valid encoding.
// Returns: the root of the tree s describes, or null for the empty string.
//
// The one idea: the text before the first '(' is the node's own value. After it come one or two
// groups, each starting at a '(' and ending at the ')' that balances it. Counting '(' as +1 and
// ')' as -1, the count returns to 0 exactly where a group closes, so the first return to 0 ends
// the left group and the second ends the right group. Everything strictly inside a group is
// itself an encoding of a subtree, so the same method is called on it.
//
// Time: O(n^2) worst case -- each call scans its text and copies each group with substring; on a
// chain of single-child nodes the texts have lengths n, n-3, n-6, ...
// Space: O(n^2) worst case -- on that chain every level's copied substring is still alive during
// the recursion; O(n) for a balanced tree.
//
// Known limit: the recursion is as deep as the tree is tall. A left-leaning chain of 9,999 nodes
// (a 29,995-character string, inside LeetCode's limit) overflows the default stack; this only
// happens on very large input.
//
// Siblings: ConstructBTFromStringIterative is the same method with an explicit stack.

public final class ConstructBTFromString {

	public static TreeNode construct(String s) {
		if ("".equals(s)) { // BASE: empty text, no node
			return null;
		}
		int lpi = s.indexOf('(');
		if (lpi == -1) { // BASE: only a number, a leaf
			return new TreeNode(Integer.parseInt(s));
		}
		TreeNode root = new TreeNode(Integer.parseInt(s.substring(0, lpi))); // VALUE: text before the first '('
		int count = 0;
		int start = lpi;
		for (int i = lpi; i < s.length(); i++) {
			if (s.charAt(i) == '(') { // OPEN: one level deeper
				count++;
			} else if (s.charAt(i) == ')') { // CLOSE: one level up
				count--;
			}

			if (count == 0) { // a group just closed
				if (start == lpi) {
					root.left = construct(s.substring(start + 1, i)); // LEFT: first group, brackets stripped
					start = i + 1;
				} else {
					root.right = construct(s.substring(start + 1, i)); // RIGHT: second group
				}
			}
		}
		return root;
	}

	public static void main(String[] args) {
		System.out.println(traverse(construct("4(2(3)(1))(6(5))"))); // [4, 2, 6, 3, 1, 5]
		System.out.println(traverse(construct("4(2(3)(1))(6(5)(7))"))); // [4, 2, 6, 3, 1, 5, 7]
		System.out.println(traverse(construct("-4(2(3)(1))(6(5)(7))"))); // [-4, 2, 6, 3, 1, 5, 7]
	}
}

package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 536. Construct Binary Tree from String, without recursion
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
// itself an encoding of a subtree. This file keeps those pieces on a stack: one entry holds a node
// whose value is already read, and the text after its value (its groups) still to be split. Popping
// an entry splits its groups, creates the child nodes, and pushes one entry per non-empty group.
//
// Time: O(n^2) worst case -- each entry scans its text and copies each group with substring; on a
// chain of single-child nodes the texts have lengths n, n-3, n-6, ...
// Space: O(n) -- an entry's text is dropped once it is split, so only the pieces still to be
// split are alive.
//
// Siblings: ConstructBTFromString is the same method with recursion; it overflows the default
// stack on a left-leaning chain of 9,999 nodes, this file does not.

public final class ConstructBTFromStringIterative {

	private record Pending(TreeNode node, String groups) {
	}

	public static TreeNode construct(String s) {
		Deque<Pending> stack = new ArrayDeque<>();
		TreeNode root = open(s, stack); // ROOT: value read, groups pushed
		while (!stack.isEmpty()) { // WALK: one pending node per pop
			Pending pending = stack.pop();
			String text = pending.groups();
			int count = 0;
			int start = 0; // the groups begin at index 0, which is a '('
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) == '(') { // OPEN: one level deeper
					count++;
				} else if (text.charAt(i) == ')') { // CLOSE: one level up
					count--;
				}

				if (count == 0) { // a group just closed
					String inner = text.substring(start + 1, i); // brackets stripped
					if (start == 0) {
						pending.node().left = open(inner, stack); // LEFT: first group
						start = i + 1;
					} else {
						pending.node().right = open(inner, stack); // RIGHT: second group
					}
				}
			}
		}
		return root;
	}

	// reads the value in front of the first '(' into a new node; the groups after it go on the stack
	private static TreeNode open(String s, Deque<Pending> stack) {
		if ("".equals(s)) { // BASE: empty text, no node
			return null;
		}
		int lpi = s.indexOf('(');
		if (lpi == -1) { // BASE: only a number, a leaf
			return new TreeNode(Integer.parseInt(s));
		}
		TreeNode node = new TreeNode(Integer.parseInt(s.substring(0, lpi))); // VALUE: text before the first '('
		stack.push(new Pending(node, s.substring(lpi))); // PUSH: the groups, still to be split
		return node;
	}

	public static void main(String[] args) {
		System.out.println(traverse(construct("4(2(3)(1))(6(5))"))); // [4, 2, 6, 3, 1, 5]
		System.out.println(traverse(construct("4(2(3)(1))(6(5)(7))"))); // [4, 2, 6, 3, 1, 5, 7]
		System.out.println(traverse(construct("-4(2(3)(1))(6(5)(7))"))); // [-4, 2, 6, 3, 1, 5, 7]
	}
}

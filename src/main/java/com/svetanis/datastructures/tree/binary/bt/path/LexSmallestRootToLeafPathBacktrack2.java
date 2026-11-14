package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 988. Smallest String Starting From Leaf
//
// Input: the root of a binary tree whose values are 0 to 25, standing for the letters 'a' to 'z'.
// Returns the lexicographically smallest string that starts at a leaf and ends at the root.
// A shorter string that is a prefix of a longer one is the smaller of the two. On LeetCode:
// 1 <= n <= 8500, 0 <= val <= 25.
//
// The one idea: the string is read from the leaf up, so build it that way: each node inserts its
// letter at the FRONT of a StringBuilder. At a leaf the builder already reads leaf to root, so
// it is compared as it is, with no reversing. Going back up deletes the front letter. The best
// starts as null, meaning "no leaf seen yet".
//
// Time: O(n * h + L * h) -- inserting or deleting at the front shifts up to h letters (h =
//   height), and each of the L leaves copies a string of up to h letters; O(n^2) on a chain.
// Space: O(h) -- the builder and the recursion stack.
//
// Known limit: a chain about 8500 nodes deep (the most the constraints allow) overflows the
// default thread stack, because the recursion is as deep as the tree. It only happens on very
// large input.
//
// Siblings: LexSmallestRootToLeafPathBacktrack appends at the back and reverses a copy at each
// leaf; LexSmallestRootToLeafPathBacktrackIterative is that version with an explicit stack.
public final class LexSmallestRootToLeafPathBacktrack2 {

	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	private String min;

	public String minPath(TreeNode root) {
		this.min = null; // BEST: no leaf seen yet
		dfs(root, new StringBuilder());
		return min;
	}

	private void dfs(TreeNode node, StringBuilder sb) {
		if (node == null) { // BASE: nothing below
			return;
		}
		sb.insert(0, LETTERS.charAt(node.val)); // TAKE: the current letter goes to the front
		if (node.left == null && node.right == null) { // LEAF: the builder reads leaf to root
			String s = sb.toString();
			if (min == null || s.compareTo(min) < 0) { // RECORD: a smaller string
				min = s;
			}
		}
		dfs(node.left, sb); // WALK: left subtree
		dfs(node.right, sb); // WALK: right subtree
		sb.deleteCharAt(0); // UNDO: leave the node
	}

	public static void main(String[] args) {
		TreeNode root = newNode(0);
		root.left = newNode(1);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.left = newNode(3);
		root.right.right = newNode(4);
		inOrder(root); // prints 3 1 4 0 3 2 4
		System.out.println();
		LexSmallestRootToLeafPathBacktrack2 lsp = new LexSmallestRootToLeafPathBacktrack2();
		System.out.println(lsp.minPath(root)); // dba

		// LeetCode example 2
		TreeNode root2 = newNode(25);
		root2.left = newNode(1);
		root2.right = newNode(3);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right.left = newNode(0);
		root2.right.right = newNode(2);
		System.out.println(lsp.minPath(root2)); // adz

		// LeetCode example 3
		TreeNode root3 = newNode(2);
		root3.left = newNode(2);
		root3.right = newNode(1);
		root3.left.right = newNode(1);
		root3.right.left = newNode(0);
		root3.left.right.left = newNode(0);
		System.out.println(lsp.minPath(root3)); // abc
	}
}

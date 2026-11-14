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
// The one idea: the string is read from the leaf up, but the walk goes from the root down. So
// keep the letters from the root down to the current node in a StringBuilder; at a leaf, reverse
// a copy to get the leaf-to-root string and keep it if it is smaller than the best so far. Going
// back up deletes the last letter. The best starts as "~", which is larger than every lower-case
// letter, so the first leaf always replaces it.
//
// Time: O(n + L * h) -- each node is visited once, and each of the L leaves copies and reverses
//   a string of up to h letters (h = height), so O(n^2) in the worst case.
// Space: O(h) -- the builder and the recursion stack.
//
// Known limit: a chain about 8500 nodes deep (the most the constraints allow) overflows the
// default thread stack, because the recursion is as deep as the tree. It only happens on very
// large input.
//
// Siblings: LexSmallestRootToLeafPathBacktrack2 builds the string leaf-first by inserting each
// letter at the front; LexSmallestRootToLeafPathBacktrackIterative is this walk with an explicit
// stack.
public final class LexSmallestRootToLeafPathBacktrack {

	private String min;

	public String minPath(TreeNode root) {
		this.min = "~"; // BEST: larger than any string of letters 'a' to 'z'
		dfs(root, new StringBuilder());
		return min;
	}

	private void dfs(TreeNode root, StringBuilder sb) {
		if (root == null) { // BASE: nothing below
			return;
		}
		char c = (char) (root.val + 'a');
		sb.append(c); // TAKE: the current letter joins the path
		if (root.left == null && root.right == null) { // LEAF: the path is complete
			sb.reverse(); // FLIP: leaf first
			String s = sb.toString();
			sb.reverse(); // FLIP BACK: root first again
			if (s.compareTo(min) < 0) { // RECORD: a smaller string
				min = s;
			}
		}
		dfs(root.left, sb); // WALK: left subtree
		dfs(root.right, sb); // WALK: right subtree
		sb.deleteCharAt(sb.length() - 1); // UNDO: leave the node
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
		LexSmallestRootToLeafPathBacktrack lsp = new LexSmallestRootToLeafPathBacktrack();
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

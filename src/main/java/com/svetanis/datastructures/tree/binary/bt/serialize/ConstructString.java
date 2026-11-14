package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 606. Construct String from Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 1 to 10^4 nodes, -1000 <= val <= 1000.
// Returns: the pre-order text of the tree: a node's value, then its left subtree in parentheses,
// then its right subtree in parentheses. Empty parentheses are left out, except when a node has a
// right child but no left child: then "()" must stand for the missing left child, or the reader
// could not tell the right child from a left one.
//
// The one idea: a node's text is its value followed by the text of each child in brackets, so
// build it with the same method on the children. The only care is which brackets to drop: a missing
// right child needs none (nothing follows it), a missing left child needs "()" only when a right
// child follows. Leaves print their value alone.
//
// Time: treeToStr O(n) -- one append per node and bracket on a shared StringBuilder; tree2str
// O(n * h) -- each level builds a new string by copying its children's strings, so a chain copies
// n, n-1, ... characters (O(n^2) on a chain).
// Space: O(n) for the text, plus the recursion (as deep as the tree is tall, up to n).
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 3,000 nodes (tree2str)
// or 7,000 nodes (treeToStr), inside LeetCode's limit of 10^4, overflows the default stack; this
// only happens on very large input.
//
// Siblings: ConstructStringIterative is the same method with an explicit stack. The two public
// methods here give the same answer: treeToStr appends to one StringBuilder; tree2str returns and
// concatenates strings.

public final class ConstructString {

	public static String treeToStr(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		treeToStr(root, sb);
		return sb.toString();
	}

	private static void treeToStr(TreeNode root, StringBuilder sb) {
		if (root == null) { // BASE: no text for a missing node
			return;
		}
		sb.append(root.val + ""); // VALUE: the node itself
		if (root.left != null) {
			sb.append("("); // LEFT: its text in brackets
			treeToStr(root.left, sb);
			sb.append(")");
		} else if (root.left == null && root.right != null) {
			sb.append("()"); // EMPTY LEFT: kept only because a right child follows
		}
		if (root.right != null) {
			sb.append("("); // RIGHT: its text in brackets
			treeToStr(root.right, sb);
			sb.append(")");
		}
	}

	public static String tree2str(TreeNode root) {
		if (root == null) { // BASE: no text for a missing node
			return "";
		}
		if (root.left == null && root.right == null) { // LEAF: value alone
			return root.val + "";
		}
		if (root.right == null) { // ONLY LEFT: no second brackets
			return root.val + "(" + tree2str(root.left) + ")";
		}
		return root.val + "(" + tree2str(root.left) + ")(" + tree2str(root.right) + ")"; // BOTH: an empty left gives "()"
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		System.out.println(tree2str(root)); // 1(2(4))(3)
		System.out.println(treeToStr(root)); // 1(2(4))(3)

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(4);
		System.out.println(tree2str(root1)); // 1(2()(4))(3)
		System.out.println(treeToStr(root1)); // 1(2()(4))(3)
	}
}

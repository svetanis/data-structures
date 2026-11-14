package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 606. Construct String from Binary Tree, without recursion
//
// Input: the root of a binary tree. On LeetCode: 1 to 10^4 nodes, -1000 <= val <= 1000.
// Returns: the pre-order text of the tree: a node's value, then its left subtree in parentheses,
// then its right subtree in parentheses. Empty parentheses are left out, except when a node has a
// right child but no left child: then "()" must stand for the missing left child, or the reader
// could not tell the right child from a left one.
//
// The one idea: a node's text is its value followed by the text of each child in brackets. This
// file keeps the work still to do on a stack, where an entry is either a node whose text is still
// to be written or one of two marks, OPEN ("(") and CLOSE (")"). Popping a node writes its value
// and pushes, in reverse order, what must follow: for the left child OPEN, the child, CLOSE (or
// OPEN, CLOSE for an empty left that a right child follows), then the same three for the right
// child. Popping a mark writes the bracket. The stack pops them back in text order.
//
// Time: O(n) -- each node and each bracket is pushed once, popped once and appended once.
// Space: O(n) for the text, plus a stack of at most about 3 entries per level of the tree.
//
// Siblings: ConstructString is the same method with recursion; it overflows the default stack on a
// chain of about 3,000 nodes (tree2str) or 7,000 nodes (treeToStr), this file does not.

public final class ConstructStringIterative {

	private static final TreeNode OPEN = new TreeNode(0); // marks: compared by identity, never printed
	private static final TreeNode CLOSE = new TreeNode(0);

	public static String treeToStr(TreeNode root) {
		return tree2str(root);
	}

	public static String tree2str(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) { // WALK: text order, one entry per pop
			TreeNode node = stack.pop();
			if (node == OPEN) { // MARK: bracket
				sb.append("(");
			} else if (node == CLOSE) {
				sb.append(")");
			} else {
				sb.append(node.val); // VALUE: the node itself
				pushChild(stack, node.right, false); // RIGHT: pushed first, written last
				pushChild(stack, node.left, node.right != null); // LEFT: pushed last, written first
			}
		}
		return sb.toString();
	}

	// pushes ")", the child, "(" so that they pop as "(", child, ")"; a missing child gets "()" only
	// when keepEmpty says a right child follows
	private static void pushChild(Deque<TreeNode> stack, TreeNode child, boolean keepEmpty) {
		if (child == null && !keepEmpty) { // SKIP: no brackets at all
			return;
		}
		stack.push(CLOSE);
		if (child != null) {
			stack.push(child);
		}
		stack.push(OPEN);
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

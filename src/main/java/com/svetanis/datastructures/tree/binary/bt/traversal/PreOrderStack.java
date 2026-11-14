package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 144. Binary Tree Preorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Returns the values in preorder: the
// node, then the whole left subtree, then the whole right subtree. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: the stack holds the subtrees still to be walked, the next one on top. Pop a node and
// report it, then put its two children on the stack for later. The right child is pushed first so
// that the left child is on top and is popped first -- the whole left subtree is then finished
// before the right child is reached, because anything pushed after the right child sits above it.
//
// Time: O(n) -- every node is pushed once and popped once.
// Space: O(h) -- at most one waiting right child per level of the current chain; h is lg n on a
// balanced tree and n on a skewed one.
//
// Siblings: PreOrderRecursive prints the same order with the call stack; InOrderStack and
// PostOrderTwoStacks are the other two orders on explicit stacks.

public final class PreOrderStack {

	public static List<Integer> preOrder(TreeNode root) {
		if (root == null) { // BASE: empty tree
			return new ArrayList<>();
		}
		List<Integer> list = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode node = stack.pop(); // TAKE: the next subtree to walk
			list.add(node.val); // RECORD: the node before its children
			if (node.right != null) { // PUSH RIGHT first, so it waits under the left
				stack.push(node.right);
			}
			if (node.left != null) { // PUSH LEFT second, so it is popped next
				stack.push(node.left);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		System.out.println(preOrder(root)); // [1, 2, 4, 5, 3, 6]
	}
}

package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicBoolean;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 110. Balanced Binary Tree
//
// Input: the root of a binary tree. A tree is balanced when, at every node, the heights of the left
// and right subtrees differ by at most 1 (the height of an empty tree is 0). Returns true when the
// tree is balanced. On LeetCode: 0 <= number of nodes <= 5000, -10^4 <= Node.val <= 10^4.
//
// The one idea: one call returns the height of one node's subtree. A node's height is 1 plus the
// taller of its two children's heights, and the node is out of balance when those two heights
// differ by more than 1. The children's heights are what the two calls on the children return, so
// each node is looked at once, bottom-up. The first out-of-balance node flips a shared flag; after
// that every call returns at once, since the answer is already false.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Siblings: IsBalancedBinaryTree -- the same check, returning -1 as the height of an unbalanced
// subtree instead of setting a flag.

public final class BalancedBinaryTree {

	public static boolean isBalanced(TreeNode root) {
		AtomicBoolean balanced = new AtomicBoolean(true); // FLAG: stays true until a node is out of balance
		isBalanced(root, balanced); // WALK: every node checks its two sides
		return balanced.get(); // RETURN: no node was out of balance
	}

	private static int isBalanced(TreeNode root, AtomicBoolean balanced) {
		if (isNull(root) || !balanced.get()) {
			return 0; // BASE: empty, or the answer is already false
		}
		int left = isBalanced(root.left, balanced); // WALK: height of the left subtree
		int right = isBalanced(root.right, balanced); // WALK: height of the right subtree
		if (abs(left - right) > 1) {
			balanced.set(false); // MISMATCH: this node is out of balance
		}
		return 1 + max(left, right); // RETURN: height of this subtree
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(5);
		insert(root1, 3);
		insert(root1, 2);
		insert(root1, 1);
		insert(root1, 4);
		insert(root1, 6);
		System.out.println(isBalanced(root1)); // false

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		System.out.println(isBalanced(root2)); // true
	}
}

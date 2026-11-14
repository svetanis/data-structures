package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 965. Univalued Binary Tree
//
// Input: the root of a binary tree. A tree is univalued when every node holds the same value.
// Returns true when it is. On LeetCode: 1 <= number of nodes <= 100, 0 <= Node.val <= 99. The root
// must not be null: it supplies the value everything is compared with.
//
// The one idea: if every node must equal the root's value, check each node against that one value.
// One call checks one node, then asks the same question of its left child and its right child; the
// tree is univalued when no call finds a different value. The root's value is passed down unchanged
// so every node is compared with the same number, not with its parent.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.

public final class UnivaluedBinaryTree {

	public static boolean isUnivalTree(TreeNode root) {
		return dfs(root, root.val);
	}

	private static boolean dfs(TreeNode root, int value) {
		if(root == null) {
			return true; // BASE: an empty side has nothing to differ
		}
		if(root.val != value) {
			return false; // MISMATCH: this node differs from the root's value
		}
		boolean left = dfs(root.left, value); // WALK: the left subtree
		boolean right = dfs(root.right, value); // WALK: the right subtree
		return left && right;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(1);
		root.right = newNode(1);
		root.left.left = newNode(1);
		root.left.right = newNode(1);
		root.right.right = newNode(1);
		System.out.println(isUnivalTree(root)); // true

		TreeNode root1 = newNode(2);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(5);
		root1.left.right = newNode(2);
		System.out.println(isUnivalTree(root1)); // false
	}
}

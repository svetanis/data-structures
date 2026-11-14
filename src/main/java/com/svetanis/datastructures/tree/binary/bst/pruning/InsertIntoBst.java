package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 701. Insert into a Binary Search Tree
//
// Input: root -- the root of a binary search tree (possibly empty); val -- a value not already in
// the tree. Returns the root of the tree after a node holding val is added as a new leaf. On
// LeetCode: 0 <= number of nodes <= 10^4, -10^8 <= Node.val <= 10^8, all values distinct, val
// not in the tree; any valid result is accepted.
//
// The one idea: one call looks at one node, which holds one value, and returns the root of that
// node's subtree after the insert. A smaller val belongs somewhere in the left subtree, so the
// left child becomes whatever the call on it returns; a larger one goes right. An empty spot is
// where the new leaf goes, and the call that finds it returns the new node. Every node on the
// way down gets its own child handed back to it, unchanged except at the very bottom.
//
// Time: O(h) -- one path from the root to the empty spot; h is the tree's height, up to n on a
// chain.
// Space: O(h) -- the call stack.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of 10^4 nodes (the
// LeetCode maximum) overflows the default stack. InsertIntoBstIterative does it with a loop.
//
// Siblings: InsertIntoBstIterative -- the same walk with a loop and O(1) extra space.

public final class InsertIntoBst {

	public static TreeNode insert(TreeNode root, int val) {
		if (root == null) {
			return new TreeNode(val); // BASE: the empty spot gets the new leaf
		}
		if (root.val < val) {
			root.right = insert(root.right, val); // WALK right: val is larger
		} else {
			root.left = insert(root.left, val); // WALK left: val is smaller
		}
		return root; // RETURN: this subtree's root is unchanged
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(7);
		root.left.left = newNode(1);
		root.left.right = newNode(3);

		Nodes.preOrder(insert(root, 5)); // 4 2 1 3 7 5
		System.out.println();

		TreeNode root2 = newNode(5);
		root2.left = newNode(2);
		root2.right = newNode(7);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(4);
		Nodes.preOrder(insert(root2, 25)); // 5 2 1 3 4 7 25
		System.out.println();
	}
}

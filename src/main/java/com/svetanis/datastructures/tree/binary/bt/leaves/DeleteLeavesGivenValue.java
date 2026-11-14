package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1325. Delete Leaves With a Given Value
//
// Input: the root of a binary tree and a target value. Deletes every leaf -- a node with no
// children -- that holds target; if that makes its parent a leaf holding target, deletes the
// parent too, and so on until no such leaf is left. Returns the root of the changed tree, which
// is null when the whole tree is deleted. On LeetCode: 1 <= nodes <= 3000, 1 <= Node.val,
// target <= 1000.
//
// The one idea: a node can only become a leaf after its children are settled, so settle the
// children first (post-order). Each call hands back what is left of its subtree: it replaces its
// left and right links with what the children hand back, then checks itself -- no children left
// and the target value -- and hands back null if so, itself if not. One pass does what repeated
// rounds of leaf-picking would do.
//
// Time: O(n) -- one call per node.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.

public final class DeleteLeavesGivenValue {

	public static TreeNode removeLeaves(TreeNode root, int target) {
		if (root == null) {
			return null; // BASE: nothing to delete
		}
		root.left = removeLeaves(root.left, target); // SETTLE: the left subtree first
		root.right = removeLeaves(root.right, target); // SETTLE: the right subtree first
		if (root.left == null && root.right == null && root.val == target) {
			return null; // DELETE: now a leaf holding the target
		}
		return root; // KEEP: hand the node back to its parent
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(2);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(4);
		Nodes.preOrder(removeLeaves(root, 2));
		System.out.println(); // 1 3 4

		TreeNode root1 = new TreeNode(1);
		root1.left = new TreeNode(3);
		root1.right = new TreeNode(3);
		root1.left.left = new TreeNode(3);
		root1.left.right = new TreeNode(2);
		Nodes.preOrder(removeLeaves(root1, 3));
		System.out.println(); // 1 3 2
	}
}

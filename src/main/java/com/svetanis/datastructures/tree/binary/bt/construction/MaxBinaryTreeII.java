package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 998. Maximum Binary Tree II
//
// Input: root -- the maximum binary tree (see MaxBinaryTree) of some array a of distinct values,
// and a value val not in a. On LeetCode: 1 <= number of nodes <= 100, 1 <= value <= 100.
// Returns: the maximum binary tree of the array a with val appended at its end.
//
// The one idea: val sits at the far right of the array. In the tree, the far right of the array is
// the end of the right spine (root, root.right, root.right.right, ...), because every node on it
// is the maximum of a slice that reaches the right end. Walk down that spine past every value
// larger than val. If the spine ends first, val becomes the right child of its last node. If a
// node smaller than val comes first, val is larger than everything in that slice, so val becomes
// the root of the slice, with the whole old slice (that node and below) as its left subtree and an
// empty right side. Both cases are the same code: a null root is also "smaller than val".
//
// Time: O(h) -- one step per node on the right spine, at most the height h (up to n).
// Space: O(h) -- the recursion along the same spine (up to n).

public final class MaxBinaryTreeII {

	public static TreeNode insert(TreeNode root, int val) {
		if (root == null || root.val < val) { // STOP: val is the maximum of this slice
			TreeNode newRoot = new TreeNode(val); // ROOT: val, with the old slice to its left
			newRoot.left = root;
			return newRoot;
		}
		root.right = insert(root.right, val); // WALK: down the right spine, val stays to the right
		return root;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(1);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(2);
		Nodes.preOrder(insert(root, 5)); // 5 4 1 3 2
		System.out.println();

		TreeNode root1 = new TreeNode(5);
		root1.left = new TreeNode(2);
		root1.right = new TreeNode(4);
		root1.left.right = new TreeNode(1);
		Nodes.preOrder(insert(root1, 3)); // 5 2 1 4 3
		System.out.println();

		TreeNode root2 = new TreeNode(5);
		root2.left = new TreeNode(2);
		root2.right = new TreeNode(3);
		root2.left.right = new TreeNode(1);
		Nodes.preOrder(insert(root2, 4)); // 5 2 1 4 3
		System.out.println();
	}
}

package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Maximum Root-to-Leaf Path Sum -- not a LeetCode problem (124. Binary Tree Maximum Path Sum
// lets a path start and end anywhere)
//
// Input: the root of a binary tree. Returns the largest sum of values on a path that starts at
// the root and ends at a leaf, or 0 for an empty tree. Values may be negative. Assumes the sums
// fit in an int.
//
// The one idea: ask each node for the best sum of a path from it down to a leaf. A leaf answers
// with its own value. A node with two children adds its value to the better answer of the two.
// A node with ONE child must use that child, whatever it is worth: an empty side is not a path,
// because a path has to end at a leaf. (Treating the empty side as 0 would let a negative
// subtree be skipped and give a sum that no root-to-leaf path has.)
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Siblings: RootToLeafMaxSumPath returns the path itself using this sum; the Recursive,
// Recursive2 and Iterative versions find the path in one pass.
public final class RootToLeafMaxSum {

	public static int maxSum(TreeNode root) {
		if (root == null) { // BASE: an empty tree has no path
			return 0;
		}
		if (isLeaf(root)) { // LEAF: the path ends here
			return root.val;
		}
		if (root.left == null) { // ONE CHILD: the path must go right
			return root.val + maxSum(root.right);
		}
		if (root.right == null) { // ONE CHILD: the path must go left
			return root.val + maxSum(root.left);
		}
		// RETURN: this node plus the better of its two downward paths
		return root.val + max(maxSum(root.left), maxSum(root.right));
	}

	public static void main(String[] args) {
		// Max sum Root-to-leaf path
		// sum = 16: 8->-2->10
		// sum = 8: -4->-2->10
		// sum = 17: 7->10
		// max sum = 17
		TreeNode root = newNode(10);
		root.left = newNode(-2);
		root.right = newNode(7);
		root.left.left = newNode(8);
		root.left.right = newNode(-4);
		System.out.println(maxSum(root)); // 17

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(8);
		root2.left.right = newNode(4);
		root2.right.left = newNode(5);
		root2.right.right = newNode(6);
		root2.left.right.left = newNode(10);
		root2.right.left.left = newNode(7);
		root2.right.left.right = newNode(9);
		root2.right.right.right = newNode(5);
		System.out.println(maxSum(root2)); // 18

		// every path is negative: the answer is the least negative one, not 0
		TreeNode root3 = newNode(1);
		root3.left = newNode(-5);
		System.out.println(maxSum(root3)); // -4
	}
}

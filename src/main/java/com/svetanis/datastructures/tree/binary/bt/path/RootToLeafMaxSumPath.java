package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.bt.path.RootToLeafMaxSum.maxSum;
import static com.svetanis.datastructures.tree.binary.bt.path.RootToLeafPathGivenSum.path;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.List;

// Root-to-Leaf Path With the Maximum Sum -- not a LeetCode problem
//
// Input: the root of a binary tree. Returns the values of the root-to-leaf path with the largest
// sum, root first; if several paths tie, the leftmost one. Returns an empty list for an empty
// tree. Values may be negative.
//
// The one idea: do it in two steps with two existing pieces. First RootToLeafMaxSum finds the
// largest sum over all root-to-leaf paths. Then RootToLeafPathGivenSum looks for a path whose
// sum is exactly that number, which exists by construction, and returns the first one it meets
// going left before right.
//
// Time: O(n) -- two passes over the tree.
// Space: O(h) -- the recursion stacks (h = height, n on a chain); the path is extra.
//
// Siblings: RootToLeafMaxSumPathRecursive, RootToLeafMaxSumPathRecursive2 and
// RootToLeafMaxSumPathIterative find the path in one pass.
public final class RootToLeafMaxSumPath {

	public static List<Integer> maxSumPath(TreeNode root) {
		int max = maxSum(root); // SUM: the best total
		return path(root, max); // PATH: given sum path
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
		System.out.println(maxSumPath(root)); // [10, 7]

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
		System.out.println(maxSumPath(root2)); // [1, 3, 5, 9]

		// every path is negative
		TreeNode root3 = newNode(1);
		root3.left = newNode(-5);
		System.out.println(maxSumPath(root3)); // [1, -5]
	}
}

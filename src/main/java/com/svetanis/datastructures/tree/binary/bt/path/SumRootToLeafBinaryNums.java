package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1022. Sum of Root To Leaf Binary Numbers
//
// Input: the root of a binary tree whose values are 0 or 1. Each root-to-leaf path read from the
// root is a binary number, the root being the most significant bit. Returns the sum of those
// numbers over all leaves; the answer is guaranteed to fit in an int. On LeetCode:
// 1 <= n <= 1000, val is 0 or 1.
//
// The one idea: reading one more bit of a binary number shifts what was read one place left and
// puts the new bit at the end: number = (number << 1) | bit. So carry the number read so far
// down to each child. A leaf's number is complete and is returned; any other node returns the sum
// of what its two children return. Each call has its own copy of the number, so nothing is undone.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: SumRootToLeafNums does the same in decimal (multiply by 10 instead of shifting).
public final class SumRootToLeafBinaryNums {

	public static int sum(TreeNode root) {
		return sum(root, 0);
	}

	private static int sum(TreeNode root, int sum) {
		if (root == null) { // BASE: no node, no number
			return 0;
		}
		sum = (sum << 1) | root.val; // TAKE: shift the bits read so far, add this bit
		if (root.left == null && root.right == null) { // LEAF: the number is complete
			return sum;
		}
		int left = sum(root.left, sum); // WALK: left subtree
		int right = sum(root.right, sum); // WALK: right subtree
		return left + right;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(0);
		root.right = newNode(1);
		root.left.left = newNode(0);
		root.left.right = newNode(1);
		root.right.left = newNode(0);
		root.right.right = newNode(1);
		System.out.println(sum(root)); // 22

		TreeNode root2 = newNode(0);
		System.out.println(sum(root2)); // 0
	}
}

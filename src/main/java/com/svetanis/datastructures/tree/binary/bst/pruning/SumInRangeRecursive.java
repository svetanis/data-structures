package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 938. Range Sum of BST
//
// Input: root -- the root of a binary search tree with distinct values; low and high -- the ends
// of the range. Returns the sum of the values of the nodes whose value v has low <= v <= high.
// On LeetCode: 1 <= number of nodes <= 2 * 10^4, 1 <= Node.val <= 10^5, all values distinct,
// 1 <= low <= high <= 10^5. The sum is at most 2 * 10^9, which still fits an int.
//
// The one idea: one call looks at one node, which holds one value; in a BST everything left of it
// is smaller and everything right of it larger. A node below the range (v < low) has only smaller
// values to its left, so the answer is the sum of its right subtree alone. A node above the range
// (v > high) is the mirror: its left subtree alone. A node inside the range adds its own value to
// the sums of both subtrees. A subtree that cannot hold an answer is never entered.
//
// Time: O(h + k) -- the k nodes in the range, plus the out-of-range nodes on the two paths down
// to the ends of the range, at most h of them; h is the tree's height.
// Space: O(h) -- the call stack.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of 2 * 10^4 nodes
// (LeetCode's largest) overflows the default stack. SumInRangeIterative uses a queue instead.
//
// Siblings: SumInRangeIterative makes the same decisions level by level with a queue, O(n) extra
// space. In this file sumInRange tests whether the node is inside the range and recurses on the
// side the node allows; sumInRange2 adds the node if it is inside and then recurses on each side
// that can still hold a value (low < val for the left, high > val for the right). Same answer,
// same work.

public final class SumInRangeRecursive {

	public static int sumInRange2(TreeNode root, int low, int high) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree adds nothing
		}
		int sum = 0;
		if (inRange(root.val, low, high)) {
			sum += root.val; // ADD: this node is inside the range
		}
		if (low < root.val) {
			sum += sumInRange(root.left, low, high); // TAKE: the left side can hold values >= low
		}
		if (high > root.val) {
			sum += sumInRange(root.right, low, high); // TAKE: the right side can hold values <= high
		}
		return sum; // RETURN
	}

	public static int sumInRange(TreeNode root, int low, int high) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree adds nothing
		}
		if (inRange(root.val, low, high)) {
			int left = sumInRange(root.left, low, high); // TAKE: both sides may hold more
			int right = sumInRange(root.right, low, high);
			return root.val + left + right; // ADD: this node plus both sides
		} else if (root.val < low) {
			return sumInRange(root.right, low, high); // SKIP: too small, the left side is smaller still
		} else {
			return sumInRange(root.left, low, high); // SKIP: too large, the right side is larger still
		}
	}

	private static boolean inRange(int val, int low, int high) {
		return val <= high && val >= low;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(10);
		root.left = newNode(5);
		root.right = newNode(50);
		root.left.left = newNode(1);
		root.right.left = newNode(40);
		root.right.right = newNode(100);
		System.out.println(sumInRange(root, 5, 45)); // 55 (5 + 10 + 40)
		System.out.println(sumInRange2(root, 5, 45)); // 55
	}
}
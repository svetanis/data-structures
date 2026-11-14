package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Count the BST nodes in a range (not a LeetCode problem; LC 938 is the same walk adding the
// values up instead of counting them)
//
// Input: root -- the root of a binary search tree of ints; low and high -- the ends of the range,
// low <= high. Returns the number of nodes whose value v has low <= v <= high.
//
// The one idea: one call looks at one node, which holds one value. In a BST everything left of it
// is smaller and everything right of it is larger. A node below the range (v < low) has only
// smaller values to its left, so the answer is the count in its right subtree alone. A node above
// the range (v > high) is the mirror: the count in its left subtree alone. A node inside the
// range counts itself plus the counts of both subtrees. A subtree that cannot hold an answer is
// never entered.
//
// Time: O(h + k) -- the k nodes in the range, plus the out-of-range nodes on the two paths down
// to the ends of the range, at most h of them; h is the tree's height.
// Space: O(h) -- the call stack.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of about 10^4 nodes
// overflows the default stack; the work is the same on any shape. CountInRangeIterative does it
// with a queue and no recursion.
//
// Siblings: CountInRangeIterative makes the same decisions level by level with a queue; it
// needs O(n) extra space where this needs O(h).

public final class CountInRangeRecursive {

	public static int countInRange(TreeNode root, int low, int high) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree holds nothing
		}
		if (inRange(root.val, low, high)) {
			int left = countInRange(root.left, low, high); // TAKE: both sides may hold more
			int right = countInRange(root.right, low, high);
			return 1 + left + right; // COUNT: this node plus both sides
		} else if (root.val < low) {
			return countInRange(root.right, low, high); // SKIP: too small, the left side is smaller still
		} else {
			return countInRange(root.left, low, high); // SKIP: too large, the right side is larger still
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
		System.out.println(countInRange(root, 5, 45)); // 3 (the nodes 5, 10 and 40)
	}
}
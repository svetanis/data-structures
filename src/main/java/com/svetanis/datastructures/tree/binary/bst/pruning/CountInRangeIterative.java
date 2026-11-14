package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Count the BST nodes in a range (not a LeetCode problem; LC 938 is the same walk adding the
// values up instead of counting them)
//
// Input: root -- the root of a binary search tree of ints; low and high -- the ends of the range,
// low <= high. Returns the number of nodes whose value v has low <= v <= high.
//
// The one idea: one node is one value, and in a BST everything left of it is smaller and
// everything right of it is larger. A node below the range (v < low) has only smaller values to
// its left, so none of them can count: only its right child is worth visiting. A node above the
// range (v > high) sends us to its left child only. A node inside the range counts one and sends
// us to both children. The walk therefore never enters a subtree that cannot hold an answer.
// This file walks by level, with a queue.
//
// Time: O(h + k) -- the k nodes in the range, plus the out-of-range nodes on the two paths down
// to the ends of the range, at most h of them; h is the tree's height.
// Space: O(n) -- the queue holds a whole level of the visited part at once; on a balanced tree
// whose range covers everything the widest level is n / 2, where the recursive sibling's call
// stack is only the height.
//
// Siblings: CountInRangeRecursive makes the same decisions with recursion, so its extra space is
// the height, O(h).

public final class CountInRangeIterative {

	public static int countInRange(TreeNode root, int low, int high) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree holds nothing
		}
		int count = 0;
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll(); // WALK: next node of the visited part
			if (inRange(node.val, low, high)) {
				count++; // COUNT: it is inside the range
				if (isNotNull(node.left)) {
					queue.add(node.left); // TAKE: both sides may hold more
				}
				if (isNotNull(node.right)) {
					queue.add(node.right);
				}
			} else if (node.val < low && isNotNull(node.right)) {
				queue.add(node.right); // SKIP: too small, its whole left side is smaller still
			} else if (node.val > high && isNotNull(node.left)) {
				queue.add(node.left); // SKIP: too large, its whole right side is larger still
			}
		}
		return count; // RETURN: nodes inside the range
	}

	private static boolean inRange(int val, int start, int end) {
		return val <= end && val >= start;
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
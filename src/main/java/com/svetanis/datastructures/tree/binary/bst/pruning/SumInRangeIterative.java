package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 938. Range Sum of BST
//
// Input: root -- the root of a binary search tree with distinct values; low and high -- the ends
// of the range. Returns the sum of the values of the nodes whose value v has low <= v <= high.
// On LeetCode: 1 <= number of nodes <= 2 * 10^4, 1 <= Node.val <= 10^5, all values distinct,
// 1 <= low <= high <= 10^5. The sum is at most 2 * 10^9, which still fits an int.
//
// The one idea: one node is one value, and in a BST everything left of it is smaller and
// everything right of it larger. A node below the range (v < low) has only smaller values to its
// left, so none of them can count: only its right child is worth visiting. A node above the range
// (v > high) sends us to its left child only. A node inside the range adds its value and sends us
// to both children. A subtree that cannot hold an answer is never entered. This file walks by
// level, with a queue.
//
// Time: O(h + k) -- the k nodes in the range, plus the out-of-range nodes on the two paths down
// to the ends of the range, at most h of them; h is the tree's height.
// Space: O(n) -- the queue holds a whole level of the visited part at once; on a balanced tree
// whose range covers everything the widest level is n / 2, where the recursive sibling's call
// stack is only the height.
//
// Siblings: SumInRangeRecursive makes the same decisions with recursion (two versions: one tests
// the range first, one tests each side), O(h) extra space.

public final class SumInRangeIterative {

	public static int sumInRange(TreeNode root, int low, int high) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree adds nothing
		}
		int sum = 0;
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll(); // WALK: next node of the visited part
			if (inRange(node.val, low, high)) {
				sum += node.val; // ADD: it is inside the range
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
		return sum; // RETURN: the values inside the range, added up
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
		System.out.println(sumInRange(root, 5, 45)); // 55 (5 + 10 + 40)
	}
}
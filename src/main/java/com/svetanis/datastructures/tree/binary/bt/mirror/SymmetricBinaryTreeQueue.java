package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 101. Symmetric Tree
//
// Input: root -- the root of a binary tree, possibly null. Returns true if the tree is a mirror of
// itself around its centre: the left subtree is the right subtree with left and right swapped at
// every node. On LeetCode: 1 <= nodes <= 1000, -100 <= Node.val <= 100.
//
// The one idea: the queue holds nodes two at a time, and the two nodes taken out together must
// match -- both missing, or both present with equal values. Start with the root's two children. When
// a pair matches, put its children back as two new pairs, crosswise: the first's left with the
// second's right, then the first's right with the second's left. One missing and one present is a
// mismatch, so the answer is false at once. When the queue runs dry every pair matched. The queue
// is a LinkedList because it must accept null entries (a missing child); an ArrayDeque would throw.
//
// Time: O(n) -- each node is put in once and compared once with its mirror partner.
// Space: O(n) -- the queue holds the pairs of one level, missing children included, up to the width
// of the tree.
//
// Siblings: SymmetricBinaryTree runs the same pair test by recursion (O(h) space).

public final class SymmetricBinaryTreeQueue {

	public static boolean symmetric(TreeNode root) {
		if (root == null) { // BASE: an empty tree is symmetric
			return true;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root.left); // PAIR: the root's two children go in together
		queue.add(root.right);
		while (!queue.isEmpty()) {
			TreeNode node1 = queue.poll(); // TAKE: two nodes that must mirror each other
			TreeNode node2 = queue.poll();

			if (node1 == null && node2 == null) { // SKIP: both missing match, nothing below
				continue;
			}
			if (node1 == null || node2 == null) { // FAIL: only one is missing
				return false;
			}
			if (node1.val != node2.val) { // FAIL: values differ
				return false;
			}
			queue.add(node1.left); // CROSS: outer pair
			queue.add(node2.right);
			queue.add(node1.right); // CROSS: inner pair
			queue.add(node2.left);
		}
		return true;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		System.out.println(symmetric(root)); // false

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(3);
		root1.left.right = newNode(4);
		root1.right.left = newNode(4);
		root1.right.right = newNode(3);
		System.out.println(symmetric(root1)); // true
	}
}

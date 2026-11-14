package com.svetanis.datastructures.tree.binary.bt.special;

import java.util.Deque;
import java.util.LinkedList;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 958. Check Completeness of a Binary Tree
//
// Input: the root of a binary tree. A tree is complete when every level except possibly the last is
// full, and the last level has its nodes as far left as possible. Returns true when the tree is
// complete. On LeetCode: 1 <= number of nodes <= 100, 1 <= Node.val <= 1000.
//
// The one idea: read the tree level by level, left to right, and write down every child slot,
// including the empty ones. In a complete tree the slots read "node, node, ..., node, then empty,
// empty, ..." -- the first empty slot is followed by nothing but empty slots. Any node found after
// an empty slot means some level had a gap before it, so the tree is not complete. The queue holds
// the slots in reading order; null stands for an empty slot.
//
// Time: O(n) -- each node is taken from the queue once and puts two slots in; the queue ends with
// at most n + 1 empty slots.
// Space: O(n) -- the queue holds the slots of about two levels, which is O(n) for a wide tree.
//
// Siblings: isCompleteLastLevel in this file does the same with one pass and a flag.
// BinaryTreeCompletenessDfs and CompleteBinaryTreeRecursive number the nodes like an array
// instead; CompleteBinaryTreeLot reads levels, deciding by the children of each node.

public final class BinaryTreeCompletenessBfs {

	public static boolean isComplete(TreeNode root) {
		Deque<TreeNode> dq = new LinkedList<>(); // SLOTS: nodes and empty slots in reading order
		dq.offer(root);
		while (dq.peek() != null) { // WALK: stop at the first empty slot
			TreeNode node = dq.poll();
			dq.offer(node.left);
			dq.offer(node.right);
		}
		while(!dq.isEmpty() && dq.peek() == null) { // SKIP: the empty slots that may follow
			dq.poll();
		}
		return dq.isEmpty(); // RETURN: anything left over is a node after a gap
	}

	// one pass: the first empty slot raises a flag, and any node seen after it fails
	public static boolean isCompleteLastLevel(TreeNode root) {
		Deque<TreeNode> dq = new LinkedList<>();
		dq.offer(root);
		boolean lastLevel = false; // GAP: true once an empty slot has been read
		while (!dq.isEmpty()) {
			TreeNode node = dq.poll();
			if(node == null) {
				lastLevel = true; // GAP: from here on only empty slots may follow
				continue;
			} else if(lastLevel) {
				return false; // MISMATCH: a node after a gap
			}
			dq.offer(node.left);
			dq.offer(node.right);
		}
		return true;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		System.out.println(isComplete(root)); // true

		TreeNode root1 = new TreeNode(1);
		root1.left = new TreeNode(2);
		root1.right = new TreeNode(3);
		root1.left.left = new TreeNode(4);
		root1.left.right = new TreeNode(5);
		root1.right.right = new TreeNode(7);
		System.out.println(isComplete(root1)); // false
	}
}

package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 637. Average of Levels in Binary Tree
//
// Input: root of a binary tree. Returns the average of the values on each level, top level first.
// On LeetCode: 1 <= number of nodes <= 10^4, -2^31 <= node value <= 2^31 - 1.
//
// The one idea: one queue entry is one node of the level being read. At the start of each round
// the queue holds exactly one whole level, so its size is how many nodes that level has: take
// exactly that many nodes off, add their values, and queue their children -- which become the
// next level. The sum is a double, which holds the sum of 10^4 ints exactly (at most about 2 * 10^13),
// so no value overflows and the one division at the end is the only rounding.
//
// Time: O(n) -- each node is queued and taken off once.
// Space: O(w) -- the queue holds one level at a time, w = the widest level (up to n / 2).
//
// Siblings: LotLineByLineQueue reads the same levels but keeps the values instead of summing them.

public final class LevelAverages {

	public static List<Double> lot(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		List<Double> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			double sum = 0;
			int size = queue.size(); // SIZE: the queue holds exactly one level
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll(); // TAKE: one node of this level
				sum += node.val; // SUM: in a double, so no int overflow
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: the next level
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			list.add(sum / size); // RECORD: the average of this level
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
		System.out.println(lot(root)); // [3.0, 14.5, 11.0]

		TreeNode root1 = new TreeNode(3);
		root1.left = new TreeNode(9);
		root1.right = new TreeNode(20);
		root1.left.left = new TreeNode(15);
		root1.left.right = new TreeNode(7);
		System.out.println(lot(root1)); // [3.0, 14.5, 11.0]
	}
}

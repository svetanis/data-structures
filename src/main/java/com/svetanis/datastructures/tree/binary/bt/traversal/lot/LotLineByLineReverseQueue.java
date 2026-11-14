package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 107. Binary Tree Level Order Traversal II
//
// Input: root of a binary tree. Returns the values level by level, deepest level first, each
// level left to right, as a list of lists. On LeetCode: 0 <= number of nodes <= 2000,
// -1000 <= node value <= 1000.
//
// The one idea: it is the ordinary level-by-level queue walk with one change. One queue entry is
// one node waiting to be read; at the start of each round the queue holds exactly one whole
// level, so its size is that level's node count. The levels come out top first, so each finished
// level is put at the FRONT of the result (index 0) instead of the end, which leaves the deepest
// level first. The values inside a level stay left to right.
//
// Plain Java, so the method pastes into LeetCode as it is.
//
// Time: O(n) -- each node is queued and taken off once; a front insert into a LinkedList is O(1).
// Space: O(n) -- the output; the queue holds one level at a time, up to n / 2 nodes.
//
// Siblings: LotLineByLineQueue is the top-first version (problem 102).

public final class LotLineByLineReverseQueue {

	public static List<List<Integer>> lot(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}

		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		List<List<Integer>> lists = new LinkedList<>();

		while (!queue.isEmpty()) {
			int size = queue.size(); // SIZE: the queue holds exactly one level
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll(); // TAKE: one node of this level
				list.add(node.val); // RECORD: its value
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: the next level, left to right
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			lists.add(0, list); // FRONT: a deeper level goes before every level found so far
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);
		System.out.println(lot(root)); // [[4, 5, 6], [2, 3], [1]] -- deepest level first
	}
}

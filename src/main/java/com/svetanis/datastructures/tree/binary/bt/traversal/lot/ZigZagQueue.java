package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 103. Binary Tree Zigzag Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level as a list of lists: the first
// level left to right, the second right to left, and so on, alternating. On LeetCode:
// 0 <= number of nodes <= 2000, -100 <= node value <= 100.
//
// The one idea: one queue entry is one node waiting to be read. At the start of each round the
// queue holds exactly one whole level, left to right, so its size is that level's node count.
// Read it in that order -- the queue never changes direction -- and decide only where each value
// goes in the level's list: at the end on a left-to-right level, at the FRONT (index 0) on a
// right-to-left level. Putting every value at the front reverses the level. A flag, flipped after
// each level, says which kind the level is.
//
// Time: O(n) -- each node is queued and taken off once; a front insert into a LinkedList is O(1).
// Space: O(n) -- the output; the queue holds one level at a time, up to n / 2 nodes.
//
// Siblings: ZigZagTwoStack gets the zigzag from two stacks, no front inserts; ZigZagRecursive walks
// the tree once per level; ZigZagStackQueue returns one flat list instead of a list per level.

public final class ZigZagQueue {

	public static List<List<Integer>> traverse(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		boolean leftToRight = true; // DIRECTION: of the level being read
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		List<List<Integer>> lists = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size(); // SIZE: the queue holds exactly one level
			List<Integer> list = new LinkedList<>();
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll(); // TAKE: one node of this level
				if (leftToRight) {
					list.add(node.val); // END: left-to-right level
				} else {
					list.add(0, node.val); // FRONT: right-to-left level
				}
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: the next level, always left to right
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			lists.add(list);
			leftToRight = !leftToRight; // FLIP: the next level goes the other way
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(traverse(root)); // [[3], [20, 9], [15, 7]]
	}
}

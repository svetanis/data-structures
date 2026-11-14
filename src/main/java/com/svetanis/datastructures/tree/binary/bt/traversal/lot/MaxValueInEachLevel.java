package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 515. Find Largest Value in Each Tree Row
//
// Input: root of a binary tree. Returns the largest value on each level, top level first. On
// LeetCode: 0 <= number of nodes <= 10^4, -2^31 <= node value <= 2^31 - 1.
//
// The one idea: one queue entry is one node waiting to be read. At the start of each round the
// queue holds exactly one whole level, so its size is that level's node count: take exactly that
// many nodes off and queue their children, which are the next level. The running maximum starts
// as the level's first value -- not as 0 or Integer.MIN_VALUE -- so negative values and the
// extremes of the int range need no special case.
//
// Time: O(n) -- each node is queued and taken off once.
// Space: O(w) -- the queue holds one level at a time, w = the widest level (up to n / 2).

public final class MaxValueInEachLevel {

	public static List<Integer> lot(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<TreeNode> queue = new ArrayDeque<>();
		queue.offer(root);
		List<Integer> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			int max = queue.peek().val; // START: the level's first value
			int size = queue.size(); // SIZE: the queue holds exactly one level
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll(); // TAKE: one node of this level
				max = Math.max(max, node.val); // MAX: running maximum of this level
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: the next level
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			list.add(max); // RECORD: this level's maximum
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(9);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.right = newNode(7);
		System.out.println(lot(root)); // [4, 9, 7]
	}
}

package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 102. Binary Tree Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level, top level first, each level
// left to right, as a list of lists. On LeetCode: 0 <= number of nodes <= 2000,
// -1000 <= node value <= 1000.
//
// The one idea: one queue entry is one node waiting to be read. At the start of each round the
// queue holds exactly one whole level, so its size is how many nodes that level has: take exactly
// that many nodes off, collect their values into one list, and queue their children -- which are
// exactly the next level, in left-to-right order, because the queue hands nodes back in the order
// they went in. The round ends with the queue holding the next level, and the loop repeats until
// the queue is empty.
//
// Time: O(n) -- each node is queued and taken off once.
// Space: O(n) -- the output; the queue holds one level at a time, up to n / 2 nodes.
//
// Siblings: LotLineByLineDfs gets the same lists from a depth-first walk that carries the depth;
// LotLineByLineHashing keeps a map from level to values; LotLineByLineRecursive walks the tree
// once per level. LotLineByLineReverseQueue is this loop with the lists in reverse order.

public final class LotLineByLineQueue {

	public static List<List<Integer>> lot(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		List<List<Integer>> lists = new ArrayList<>();
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
			lists.add(list);
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(lot(root)); // [[3], [9, 20], [15, 7]]
	}
}

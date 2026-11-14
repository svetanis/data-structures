package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 199. Binary Tree Right Side View
//
// Input: the root of a binary tree. Returns the values of the nodes visible when the tree is
// seen from the right side: the rightmost node of every row, from the top row down. On
// LeetCode: 0 <= number of nodes <= 100, -100 <= val <= 100.
//
// The one idea: walk the tree one row at a time, queueing each node's right child before its
// left child. When a pass starts, the queue holds exactly one row in right-to-left order, so
// its size says how many nodes belong to that row and its first node is that row's rightmost.
// Write that first node, then take the whole row and queue the children for the next pass.
//
// Time: O(n) -- every node is queued once and taken once.
// Space: O(w) -- the queue holds at most the widest row, w <= n.
//
// Siblings: RightViewDfs reaches the same answer with a depth-first walk that goes right first
// and writes the first node it meets on each new row.

public final class RightViewLevelOrder {

	public static List<Integer> rightView(TreeNode root) {
		if (root == null) {
			return new ArrayList<>(); // BASE: empty tree, empty view
		}
		List<Integer> list = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root); // SEED: the first row is the root alone
		while (!queue.isEmpty()) { // WALK: one pass per row
			int size = queue.size(); // COUNT: nodes in this row
			list.add(queue.peek().val); // RECORD: the row's rightmost node
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (node.right != null) {
					queue.offer(node.right); // QUEUE: next row, right child first
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		System.out.println(rightView(root1)); // [1, 3, 7]

		TreeNode root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.left.left.left = newNode(3);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		System.out.println(rightView(root2)); // [12, 1, 5, 3]

		TreeNode root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		root3.left.right = newNode(5);
		root3.right.right = newNode(4);
		System.out.println(rightView(root3)); // [1, 3, 4]
	}
}

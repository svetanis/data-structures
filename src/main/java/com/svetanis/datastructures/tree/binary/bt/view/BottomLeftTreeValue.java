package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 513. Find Bottom Left Tree Value
//
// Input: the root of a binary tree. Returns the value of the leftmost node in the deepest row.
// On LeetCode: 1 <= number of nodes <= 10^4, -2^31 <= val <= 2^31 - 1.
//
// The one idea: walk the tree one row at a time, left to right. When a pass starts, the queue
// holds exactly one row, so its size says how many nodes belong to that row and its first node
// is that row's leftmost. Remember the first value of every row; when the rows run out, the
// value remembered last came from the deepest row.
//
// Time: O(n) -- every node is queued once and taken once.
// Space: O(w) -- the queue holds at most the widest row, w <= n.

public final class BottomLeftTreeValue {

	public static int bottomLeftVaue(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root); // SEED: the first row is the root alone
		int blv = 0;
		while (!queue.isEmpty()) { // WALK: one pass per row
			int size = queue.size(); // COUNT: nodes in this row
			blv = queue.peek().val; // RECORD: the row's leftmost value; the last row wins
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: next row, left child first
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return blv; // RETURN: the leftmost value of the deepest row
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);

		root.right.left = newNode(5);
		root.right.right = newNode(6);
		root.right.left.left = newNode(7);
		System.out.println(bottomLeftVaue(root)); // 7

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(3);
		System.out.println(bottomLeftVaue(root2)); // 1
	}
}

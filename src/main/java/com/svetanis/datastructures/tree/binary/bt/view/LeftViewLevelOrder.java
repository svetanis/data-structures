package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Left View of a Binary Tree (no LeetCode number; the mirror image of 199. Binary Tree Right
// Side View)
//
// Input: the root of a binary tree. Returns the nodes visible when the tree is seen from the
// left side: the leftmost node of every row, from the top row down.
//
// The one idea: walk the tree one row at a time, left to right. When a pass starts, the queue
// holds exactly one row, so its size says how many nodes belong to that row and its first
// node is that row's leftmost. Write that first node, then take the whole row and queue the
// children for the next pass.
//
// Time: O(n) -- every node is queued once and taken once.
// Space: O(w) -- the queue holds at most the widest row, w <= n.
//
// Siblings: LeftViewPreOrder reaches the same answer with a depth-first walk that goes left
// first and writes the first node it meets on each new row.

public final class LeftViewLevelOrder {

	public static List<TreeNode> leftView(TreeNode root) {
		if (isNull(root)) {
			return new ArrayList<>(); // BASE: empty tree, empty view
		}
		List<TreeNode> list = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root); // SEED: the first row is the root alone

		while (!queue.isEmpty()) { // WALK: one pass per row
			int size = queue.size(); // COUNT: nodes in this row
			list.add(queue.peek()); // RECORD: the row's leftmost node
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (isNotNull(node.left)) {
					queue.offer(node.left); // QUEUE: next row, left child first
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
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
		System.out.println(leftView(root1)); // [1, 2, 4]

		TreeNode root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.left.left.left = newNode(3);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		System.out.println(leftView(root2)); // [12, 7, 9, 3]
	}
}

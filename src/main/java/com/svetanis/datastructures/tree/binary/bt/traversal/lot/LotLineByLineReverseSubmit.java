package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.Print;

// 107. Binary Tree Level Order Traversal II

// Given a binary tree, populate an array to represent
// its level-by-level traversal in reverse order, i.e., 
// the lowest level comes first. Populate the values of 
// all nodes in each level from left to right in separate sub-arrays.

public final class LotLineByLineReverseSubmit {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> lot(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<Node> queue = new LinkedList<>();
		queue.offer(root);
		LinkedList<List<Integer>> lists = new LinkedList<>();
		while (!queue.isEmpty()) {
			List<Integer> list = new ArrayList<>();
			for (int i = queue.size(); i > 0; i--) {
				Node node = queue.pollFirst();
				list.add(node.data);
				if (node.left != null) {
					queue.offerLast(node.left);
				}
				if (node.right != null) {
					queue.offerLast(node.right);
				}
			}
			lists.addFirst(list);
		}
		return lists;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.right = new Node(6);
		Print.print(lot(root));
	}
}

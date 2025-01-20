package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 102. Binary Tree Level Order Traversal

// Given a binary tree, populate an array 
// to represent its level-by-level traversal. 
// Populate the values of all nodes of each 
// level from left to right in separate sub-arrays.

public final class LotLineByLineQueue {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> lot(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		List<List<Integer>> lists = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size();
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				list.add(node.data);
				if (node.left != null) {
					queue.offer(node.left);
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
		Node root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(lot(root)); // [3], [9,20], [15,7]
	}
}

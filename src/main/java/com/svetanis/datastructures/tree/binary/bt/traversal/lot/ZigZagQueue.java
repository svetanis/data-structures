package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 103. Binary Tree Zigzag Level Order Traversal

// Given a binary tree, populate an array 
// to represent its zigzag level order traversal. 
// You should populate the values of all nodes of 
// the first level from left to right, 
// then right to left for the next level and 
// keep alternating in the same manner for 
// the following levels.

public final class ZigZagQueue {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> traverse(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		boolean leftToRight = true;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		List<List<Integer>> lists = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size();
			List<Integer> list = new LinkedList<>();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (leftToRight) {
					list.add(node.data);
				} else {
					list.add(0, node.data);
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			lists.add(list);
			leftToRight = !leftToRight;
		}
		return lists;
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(traverse(root)); // [3], [20,9],[15,7]
	}
}

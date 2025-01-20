package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 199. Binary Tree Right Side View

// Given a binary tree, return an array 
// containing nodes in its right view. 

// The right view of a binary tree is 
// the set of nodes visible when the 
// tree is seen from the right side.

public final class RightViewLevelOrder {
	// Time Complexity: O(n)

	public static List<Integer> rightView(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		List<Integer> list = new ArrayList<>();
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			list.add(queue.peek().data);
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.right != null) {
					queue.offer(node.right);
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		print(rightView(root1)); // 1, 3, 7

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.left.left.left = newNode(3);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		print(rightView(root2)); // 12, 1, 5, 3
	}
}

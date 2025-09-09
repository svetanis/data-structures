package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 101. Symmetric Tree

// Mirror of itself (symmetric around its center)

public final class SymmetricBinaryTreeQueue {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static boolean symmetric(Node root) {
		if (root == null) {
			return true;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(root.left);
		queue.add(root.right);
		while (!queue.isEmpty()) {
			Node node1 = queue.poll();
			Node node2 = queue.poll();

			if (node1 == null && node2 == null) {
				continue;
			}
			if (node1 == null || node2 == null) {
				return false;
			}
			if (node1.data != node2.data) {
				return false;
			}
			queue.add(node1.left);
			queue.add(node2.right);
			queue.add(node1.right);
			queue.add(node2.left);
		}
		return true;
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		System.out.println(symmetric(root));

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(3);
		root1.left.right = newNode(4);
		root1.right.left = newNode(4);
		root1.right.right = newNode(3);
		System.out.println(symmetric(root1));
	}
}

package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 993. Cousins in Binary Tree

// 1. two nodes should be on the same level in BT
// 2. two nodes should not be sublings that is
// they should not have the same parent node

public final class CousinsQueue {
	// Time Complexity: O(n)

	public static boolean areCousins(Node root, int x, int y) {
		if (root == null) {
			return false;
		}
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			boolean foundX = false;
			boolean foundY = false;
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.data == x) {
					foundX = true;
				}
				if (node.data == y) {
					foundY = true;
				}
				if (node.left != null && node.right != null) {
					int left = node.left.data;
					int right = node.right.data;
					boolean one = left == x && right == y;
					boolean two = left == y && right == x;
					if (one || two) {
						return false;
					}
				}
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			if (foundX && foundY) {
				return true;
			}
			if (foundX || foundY) {
				return false;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.left.right.right = newNode(15);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		root.right.left.right = newNode(8);
		System.out.println(areCousins(root, 4, 7));

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		System.out.println(areCousins(root1, 5, 6));
	}
}
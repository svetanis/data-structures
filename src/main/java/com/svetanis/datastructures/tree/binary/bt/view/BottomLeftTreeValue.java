package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 513. Find Bottom Left Tree Value

public final class BottomLeftTreeValue {
	// Time Complexity: O(n)

	public static int bottomLeftVaue(Node root) {
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		int blv = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			blv = queue.peek().data;
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return blv;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);

		root.right.left = newNode(5);
		root.right.right = newNode(6);
		root.right.left.left = newNode(7);
		System.out.println(bottomLeftVaue(root)); // 7
	}
}

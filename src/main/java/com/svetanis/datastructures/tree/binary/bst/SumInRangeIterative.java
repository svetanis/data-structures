package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a range
// count number of nodes
// that lie in the given range

public final class SumInRangeIterative {

	public static int sumInRange(Node root, int low, int high) {
		// Time Complexity: O(n)
		// Space Complexity: O(h)

		if (isNull(root)) {
			return 0;
		}
		int sum = 0;
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (inRange(node.data, low, high)) {
				sum += node.data;
				if (isNotNull(node.left)) {
					queue.add(node.left);
				}
				if (isNotNull(node.right)) {
					queue.add(node.right);
				}
			} else if (node.data < low && isNotNull(node.right)) {
				queue.add(node.right);
			} else if (node.data > high && isNotNull(node.left)) {
				queue.add(node.left);
			}
		}
		return sum;
	}

	private static boolean inRange(int val, int start, int end) {
		return val <= end && val >= start;
	}

	public static void main(String[] args) {
		Node root = newNode(10);
		root.left = newNode(5);
		root.right = newNode(50);
		root.left.left = newNode(1);
		root.right.left = newNode(40);
		root.right.right = newNode(100);
		System.out.println(sumInRange(root, 5, 45));
	}
}
package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a binary tree and a node, 
// find the level order successor
// of the given node in the tree

// the level order successor is 
// the node that appears right 
// after the given node in the
// level order traversal

public final class LevelOrderSuccessor {

	public static Node lot(Node root, int key) {
		// Time Complexity: O(n)
		// Space Complexity: O(n)

		Queue<Node> queue = newLinkedList();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (isNotNull(node.left)) {
				queue.offer(node.left);
			}
			if (isNotNull(node.right)) {
				queue.offer(node.right);
			}
			if (node.data == key) {
				break;
			}
		}
		return queue.peek();
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);

		System.out.println(lot(root, 4));
	}
}

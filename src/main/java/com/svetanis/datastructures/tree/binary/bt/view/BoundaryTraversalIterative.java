package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BT, return an array containing
// all the boundary nodes of the tree in  
// an anti-clockwise direction. 

// the boundary of a tree contains all nodes
// in the left view, all leaves, and all 
// nodes in the right view.
// there should not be any duplicate nodes

public final class BoundaryTraversalIterative {

	public static ImmutableList<Node> boundary(Node root) {
		// Time Complexity: O(n)

		List<Node> leftView = newArrayList();
		List<Node> rightView = newArrayList();
		Queue<Node> queue = newLinkedList();
		queue.offer(root);

		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (isLeaf(node)) {
					continue;
				} else if (i == 0) {
					leftView.add(node);
				} else if (i == size - 1) {
					rightView.add(node);
				}

				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
		}

		List<Node> list = newArrayList();
		list.addAll(leftView);
		list.addAll(leaves(root));
		list.addAll(rightView);
		return newList(list);
	}

	private static ImmutableList<Node> leaves(Node root) {
		List<Node> list = newArrayList();
		Deque<Node> stack = new ArrayDeque<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (isLeaf(node)) {
				list.add(node);
			}
			if (isNotNull(node.right)) {
				stack.push(node.right);
			}
			if (isNotNull(node.left)) {
				stack.push(node.left);
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		root1.left.left.left = newNode(8);
		root1.left.left.right = newNode(9);
		root1.left.right.left = newNode(10);
		root1.right.left.left = newNode(11);
		root1.right.right.left = newNode(12);
		root1.right.right.right = newNode(13);

		print(boundary(root1)); // 1, 2, 4, 8, 9, 10, 11, 12, 13, 7, 3
		System.out.println();

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(4);
		root2.left.right = newNode(3);
		root2.left.left.left = newNode(9);
		root2.left.right.left = newNode(15);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		root2.right.right.left = newNode(6);

		print(boundary(root2)); // 12, 7, 4, 9, 15, 10, 6, 5, 1
		System.out.println();

		Node root3 = newNode(12);
		root3.right = newNode(1);
		root3.right.left = newNode(10);
		root3.right.right = newNode(5);
		print(boundary(root3)); // 12, 1, 10, 5
		System.out.println();
	}
}

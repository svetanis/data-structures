package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 545. Boundary of Binary Tree

public final class BoundaryTraversalSubmit {
	// Time Complexity: O(n)

	private List<Integer> boundary;

	public List<Integer> boundary(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		boundary = new ArrayList<>();
		if (!isLeaf(root)) {
			boundary.add(root.data);
		}
		// left boundary (excluding last left node)
		Node node = root.left;
		while (node != null) {
			if (!isLeaf(node)) {
				boundary.add(node.data);
			}
			node = node.left != null ? node.left : node.right;
		}
		// add all leaf nodes
		addLeaves(root);

		// right boundary in reverse order
		Deque<Integer> dq = new ArrayDeque<>();
		Node curr = root.right;
		while (curr != null) {
			if (!isLeaf(curr)) {
				dq.push(curr.data);
			}
			curr = curr.right != null ? curr.right : curr.left;
		}
		while (!dq.isEmpty()) {
			boundary.add(dq.pop());
		}
		return boundary;
	}

	private void addLeaves(Node node) {
		if (node == null) {
			return;
		}
		if (isLeaf(node)) {
			boundary.add(node.data);
		}
		addLeaves(node.left);
		addLeaves(node.right);
	}

	private boolean isLeaf(Node node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		BoundaryTraversalSubmit bt = new BoundaryTraversalSubmit();
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

		print(bt.boundary(root1)); // 1, 2, 4, 8, 9, 10, 11, 12, 13, 7, 3
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

		print(bt.boundary(root2)); // 12, 7, 4, 9, 15, 10, 6, 5, 1
		System.out.println();

		Node root3 = newNode(12);
		root3.right = newNode(1);
		root3.right.left = newNode(10);
		root3.right.right = newNode(5);
		print(bt.boundary(root3)); // 12, 10, 5, 1
		System.out.println();
	}
}

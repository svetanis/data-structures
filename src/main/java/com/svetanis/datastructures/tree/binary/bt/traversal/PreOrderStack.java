package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 144. Binary Tree Preorder Traversal

public final class PreOrderStack {

	public static List<Integer> preOrder(Node root) {
		// base case
		if (root == null) {
			return new ArrayList<>();
		}
		List<Integer> list = new ArrayList<>();
		Deque<Node> stack = new ArrayDeque<>();
		stack.push(root);

		// pop all items one by one.
		// do the following for every popped item
		// 1. print it
		// 2. push its right child
		// 3. push its left child
		// note that right child is pushed first
		// so that left is processed first
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			list.add(node.data);
			if (node.right != null) {
				stack.push(node.right);
			}
			if (node.left != null) {
				stack.push(node.left);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		print(preOrder(root));
	}
}

package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 94. Binary Tree Inorder Traversal

public final class InOrderStack {
	// Time complexity: O(n)
	// Space complexity: O(n)

	public static List<Integer> inOrder(Node root) {
		List<Integer> list = new ArrayList<>();
		Deque<Node> stack = push(new ArrayDeque<>(), root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			list.add(node.data);
			stack = push(stack, node.right);
		}
		return list;
	}

	private static Deque<Node> push(Deque<Node> stack, Node node) {
		while (node != null) {
			stack.push(node);
			node = node.left;
		}
		return stack;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		print(inOrder(root));
	}
}

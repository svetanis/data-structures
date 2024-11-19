package com.svetanis.datastructures.tree.binary.bst;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 530. Minimum Absolute Difference in BST

public final class MinAbsDiffBstIterative {
	// Time Complexity: O(n)

	private static final int INF = Integer.MAX_VALUE;

	public static int minDiff(Node root) {
		Deque<Node> stack = new ArrayDeque<>();
		Node curr = root;
		Node prev = null;
		int min = INF;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop();
			if (prev != null) {
				int diff = Math.abs(curr.data - prev.data);
				min = Math.min(min, diff);
			}
			prev = curr;
			curr = curr.right;
		}
		return min;
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(2);
		root.right = new Node(6);
		root.left.left = new Node(1);
		root.left.right = new Node(3);
		System.out.println(minDiff(root)); // 1

		Node root2 = new Node(1);
		root2.left = new Node(0);
		root2.right = new Node(48);
		root2.right.left = new Node(12);
		root2.right.right = new Node(49);
		System.out.println(minDiff(root2)); // 1

		// [236,104,701,null,227,null,911] expected 9

		Node root3 = new Node(236);
		root3.left = new Node(104);
		root3.right = new Node(701);
		root3.left.right = new Node(227);
		root3.right.right = new Node(911);
		System.out.println(minDiff(root3)); // 9

	}
}
package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 98. Validate Binary Search Tree

// given a Binary Tree (BT)
// determine if it is a BST or not

public final class ValidBstInOrderIterative {
	// Time complexity O(n);
	// Space complexity: O(h)

	public static boolean isValidBst(Node root) {
		Integer prev = null;
		Node curr = root;
		Deque<Node> dq = new ArrayDeque<>();
		while (!dq.isEmpty() || curr != null) {
			while (curr != null) {
				dq.push(curr);
				curr = curr.left;
			}
			curr = dq.pop();
			if (prev != null && curr.data <= prev) {
				return false;
			}
			prev = curr.data;
			curr = curr.right;
		}
		return true;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(isValidBst(root));

		Node root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.right = newNode(6);
		System.out.println(isValidBst(root2));
	}
}

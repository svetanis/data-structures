package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 103. Binary Tree Zigzag Level Order Traversal

public final class ZigZagTwoStack {
	// Time Complexity: O(n)

	public static List<List<Integer>> traverse(Node root) {
		List<List<Integer>> lists = new ArrayList<>();
		if (root == null) {
			return lists;
		}
		boolean leftToRight = true;
		Deque<Node> curr = new ArrayDeque<>();
		Deque<Node> next = new ArrayDeque<>();
		curr.push(root);
		List<Integer> list = new ArrayList<>();
		while (!curr.isEmpty()) {
			Node node = curr.pop();
			list.add(node.data);
			if (leftToRight) {
				if (node.left != null) {
					next.push(node.left);
				}
				if (node.right != null) {
					next.push(node.right);
				}
			} else {
				if (node.right != null) {
					next.push(node.right);
				}
				if (node.left != null) {
					next.push(node.left);
				}
			}

			if (curr.isEmpty()) {
				lists.add(new ArrayList<>(list));
				list.clear();
				leftToRight = !leftToRight;
				Deque<Node> temp = curr;
				curr = next;
				next = temp;
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(7);
		root.left.right = newNode(6);
		root.right.left = newNode(5);
		root.right.right = newNode(4);
		print(traverse(root));
	}
}

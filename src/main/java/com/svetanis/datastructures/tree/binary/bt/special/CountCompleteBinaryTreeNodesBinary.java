package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 222. Count Complete Binary Tree Nodes

// given the root of a complete binary tree
// return the number of the nodes in the tree

public final class CountCompleteBinaryTreeNodesBinary {

	public static int count(Node root) {
		if (root == null) {
			return 0;
		}
		int depth = depth(root);
		if (depth == 0) {
			return 1;
		}
		// binary search of last level
		int left = 0;
		int right = (int) Math.pow(2, depth) - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (exists(mid, depth, root)) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return (int) Math.pow(2, depth) - 1 + left;
	}

	private static boolean exists(int index, int depth, Node node) {
		int left = 0;
		int right = (int) Math.pow(2, depth) - 1;
		for (int i = 0; i < depth; i++) {
			int mid = left + (right - left) / 2;
			if (index <= mid) {
				node = node.left;
				right = mid;
			} else {
				node = node.right;
				left = mid + 1;
			}
		}
		return node != null;
	}

	private static int depth(Node root) {
		int depth = 0;
		while (root.left != null) {
			depth++;
			root = root.left;
		}
		return depth;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		System.out.println(count(root)); // 6

		Node root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}
}

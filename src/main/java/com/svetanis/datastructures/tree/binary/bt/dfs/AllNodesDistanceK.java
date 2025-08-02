package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 863. All Nodes Distance K in Binary Tree

public final class AllNodesDistanceK {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Integer> nodesDistK(Node root, Node target, int k) {
		List<Integer> list = new ArrayList<>();
		dfs(root, target, k, list);
		return list;
	}

	private static int dfs(Node node, Node target, int k, List<Integer> list) {
		if (node == null) {
			return -1;
		}
		if (node == target) {
			kDown(node, k, list);
			return 1;
		}
		int left = dfs(node.left, target, k, list);
		if (left != -1) {
			if (left == k) {
				list.add(node.data);
			}
			kDown(node.right, k - left - 1, list);
			return left + 1;
		}
		int right = dfs(node.right, target, k, list);
		if (right != -1) {
			if (right == k) {
				list.add(node.data);
			}
			kDown(node.left, k - right - 1, list);
			return right + 1;
		}
		return -1;
	}

	private static void kDown(Node node, int dist, List<Integer> list) {
		if (node == null || dist < 0) {
			return;
		}
		if (dist == 0) {
			list.add(node.data);
			return;
		}
		kDown(node.left, dist - 1, list);
		kDown(node.right, dist - 1, list);
	}

	public static void main(String[] args) {
		Node target = new Node(5);
		Node root = new Node(3);
		root.left = target;
		root.right = new Node(1);
		root.left.left = new Node(6);
		root.left.right = new Node(2);
		root.left.right.left = new Node(7);
		root.left.right.right = new Node(4);
		root.right.left = new Node(0);
		root.right.right = new Node(8);
		System.out.println(nodesDistK(root, target, 2));

		Node root2 = new Node(1);
		System.out.println(nodesDistK(root2, root2, 3));
	}
}

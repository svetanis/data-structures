package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1469. Find All The Lonely Nodes

public final class AllLonelyNodes {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Integer> lonelyNodes(Node root) {
		List<Integer> list = new ArrayList<>();
		dfs(root, list);
		return list;
	}

	private static void dfs(Node node, List<Integer> list) {
		if (node == null) {
			return;
		}
		if (node.left == null && node.right == null) {
			return;
		}
		if (node.left == null) {
			list.add(node.right.data);
		}
		if (node.right == null) {
			list.add(node.left.data);
		}
		dfs(node.left, list);
		dfs(node.right, list);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.right.left = new Node(5);
		root.right.left.left = new Node(6);
		System.out.println(lonelyNodes(root)); 
	}
}

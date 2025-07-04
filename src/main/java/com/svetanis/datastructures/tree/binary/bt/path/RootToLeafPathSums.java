package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, 
// print all of the path sums 
// from the root to leaf nodes.

public final class RootToLeafPathSums {
	// Time complexity: O(n)

	public static List<Integer> paths(Node root) {
		List<Integer> list = new ArrayList<>();
		dfs(root, 0, list);
		return list;
	}

	private static void dfs(Node node, int sum, List<Integer> list) {
		if (node == null) {
			return;
		}
		// add the current node to the sum
		sum += node.data;
		// if the current node is a leaf
		// save the current path sum
		if (node.left == null && node.right == null) {
			list.add(sum);
		} else {
			// traverse left subtree
			dfs(node.left, sum, list);
			// traverse right subtree
			dfs(node.right, sum, list);
		}
	}

	public static void main(String[] args) {
		// 10->8->3
		// 10->8->5
		// 10->2->2
		Node root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		System.out.println(paths(root)); // 21, 23, 14
		System.out.println();
	}
}

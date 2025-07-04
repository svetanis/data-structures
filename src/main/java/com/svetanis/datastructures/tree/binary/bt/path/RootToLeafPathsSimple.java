package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 257. Binary Tree Paths

// Given a binary tree, 
// print all of the paths 
// from the root to leaf nodes.

public final class RootToLeafPathsSimple {
	// Time complexity: O(n)

	public static List<String> paths(Node root) {
		List<String> paths = new ArrayList<>();
		dfs(root, "", paths);
		return paths;
	}

	private static void dfs(Node node, String path, List<String> paths) {
		if (node == null) {
			return;
		}
		// add the current node to the path
		path += node.data;
		// if the current node is a leaf
		// save the current path
		if (node.left == null && node.right == null) {
			paths.add(path);
		} else {
			// traverse left subtree
			dfs(node.left, path + "->", paths);
			// traverse right subtree
			dfs(node.right, path + "->", paths);
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

		inOrder(root);
		System.out.println();

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		System.out.println(paths(root));
		System.out.println();
	}
}

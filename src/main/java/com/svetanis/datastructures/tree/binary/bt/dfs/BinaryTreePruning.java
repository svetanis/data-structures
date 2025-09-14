package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 814. Binary Tree Pruning

public final class BinaryTreePruning {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static Node pruneTree(Node root) {
		if (root == null) {
			return null;
		}
		root.left = pruneTree(root.left);
		root.right = pruneTree(root.right);
		if (root.left == null && root.right == null && root.data == 0) {
			return null;
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(0);
		root.left.left = newNode(0);
		root.left.right = newNode(0);
		root.right = newNode(1);
		root.right.left = newNode(0);
		root.right.right = newNode(1);
		Nodes.preOrder(pruneTree(root)); // 1 1 1 
		System.out.println();

		Node root2 = newNode(1);
		root2.right = newNode(0);
		root2.right.left = newNode(0);
		root2.right.right = newNode(1);
		Nodes.preOrder(pruneTree(root2)); // 1 0 1 
	}
}

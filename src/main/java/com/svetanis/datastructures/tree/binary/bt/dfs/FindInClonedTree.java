package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1379. Find a Corresponding Node of a Binary Tree in a Clone of That Tree

public final class FindInClonedTree {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private Node target;

	public final Node targetCopy(Node original, Node cloned, Node target) {
		this.target = target;
		return dfs(original, cloned);
	}

	private Node dfs(Node original, Node cloned) {
		if (original == null) {
			return null;
		}
		if (original == target) {
			return cloned;
		}

		Node left = dfs(original.left, cloned.left);
		if (left != null) {
			return left;
		}
		return dfs(original.right, cloned.right);
	}

	public static void main(String[] args) {
		Node target = new Node(3);
		FindInClonedTree fct = new FindInClonedTree();
		Node root = new Node(7);
		root.left = new Node(4);
		root.right = target;
		root.right.left = new Node(6);
		root.right.right = new Node(19);

		Node root1 = new Node(7);
		root1.left = new Node(4);
		root1.right = target;
		root1.right.left = new Node(6);
		root1.right.right = new Node(19);

		System.out.println(fct.targetCopy(root, root1, target)); // 3
	}
}

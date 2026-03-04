package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1676. Lowest Common Ancestor of a Binary Tree IV

public final class LcaBTIV {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	private Set<Integer> set;

	public Node lca(Node root, Node[] nodes) {
		this.set = new HashSet<>();
		for (Node node : nodes) {
			set.add(node.data);
		}
		return dfs(root);
	}

	private Node dfs(Node root) {
		if (root == null || set.contains(root.data)) {
			return root;
		}
		if (root.left == null && root.right == null) {
			return null;
		}
		Node left = dfs(root.left);
		Node right = dfs(root.right);
		if (left != null && right != null) {
			return root;
		}
		if (left != null) {
			return left;
		}
		return right;
	}

	public static void main(String[] args) {
		LcaBTIV lcabt = new LcaBTIV();
		Node left = newNode(7);
		Node right = newNode(4);
		Node root = newNode(3);
		root.left = newNode(5);
		root.right = newNode(1);
		root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		inOrder(root);
		System.out.println();
		System.out.println(lcabt.lca(root, new Node[] { left, right }));
		System.out.println();
	}
}

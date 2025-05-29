package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1315. Sum of Nodes with Even-Valued Grandparent

public final class SumOfNodesWithEvenValuedGrandparent {
	// Time Complexity: O(n)

	public static int sumTree(Node root) {
		return dfs(root, null, null);
	}

	private static int dfs(Node root, Node parent, Node gparent) {
		if (root == null) {
			return 0;
		}
		int val = (gparent != null && gparent.data % 2 == 0) ? root.data : 0;
		return val + dfs(root.left, root, parent) + dfs(root.right, root, parent);
	}

	public static void main(String[] args) {
		Node root = newNode(6);
		root.left = newNode(7);
		root.right = newNode(8);
		root.left.left = newNode(2);
		root.left.right = newNode(7);
		root.right.left = newNode(1);
		root.right.right = newNode(3);
		root.left.left.left = newNode(9);
		root.left.right.left = newNode(1);
		root.left.right.right = newNode(4);
		root.right.right.right = newNode(5);
		System.out.println(sumTree(root)); // 18
	}
}

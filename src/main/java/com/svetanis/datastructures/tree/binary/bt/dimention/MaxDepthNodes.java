package com.svetanis.datastructures.tree.binary.bt.dimention;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 104. Maximum Depth of Binary Tree

// Max Depth of a Binary tree
// is the longest root-to-leaf path.

// Max depth is the number of nodes along 
// the longest path from the root node to
// the farthest leaf node

public final class MaxDepthNodes {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	// the return value is the node-count of
	// the longest subroot-to-leaf path in the
	// current subtree after visiting a node
	private static int dfs(Node root) {
		if (isNull(root)) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		return 1 + max(left, right);
	}

	public static void main(String[] args) {
		Node root = new Node(5);
		root.left = new Node(4);
		root.right = new Node(6);
		root.left.left = new Node(3);
		root.left.right = new Node(8);
		System.out.println(dfs(root)); // 3

		Node root2 = new Node(3);
		root2.left = new Node(9);
		root2.right = new Node(20);
		root2.right.left = new Node(15);
		root2.right.right = new Node(7);
		System.out.println(dfs(root2)); // 3

		Node root3 = new Node(1);
		root3.right = new Node(2);
		System.out.println(dfs(root3)); // 2
	}
}

package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 104. Maximum Depth of Binary Tree

// Max Depth of a Binary tree
// is the longest root-to-leaf path.

// Max depth is the number of nodes along 
// the longest path from the root node to
// the farthest leaf node

public final class MaxDepthTopDown {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	// the return value is the node-count of
	// the longest subroot-to-leaf path in the
	// current subtree after visiting a node

	public static int maxDepth(Node root) {
		// root's height = 1
		return dfs(root, 1);
	}

	private static int dfs(Node root, int height) {
		if (isNull(root)) {
			return height - 1;
		}
		int left = dfs(root.left, height + 1);
		int right = dfs(root.right, height + 1);
		return max(left, right);
	}

	public static void main(String[] args) {
		Node root = new Node(5);
		root.left = new Node(4);
		root.right = new Node(6);
		root.left.left = new Node(3);
		root.left.right = new Node(8);
		System.out.println(maxDepth(root)); // 3

		Node root2 = new Node(3);
		root2.left = new Node(9);
		root2.right = new Node(20);
		root2.right.left = new Node(15);
		root2.right.right = new Node(7);
		System.out.println(maxDepth(root2)); // 3

		Node root3 = new Node(1);
		root3.right = new Node(2);
		System.out.println(maxDepth(root3)); // 2
	}
}

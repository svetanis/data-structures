package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Max Depth of a Binary tree
// is the longest root-to-leaf path.

// given a binary tree, find its max depth.
// here we define the length of the path to 
// be the number of edges on that path, not
// the number of nodes

public final class MaxDepthEdges {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	public static int maxDepth(Node root) {
		return isNull(root) ? 0 : dfs(root) - 1;
	}

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
		System.out.println(maxDepth(root)); // 2
	}
}

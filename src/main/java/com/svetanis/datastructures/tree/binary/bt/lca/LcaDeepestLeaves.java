package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1123. Lowest Common Ancestor of Deepest Leaves
// 865. Smallest Subtree with all the Deepest Nodes

public final class LcaDeepestLeaves {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node lca(Node root) {
		return dfs(root).node;
	}

	private static Result dfs(Node node) {
		if (node == null) {
			return new Result(null, 0);
		}
		Result left = dfs(node.left);
		Result right = dfs(node.right);
		int ldepth = left.depth;
		int rdepth = right.depth;
		if (ldepth > rdepth) {
			return new Result(left.node, ldepth + 1);
		}
		if (ldepth < rdepth) {
			return new Result(right.node, rdepth + 1);
		}
		return new Result(node, ldepth + 1);
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(5);
		root.right = newNode(1);
		root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		Nodes.preOrder(lca(root)); // 2 7 4
	}

	public static class Result {
		private Node node;
		private int depth;

		public Result(Node node, int depth) {
			this.node = node;
			this.depth = depth;
		}
	}
}

package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Max Depth of a Binary Tree, counted in edges (LeetCode 104 is the same question counted in
// nodes).
//
// Input: the root of a binary tree. Returns the length of the longest root-to-leaf path as the
// number of edges on it, not the number of nodes: a single node has depth 0, an empty tree 0.
//
// The one idea: count nodes first, then take one off. dfs(node) is the number of nodes on the
// longest downward path starting at node: 0 for an empty subtree, otherwise 1 for node plus the
// longer of the two children's counts. A path of k nodes has k - 1 edges.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: MaxDepthNodes is the same dfs without the subtraction (104).

public final class MaxDepthEdges {

	public static int maxDepth(TreeNode root) {
		return isNull(root) ? 0 : dfs(root) - 1; // RETURN: nodes on the path, minus one
	}

	// the return value is the node-count of
	// the longest subroot-to-leaf path in the
	// current subtree after visiting a node
	private static int dfs(TreeNode root) {
		if (isNull(root)) {
			return 0; // BASE: an empty subtree has no nodes
		}
		int left = dfs(root.left); // WALK: nodes on the longest path down the left
		int right = dfs(root.right); // WALK: nodes on the longest path down the right
		return 1 + max(left, right); // RETURN: this node plus the longer side
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(4);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(8);
		System.out.println(maxDepth(root)); // 2
		System.out.println(maxDepth(new TreeNode(1))); // 0
		System.out.println(maxDepth(null)); // 0
	}
}

package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1123. Lowest Common Ancestor of Deepest Leaves
// 865. Smallest Subtree with all the Deepest Nodes
//
// Input: root -- the root of a non-empty binary tree. Returns the lowest node whose subtree holds
// every one of the deepest leaves (the leaves farthest from the root). The two LeetCode problems
// are the same question. On LeetCode: 1123 has 1 <= nodes <= 1000 and 0 <= Node.val <= 1000;
// 865 has 1 <= nodes <= 500 and 0 <= Node.val <= 500; values are distinct.
//
// The one idea: each call answers two things about its subtree -- how deep it is (the number of
// nodes on its longest downward path) and which node is the lowest one holding all of its deepest
// leaves. A missing child has depth 0 and no node. At a node, compare the two sides: if the left is
// deeper, every deepest leaf of this subtree is on the left, so the answer is the left side's
// answer; likewise on the right. If the two depths are equal, the deepest leaves are on both sides,
// so this node is the lowest one holding them all. The depth passed up is one more than the deeper
// side.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: LcaBtNoParentBottomUp finds the LCA of two given nodes with the same bottom-up shape.

public final class LcaDeepestLeaves {

	public static TreeNode lca(TreeNode root) {
		return dfs(root).node;
	}

	private static Result dfs(TreeNode node) {
		if (node == null) { // BASE: depth 0, no node
			return new Result(null, 0);
		}
		Result left = dfs(node.left); // SEARCH: left side
		Result right = dfs(node.right); // SEARCH: right side
		int ldepth = left.depth;
		int rdepth = right.depth;
		if (ldepth > rdepth) { // LEFT DEEPER: all deepest leaves are on the left
			return new Result(left.node, ldepth + 1);
		}
		if (ldepth < rdepth) { // RIGHT DEEPER: all deepest leaves are on the right
			return new Result(right.node, rdepth + 1);
		}
		return new Result(node, ldepth + 1); // TIE: deepest leaves on both sides (or a leaf) -- here
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(5);
		root.right = newNode(1);
		root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		Nodes.preOrder(lca(root)); // prints 2 7 4
	}

	public static class Result {
		private TreeNode node;
		private int depth;

		public Result(TreeNode node, int depth) {
			this.node = node;
			this.depth = depth;
		}
	}
}

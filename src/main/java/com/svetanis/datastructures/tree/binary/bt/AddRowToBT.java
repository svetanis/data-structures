package com.svetanis.datastructures.tree.binary.bt;

import com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 623. Add One Row to Tree
//
// Input: root of a binary tree, a value val and a depth. Insert one row of nodes holding val at
// that depth (the root is depth 1): every node at depth - 1 gets a new left child holding val
// whose left child is the old left subtree, and a new right child holding val whose right child
// is the old right subtree. depth == 1 is the special case: a new root holding val takes the
// whole old tree as its left child. Returns the root. On LeetCode: 1 <= number of nodes <= 10^4,
// tree depth <= 10^4, -100 <= node value <= 100, -10^5 <= val <= 10^5, 1 <= depth <= (tree depth) + 1.
//
// The one idea: one node is a value plus a left link and a right link, so putting a row in means
// re-pointing links. Walk down counting depth; the nodes at depth - 1 are the parents of the new
// row. For each parent, build two new nodes: the new left node takes the old left child as its
// LEFT child, the new right node takes the old right child as its RIGHT child, and the parent
// points at the two new nodes. The old subtrees hang from the new nodes without being copied.
// Depth 1 has no parent to re-point, so it is handled before the walk.
//
// Time: O(n) -- the walk visits every node once; it does not stop after the row is added.
// Space: O(h) -- the recursion stack, h = tree depth (up to n for a chain); the 2 new nodes per parent are the output.

public final class AddRowToBT {

	public static TreeNode addRow(TreeNode root, int val, int depth) {
		if (depth == 1) { // SPECIAL CASE: the new root takes the old tree as its left child
			TreeNode node = new TreeNode(val);
			node.left = root;
			return node;
		}
		dfs(root, val, depth, 1);
		return root;
	}

	private static void dfs(TreeNode root, int val, int depth, int current) {
		if (root == null) { // BASE: nothing to re-point below a missing child
			return;
		}
		if (current == depth - 1) { // INSERT: this node is a parent of the new row
			TreeNode node1 = new TreeNode(val);
			node1.left = root.left; // old left subtree becomes the new left node's LEFT child
			TreeNode node2 = new TreeNode(val);
			node2.right = root.right; // old right subtree becomes the new right node's RIGHT child
			root.left = node1;
			root.right = node2;
		}
		dfs(root.left, val, depth, current + 1); // WALK: left, then right
		dfs(root.right, val, depth, current + 1);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(1);
		root.right.left = new TreeNode(5);
		TreeNode root2 = addRow(root, 1, 2);
		System.out.println(LotQueue.traverse(root2)); // [4, 1, 1, 2, 6, 3, 1, 5]

		TreeNode root3 = new TreeNode(4);
		root3.left = new TreeNode(2);
		root3.left.left = new TreeNode(3);
		root3.left.right = new TreeNode(1);
		TreeNode root4 = addRow(root3, 1, 3);
		System.out.println(LotQueue.traverse(root4)); // [4, 2, 1, 1, 3, 1]
	}
}

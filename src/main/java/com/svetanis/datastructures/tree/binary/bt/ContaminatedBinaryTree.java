package com.svetanis.datastructures.tree.binary.bt;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1261. Find Elements in a Contaminated Binary Tree
//
// Input: root of a tree whose every node value was overwritten with -1. The tree was valid once:
// root.val == 0, a node with value x had left child 2 * x + 1 and right child 2 * x + 2. The
// constructor recovers the values; find(target) says whether the recovered tree holds target.
// On LeetCode: tree height <= 20, 1 <= number of nodes <= 10^4, 0 <= target <= 10^6.
//
// The one idea: a node's value is fixed by its place, so nothing was lost. The root is 0; going
// left from x gives 2x + 1, going right gives 2x + 2. The constructor walks the tree once, writes
// each node's value from its parent's and puts every value in a hash set. A value that is not in
// the set is not in the tree, so find is one lookup.
//
// Time: O(n) for the constructor, O(1) for find -- one visit per node, one hash lookup per query.
// Space: O(n) -- the set holds one value per node; the recursion is as deep as the tree (<= 20).

public final class ContaminatedBinaryTree {

	private Set<Integer> values; // every recovered value in the tree

	public ContaminatedBinaryTree(TreeNode root) {
		root.val = 0; // BASE: the root is always 0
		this.values = new HashSet<>();
		this.values.add(0);
		dfs(root);
	}

	private void dfs(TreeNode node) {
		this.values.add(node.val); // RECORD: this node's value
		if (node.left != null) {
			node.left.val = 2 * node.val + 1; // RECOVER: left child of x is 2x + 1
			dfs(node.left);
		}
		if (node.right != null) {
			node.right.val = 2 * node.val + 2; // RECOVER: right child of x is 2x + 2
			dfs(node.right);
		}
	}

	public boolean find(int target) {
		return values.contains(target); // LOOKUP: in the tree exactly when it was recorded
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(-1);
		root.left = new TreeNode(-1);
		root.right = new TreeNode(-1);
		root.left.left = new TreeNode(-1);
		root.left.right = new TreeNode(-1);

		ContaminatedBinaryTree cbt = new ContaminatedBinaryTree(root);
		System.out.println(cbt.find(1)); // true
		System.out.println(cbt.find(3)); // true
		System.out.println(cbt.find(5)); // false
		System.out.println();

		TreeNode root3 = new TreeNode(-1);
		root3.right = new TreeNode(-1);
		root3.right.left = new TreeNode(-1);
		root3.right.left.left = new TreeNode(-1);

		ContaminatedBinaryTree cbt2 = new ContaminatedBinaryTree(root3);
		System.out.println(cbt2.find(2)); // true
		System.out.println(cbt2.find(3)); // false
		System.out.println(cbt2.find(4)); // false
		System.out.println(cbt2.find(5)); // true
	}
}

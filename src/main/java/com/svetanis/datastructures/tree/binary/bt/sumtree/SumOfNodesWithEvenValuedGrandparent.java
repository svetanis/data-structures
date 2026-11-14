package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1315. Sum of Nodes with Even-Valued Grandparent
//
// Input: root of a binary tree. A grandparent of a node is its parent's parent, if there is one.
// Returns the sum of the values of all nodes whose grandparent has an even value; 0 if there are
// none. On LeetCode: 1 <= number of nodes <= 10^4, 1 <= node.val <= 100.
//
// The one idea: a node cannot see upward, so pass the two nodes above it down as parameters. A
// call receives its node, the node's parent and the node's grandparent; it counts its own value
// when the grandparent exists and is even. Its children receive (child, this node, this node's
// parent), which slides the window of two ancestors down one level.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height.

public final class SumOfNodesWithEvenValuedGrandparent {

	public static int sumTree(TreeNode root) {
		return dfs(root, null, null); // START: the root has no parent and no grandparent
	}

	private static int dfs(TreeNode root, TreeNode parent, TreeNode gparent) {
		if (root == null) {
			return 0; // BASE: an empty spot adds nothing
		}
		int val = (gparent != null && gparent.val % 2 == 0) ? root.val : 0; // TAKE: counts when the grandparent is even
		return val + dfs(root.left, root, parent) + dfs(root.right, root, parent); // WALK: slide the window of ancestors down
	}

	public static void main(String[] args) {
		TreeNode root = newNode(6);
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

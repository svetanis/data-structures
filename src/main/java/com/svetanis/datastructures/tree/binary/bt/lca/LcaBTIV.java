package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1676. Lowest Common Ancestor of a Binary Tree IV
//
// Input: root -- the root of a binary tree; nodes -- an array of m nodes of that tree. Returns the
// lowest node that has every node of the array in its subtree, a node counting as part of its own
// subtree. On LeetCode: 1 <= nodes in the tree <= 10^4, -10^9 <= Node.val <= 10^9, all values
// distinct, and every node of the array exists in the tree.
//
// The one idea: it is the two-node search of LC 236 with "is this node p or q" replaced by "is this
// node in the set". A call returns null when no wanted node is below it, a wanted node when it
// stands on one, and the answer when wanted nodes are on both sides. A wanted node is returned
// at once without looking below it: if more wanted nodes are below it, this node is already their
// lowest common ancestor; if not, it is the only one. A leaf that is not wanted returns null.
// Two sides both returning something means the wanted nodes split here, so this node is the answer.
//
// Time: O(n + m) -- m to build the set of values, then each node visited once.
// Space: O(m + h) -- the set, and the recursion stack. h is lg n only on a balanced tree; a skewed
// one makes it n.
//
// Siblings: LcaBtNoParentBottomUp is the same search for exactly two nodes.

public final class LcaBtIV {

	private Set<Integer> set; // WANTED: values of the nodes whose ancestor is wanted

	public TreeNode lca(TreeNode root, TreeNode[] nodes) {
		this.set = new HashSet<>();
		for (TreeNode node : nodes) {
			set.add(node.val);
		}
		return dfs(root);
	}

	private TreeNode dfs(TreeNode root) {
		if (root == null || set.contains(root.val)) { // BASE: empty, or a wanted node -- stop here
			return root;
		}
		if (root.left == null && root.right == null) { // LEAF: not wanted, nothing below
			return null;
		}
		TreeNode left = dfs(root.left); // SEARCH: left side
		TreeNode right = dfs(root.right); // SEARCH: right side
		if (left != null && right != null) { // MEET: wanted nodes on both sides
			return root;
		}
		if (left != null) { // PASS UP: only one side found something
			return left;
		}
		return right;
	}

	public static void main(String[] args) {
		LcaBtIV lcabt = new LcaBtIV();
		TreeNode left = newNode(7);
		TreeNode right = newNode(4);
		TreeNode root = newNode(3);
		root.left = newNode(5);
		root.right = newNode(1);
		root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		inOrder(root);
		System.out.println(); // 6 5 7 2 4 3 0 1 8
		System.out.println(lcabt.lca(root, new TreeNode[] { left, right })); // 2
		System.out.println();
	}
}

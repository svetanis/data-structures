package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 236. Lowest Common Ancestor of a Binary Tree
//
// Input: root -- the root of a binary tree; p, q -- two nodes of it. Returns the lowest node that
// has both p and q in its subtree, a node counting as part of its own subtree. On LeetCode:
// 2 <= nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values distinct, p != q, and both exist in the
// tree.
//
// The one idea: no call can see the answer, so each call reports the most useful node under it --
// p, q, or the answer already found -- or null for nothing. A call that is p or q returns itself
// without looking below: if the other one is below, this node is the answer; if not, this node is
// the report "found one". If both children report a node, p and q are on different sides, so this
// node is the answer; if one does, its report is passed up unchanged.
//
// p and q are matched by identity (root == p), not by value. That needs no promise about distinct
// values: on a tree holding the same value twice, matching by value can take a different node for
// p and report the meeting point too high.
//
// Time: O(n) -- each node is visited at most once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n,
// and a line of 10^5 nodes overflows the default call stack.
//
// Known limit: the early return is safe only because both nodes are promised to exist. With one
// missing (LC 1644) it returns the other as if it were the ancestor.
//
// Siblings: LcaBtNoParentBottomUp is the same search matching by value, with an int entry point
// that also answers empty when a value is missing. LcaBtIV generalises to a set of nodes.
// LcaBtParent, LcaBtParentLevel and LcaBtParentStack answer the same question when each node knows
// its parent.

public final class LcaBtNoParentBottomUpByNode {

	public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) { // BASE: nothing here
			return null;
		}
		if (root == p || root == q) { // FOUND: no need to look below -- the other is promised to exist
			return root;
		}
		TreeNode left = lca(root.left, p, q); // SEARCH: what the left side found
		TreeNode right = lca(root.right, p, q); // SEARCH: what the right side found
		if (left != null && right != null) { // MEET: one on each side -- this node is the answer
			return root;
		}
		return left != null ? left : right; // PASS UP: whichever side found something, or null
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		TreeNode five = root.left = newNode(5);
		TreeNode one = root.right = newNode(1);
		TreeNode six = root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		TreeNode four = root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		System.out.println(lca(root, five, one)); // 3
		System.out.println(lca(root, five, four)); // 5
		System.out.println(lca(root, six, four)); // 5

		// the same value twice: the 5 on the left is not p
		TreeNode dup = newNode(7);
		dup.left = newNode(5);
		TreeNode q = dup.right = newNode(3);
		TreeNode p = dup.right.left = newNode(5);
		System.out.println(lca(dup, p, q)); // 3 -- matching by value says 7
	}
}

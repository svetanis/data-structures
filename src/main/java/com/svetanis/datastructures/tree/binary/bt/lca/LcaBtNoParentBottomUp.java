package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isAbsent;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 236. Lowest Common Ancestor of a Binary Tree
//
// Input: root -- the root of a binary tree; p, q -- two nodes (or, in the int overload, two values).
// Returns the lowest node that has both p and q in its subtree, a node counting as part of its own
// subtree. On LeetCode: 2 <= nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values distinct, p != q,
// and both exist in the tree. The int overload also answers empty when either value is missing,
// which LeetCode never asks.
//
// The one idea: ask each subtree "does it hold p or q, and if so which node is the answer so far?"
// A call returns null when neither is below it, p or q itself when it finds one, and the LCA when
// both are below it. At a node: if the node is p or q, return it -- the other one, if it is below,
// does not matter, because this node is already the lowest ancestor of both. Otherwise look left and
// right; if both sides returned something, p and q sit on different sides, so this node is where
// they meet; if only one side returned something, pass that up unchanged (it is either the one
// found node or the LCA already found lower down).
//
// The search runs on TreeNode objects, never on int values. -1 is a legal node value, so no int can also
// mean "not found"; null can, because no node is ever null.
//
// Time: O(n) -- each node is visited once; the int overload scans the tree two more times to check
// that both values exist.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: LcaBtNoParentBottomUpByNode is this search matching by identity, so it needs no
// promise of distinct values. LcaBtIV generalises to a set of nodes. LcaBtParent, LcaBtParentLevel and
// LcaBtParentStack answer the same question when each node knows its parent. LcaDeepestLeaves finds
// the LCA of the deepest leaves. DistBetweenTwoNodes and DistBetweenTwoNodesEfficient use this.

public final class LcaBtNoParentBottomUp {

	public static Optional<Integer> lca(TreeNode root, int p, int q) {
		if (isNull(root) || isAbsent(root, p) || isAbsent(root, q)) { // MISSING: nothing to find
			return Optional.empty();
		}
		TreeNode lca = lca(root, newNode(p), newNode(q)); // SEARCH: on nodes, never on ints
		return isNull(lca) ? Optional.empty() : Optional.of(lca.val);
	}

	public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		if (isNull(root) || isNull(p) || isNull(q)) { // BASE: nothing below
			return null;
		}
		if (root.val == p.val || root.val == q.val) { // FOUND: this node is p or q
			return root;
		}
		TreeNode left = lca(root.left, p, q); // SEARCH: left side
		TreeNode right = lca(root.right, p, q); // SEARCH: right side
		if (isNotNull(left) && isNotNull(right)) { // MEET: p and q are on different sides
			return root;
		}
		// PASS UP: only one side found something -- one of p, q, or the LCA already
		return isNotNull(left) ? left : right;
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
		inOrder(root);
		System.out.println();
		System.out.println(lca(root, 5, 1)); // Optional[3]
		System.out.println(lca(root, 6, 4)); // Optional[5]
		System.out.println(lca(root, 4, 10)); // Optional.empty
		System.out.println();

		// -1 is a legal node value
		TreeNode negative = newNode(3);
		negative.left = newNode(5);
		negative.right = newNode(1);
		negative.left.left = newNode(-1);
		negative.left.right = newNode(2);
		negative.right.right = newNode(8);
		System.out.println(lca(negative, -1, 8)); // Optional[3]
		System.out.println(lca(negative, -1, 2)); // Optional[5]
		System.out.println();

		TreeNode root2 = newNode(3);
		root2.left = newNode(1);
		root2.right = newNode(5);
		root2.right.right = newNode(8);
		System.out.println(lca(root2, 5, 7)); // Optional.empty
	}
}

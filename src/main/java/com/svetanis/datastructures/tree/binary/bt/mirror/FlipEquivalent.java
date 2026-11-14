package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 951. Flip Equivalent Binary Trees
//
// Input: root1, root2 -- the roots of two binary trees, possibly null. A flip swaps the left and
// right children of one node. Returns true if some set of flips turns the first tree into the
// second. On LeetCode: 0 <= nodes <= 100 in each tree, every value in a tree is distinct, in
// 0 <= Node.val <= 99.
//
// The one idea: two trees are flip equivalent when their roots hold the same value and the children
// pair up in one of two ways -- left with left and right with right (no flip at this node), or left
// with right and right with left (flip at this node) -- and each pair is itself flip equivalent. Two
// empty trees match; an empty tree and a non-empty one do not.
//
// Time: O(n) -- all four pairings are tried, but with distinct values a wrong pairing fails at its
// first comparison, so each node is matched in depth with at most one partner and costs about four
// calls. If values could repeat, wrong pairings would go deeper and the work could grow to O(n^2).
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: MirrorBinaryTrees requires the flip at every node; this file lets each node choose.

public final class FlipEquivalent {

	public static boolean flipEquivalent(TreeNode root1, TreeNode root2) {
		if (isNull(root1) && isNull(root2)) { // BASE: both empty
			return true;
		}
		if (isNull(root1) || isNull(root2)) { // BASE: only one is empty
			return false;
		}
		if (root1.val != root2.val) { // BASE: different values
			return false;
		}
		boolean leftLeft = flipEquivalent(root1.left, root2.left); // NO FLIP: sides stay
		boolean rightRight = flipEquivalent(root1.right, root2.right);
		boolean leftRight = flipEquivalent(root1.left, root2.right); // FLIP: sides cross
		boolean rightLeft = flipEquivalent(root1.right, root2.left);
		return (leftLeft && rightRight) || (leftRight && rightLeft); // EITHER pairing works
	}

	public static void main(String[] args) {

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		inOrder(root1);
		System.out.println(); // 4 2 5 1 3

		TreeNode root2 = newNode(1);
		root2.left = newNode(3);
		root2.right = newNode(2);
		root2.right.left = newNode(5);
		root2.right.right = newNode(4);
		inOrder(root2);
		System.out.println(); // 3 1 5 2 4

		System.out.println(flipEquivalent(root1, root2)); // true
	}
}

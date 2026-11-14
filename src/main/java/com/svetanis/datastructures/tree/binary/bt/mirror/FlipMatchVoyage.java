package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 971. Flip Binary Tree To Match Preorder Traversal
//
// Input: root -- the root of a binary tree of n nodes with distinct values; voyage -- the wanted
// preorder, n values. A flip swaps the left and right children of one node. Returns the values of
// the nodes flipped so that the tree's preorder equals voyage, or [-1]
// if no flips can do it. On LeetCode: 1 <= n <= 100, 1 <= Node.val, voyage[i] <= n, all values
// distinct.
//
// The one idea: walk the tree in preorder and compare each node with the next value of voyage. The
// only choice is at a node with two children: the left child has to be the next value of the
// voyage, and if it is not, the right child must be -- so flip the node, which is recorded, and go
// right first. Which node is next in the voyage decides the flip, so no node is ever flipped
// without need and no flip is ever undone. A node whose value differs from the voyage means no
// flips can work: match becomes false and the walk stops.
//
// Time: O(n) -- each node is compared once.
// Space: O(n) -- the recursion stack (up to n deep on a skewed tree) and the list of flipped nodes.
//
// Siblings: FlipEquivalent compares two trees; this file compares one tree with a preorder list.

public final class FlipMatchVoyage {

	private int index; // NEXT: position in voyage of the value the next node must hold
	private boolean match;
	private int[] voyage;
	private List<Integer> flipped;

	public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
		this.index = 0;
		this.match = true;
		this.voyage = voyage;
		this.flipped = new ArrayList<>();
		if (root == null) {
			return flipped;
		}
		dfs(root);
		return match ? flipped : Arrays.asList(-1); // RETURN: [-1] when no flips can match
	}

	private void dfs(TreeNode root) {
		if (root == null || !match) { // BASE: nothing here, or already failed
			return;
		}
		if (root.val != voyage[index]) { // MISMATCH: no flips can fix this node
			match = false;
			return;
		}
		index += 1;
		if (root.left == null || root.left.val == voyage[index]) { // KEEP: left comes next
			dfs(root.left);
			dfs(root.right);
		} else { // FLIP: right must come next
			flipped.add(root.val);
			dfs(root.right);
			dfs(root.left);
		}
	}

	public static void main(String[] args) {
		FlipMatchVoyage fmv = new FlipMatchVoyage();
		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		System.out.println(fmv.flipMatchVoyage(root2, new int[] { 2, 1 })); // [-1]

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		System.out.println(fmv.flipMatchVoyage(root1, new int[] { 1, 3, 2 })); // [1]

		TreeNode root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		System.out.println(fmv.flipMatchVoyage(root3, new int[] { 1, 2, 3 })); // []
	}
}

package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Maximum Width of a Binary Tree, counting only real nodes (662 counts the empty positions
// between the end nodes too; this file does not).
//
// Input: the root of a binary tree. Returns the largest number of nodes on any one level
// (0 for an empty tree).
//
// The one idea: width(root, level) counts the nodes that sit exactly `level` steps down: the
// root alone at level 1, otherwise the count of the left subtree one level up plus the count of
// the right subtree one level up. Try every level from 1 to the height and keep the largest.
// Each level is a fresh walk from the root, which is simple but repeats work.
//
// Time: O(n * h) -- h levels, and the walk for level L visits every node above level L; O(n^2)
// on a chain.
// Space: O(h) -- the recursion stack.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack; MaxWidthQueue is the loop version.
//
// Siblings: MaxWidthQueue is a single level-by-level pass (O(n)); MaxWidthPreOrder counts every
// level in one recursive pass with a map; MaxWidth662 counts empty positions too.

public final class MaxWidthLevelOrder {

	public static int maxWidth(TreeNode root) {
		int max = 0;
		int height = height(root);

		// get width of each level and compare
		// the width with max width so far
		for (int i = 1; i <= height; i++) {
			int width = width(root, i); // COUNT: nodes on level i
			max = max(max, width); // RECORD: the widest level so far
		}
		return max;
	}

	private static int width(TreeNode root, int level) {
		if (isNull(root)) {
			return 0; // BASE: nothing here
		}
		if (level == 1) {
			return 1; // BASE: this node is on the wanted level
		} else {
			int left = width(root.left, level - 1); // WALK: one level closer
			int right = width(root.right, level - 1);
			return left + right; // RETURN: nodes on the wanted level below this node
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(8);
		root.right.right.left = newNode(6);
		root.right.right.right = newNode(7);
		System.out.println(maxWidth(root)); // 3
		System.out.println(maxWidth(null)); // 0
	}
}

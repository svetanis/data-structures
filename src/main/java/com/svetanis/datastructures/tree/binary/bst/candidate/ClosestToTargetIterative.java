package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 270. Closest Binary Search Tree Value (a variant: ties are not broken toward the smaller value)
//
// Input: root of a BST and an int k. Values and k are assumed to lie in -10^9..10^9, as LC 270
// keeps them, so abs(k - val) fits in an int. Returns the value in the tree closest to k; when two
// values are equally close, the one the descent met first. Returns -1 for an empty tree.
//
// The one idea: the closest value is on the path a search for k would take. At a node above k,
// everything in its right subtree is larger still, so farther away: go left. At a node below k,
// go right. Every node on the path is a candidate, and the best distance so far is kept.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- two variables.
//
// Siblings: ClosestToTarget is the one that implements LC 270 exactly (a double target, the smaller
// value on a tie, and it throws on an empty tree). ClosestToTargetRecursive and
// ClosestToTargetRecursive2 are this loop as recursion, with the same tie rule.

public final class ClosestToTargetIterative {

	public static int closestToK(TreeNode root, int k) {
		if (isNull(root)) {
			return -1; // BASE: no tree, no answer
		}

		int key = -1;
		int min = MAX_VALUE;
		while (isNotNull(root)) {
			if (root.val == k) {
				return root.val; // RETURN: an exact match cannot be beaten
			}
			int diff = abs(k - root.val);
			if (min > diff) { // RECORD: strictly nearer, so on a tie the first one met stays
				min = diff;
				key = root.val;
			}
			if (k < root.val) {
				root = root.left; // WALK: everything on the right is farther
			} else {
				root = root.right; // WALK: everything on the left is farther
			}
		}
		return key;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(9);
		root.left = newNode(4);
		root.right = newNode(17);
		root.left.left = newNode(3);
		root.left.right = newNode(6);
		root.left.right.left = newNode(5);
		root.left.right.right = newNode(7);
		root.right.right = newNode(22);
		root.right.right.left = newNode(20);
		System.out.println(closestToK(root, 4)); // 4
		System.out.println(closestToK(root, 18)); // 17
		System.out.println(closestToK(root, 12)); // 9
		System.out.println(closestToK(root, 2)); // 3
	}
}

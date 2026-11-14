package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 270. Closest Binary Search Tree Value
//
// Input: root of a BST and a target. On LeetCode: 1 <= number of nodes <= 10^4, 0 <= node.val <=
// 10^9, -10^9 <= target <= 10^9 (a double). Returns the value in the tree closest to target; when
// two values are equally close, the smaller. LeetCode guarantees at least one node, so a null root
// is not handled: it throws a NullPointerException, because every int is a legal answer and there
// is no value to return for "none".
//
// The one idea: the closest value is on the path a search for target would take. At a node whose
// value is above target, everything in its right subtree is larger still, so farther away than the
// node itself: go left. At a node whose value is at or below target, everything in its left subtree
// is smaller still: go right. Every node on that path is a candidate and every node off it is
// beaten by one on it, so the path is walked once, keeping the best candidate so far.
//
// The walk does not meet candidates in order of distance -- it can pass the closest node and then
// record worse ones -- so every node is compared against the best so far instead of overwriting it.
// On a tie, the comparison on values keeps the smaller one.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- two variables.
//
// Siblings: ClosestToTargetIterative, ClosestToTargetRecursive and ClosestToTargetRecursive2 take an
// int k, keep whichever of two equally close values the descent met first (for 6 above 4 and target
// 5 they answer 6, where LC 270 wants 4), and answer -1 on an empty tree.

public final class ClosestToTarget {

	public static int closestToTarget(TreeNode root, double target) {
		int closest = root.val;
		double minDiff = Double.MAX_VALUE;
		while (root != null) {
			double currDiff = Math.abs(target - root.val);
			// a tie needs the value comparison: `<=` alone keeps the last one met
			boolean equals = currDiff == minDiff && root.val < closest;
			if (currDiff < minDiff || equals) { // RECORD: nearer than the best so far, or tied and smaller
				minDiff = currDiff;
				closest = root.val;
			}
			if (root.val > target) {
				root = root.left; // WALK: everything on the right is farther
			} else {
				root = root.right; // WALK: everything on the left is farther
			}
		}
		return closest; // RETURN: the best candidate on the path
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(closestToTarget(root, 3.714286)); // 4 -- the first node met, then passed
		System.out.println(closestToTarget(root, 4.5)); // 4 -- tie, smaller met first
		System.out.println(closestToTarget(root, 3.5)); // 3 -- tie, larger met first
		System.out.println(closestToTarget(root, 2.5)); // 2 -- tie, smaller met first
	}
}

package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 543. Diameter of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 1 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the diameter: the number of edges on the longest path between
// any two nodes. The path may or may not pass through the root.
//
// The one idea: the longest path has a highest node, the one where it turns. So try every node
// as the turning point. height(node) returns how many nodes are on the longest path that starts
// at node and only goes down (0 for an empty subtree); for a child that is also the number of
// edges from node down to the bottom of that child's path. A path that turns at node has
// left + right edges. Every node reports that candidate into a one-cell counter and the biggest
// wins.
//
// Time: O(n) -- each node is visited once and does constant work.
// Space: O(h) -- the recursion stack, h is the height of the tree (up to n on a chain).
//
// Siblings: DiameterCountEdges keeps the best in a field instead of an AtomicInteger, so it is
// one object per call; this file is all static. DiameterCountNodes counts nodes, not edges.

public final class DiameterSubmit {

	public static int diameter(TreeNode root) {
		AtomicInteger diameter = new AtomicInteger(); // BEST: one cell shared by the whole walk
		height(root, diameter);
		return diameter.get();
	}

	private static int height(TreeNode root, AtomicInteger diameter) {
		if (root == null) {
			return 0; // BASE: an empty subtree has no nodes on any path
		}
		int left = height(root.left, diameter); // WALK: longest drop on the left
		int right = height(root.right, diameter); // WALK: longest drop on the right
		int d = left + right; // TURN: a path that bends at this node
		int max = Math.max(diameter.get(), d);
		diameter.set(max); // RECORD: best turn so far
		return 1 + Math.max(left, right); // RETURN: this node plus the longer drop
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		System.out.println(diameter(root)); // 3
		TreeNode pair = newNode(1);
		pair.left = newNode(2);
		System.out.println(diameter(pair)); // 1
	}
}

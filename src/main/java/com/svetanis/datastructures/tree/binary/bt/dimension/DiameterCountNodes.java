package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Diameter of a Binary Tree, counted in nodes (543 asks for the same path counted in edges;
// this answer is that one plus 1, and 0 for an empty tree).
//
// Input: the root of a binary tree. Returns the number of nodes on the longest path between
// any two nodes. The path may or may not pass through the root.
//
// The one idea: the longest path has a highest node, the one where it turns. So try every node
// as the turning point. height(node) returns how many nodes are on the longest path that starts
// at node and only goes down (0 for an empty subtree). A path that turns at node holds the
// longest drop on the left, node itself, and the longest drop on the right: 1 + left + right
// nodes. Every node reports that candidate into a one-cell counter and the biggest wins.
//
// Time: O(n) -- each node is visited once and does constant work.
// Space: O(h) -- the recursion stack, h is the height of the tree (up to n on a chain).
//
// Siblings: DiameterCountEdges and DiameterSubmit are the LeetCode 543 versions (edges).

public final class DiameterCountNodes {

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

		int d = 1 + left + right; // TURN: a path that bends at this node, node included
		int max = max(diameter.get(), d);
		diameter.set(max); // RECORD: best turn so far
		return 1 + max(left, right); // RETURN: this node plus the longer drop
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.right.left = newNode(5);
		root.right.right = newNode(6);
		// 5 nodes: 4-2-1-3-5. DiameterCountEdges would say 4 on this tree --
		// same path, counted in edges. The file names say which is which.
		System.out.println(diameter(root)); // 5

		root.left.left = null;
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(8);
		root.right.right.left = newNode(9);
		root.right.left.right.left = newNode(10);
		root.right.right.left.left = newNode(11);
		System.out.println(diameter(root)); // 7
	}
}

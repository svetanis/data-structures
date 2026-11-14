package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 543. Diameter of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 1 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the diameter: the number of edges on the longest path between
// any two nodes. The path may or may not pass through the root.
//
// The one idea: the longest path has a highest node, the one where it turns. So try every node
// as the turning point. dfs(node) returns how many nodes are on the longest path that starts at
// node and only goes down (0 for an empty subtree). For a child, that count is also the number
// of edges from node down to the bottom of that child's path. A path that turns at node uses
// the longest drop on the left plus the longest drop on the right, so its edge count is
// left + right. Every node reports that candidate and the biggest one wins. The same dfs gives
// its parent the longer of the two drops plus one for node itself.
//
// Time: O(n) -- each node is visited once and does constant work.
// Space: O(h) -- the recursion stack, h is the height of the tree (up to n on a chain).
//
// Siblings: DiameterSubmit is the same method with a static helper and an AtomicInteger in
// place of the field. DiameterCountNodes counts the same path in nodes instead of edges.

public final class DiameterCountEdges {

	private int maxDiameter;

	public int diameter(TreeNode root) {
		this.maxDiameter = 0; // BASE: reset, so one instance can answer many trees
		dfs(root);
		return maxDiameter;
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty subtree has no nodes on any path
		}
		int left = dfs(root.left); // WALK: longest drop on the left
		int right = dfs(root.right); // WALK: longest drop on the right
		int diameterThroughNode = left + right; // TURN: a path that bends at this node
		maxDiameter = Math.max(maxDiameter, diameterThroughNode); // RECORD: best turn so far
		return 1 + Math.max(left, right); // RETURN: this node plus the longer drop
	}

	public static void main(String[] args) {
		DiameterCountEdges dop = new DiameterCountEdges();
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		System.out.println(dop.diameter(root)); // 3
		TreeNode pair = newNode(1);
		pair.left = newNode(2);
		System.out.println(dop.diameter(pair)); // 1
		System.out.println(dop.diameter(newNode(7))); // 0
	}
}

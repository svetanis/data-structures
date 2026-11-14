package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 111. Minimum Depth of Binary Tree
//
// Input: root of a binary tree. The depth counts NODES, not edges: a single node has depth 1.
// Returns the number of nodes on the shortest path from the root down to the nearest leaf (a node
// with no children); an empty tree gives 0. On LeetCode: 0 <= number of nodes <= 10^5,
// -1000 <= node value <= 1000.
//
// The one idea (lot): one queue entry is one node waiting to be read. At the start of each round
// the queue holds exactly one whole level, and every node in it is minDepth nodes from the root.
// The first leaf met in this top-to-bottom reading is on the shallowest level that has a leaf, so
// its level number is the answer and the walk stops there. A node with only one child is not a
// leaf, so a path may not end there.
//
// The one idea (minDepth): the answer for a node is 1 + the answer for its nearer child -- but
// only a child that exists counts. With one child missing the other child's answer is used (the
// missing side is not a path to a leaf); with both present, the smaller of the two.
//
// Time: O(n) for both -- lot stops at the first leaf; minDepth visits every node once.
// Space: O(w) for lot, w = the widest level (up to n / 2); O(h) for minDepth, the recursion stack,
//   h = the height (up to n for a chain). On a chain of 10^5 nodes, which LeetCode allows, minDepth
//   overflows the stack of a plain JVM main thread; lot does not.
//
// Siblings: none; this class holds both ways to find the minimum depth -- the queue walk (lot) and
// the recursion (minDepth). Plain Java, so either method pastes into LeetCode as it is.

public final class MinDepthSubmit {

	public static int lot(TreeNode root) {
		// an empty tree is 0 levels deep. Without this, the null goes
		// into the queue and the first poll reads through it
		if (root == null) {
			return 0;
		}
		int minDepth = 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			// count the level BEFORE draining it: the nodes about to be
			// polled are minDepth nodes from the root, root included
			minDepth++; // DEPTH: the level about to be read
			int size = queue.size(); // SIZE: the queue holds exactly one level
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll(); // TAKE: one node of this level
				if (node.left == null && node.right == null) { // FOUND: the first leaf is on the shallowest leaf level
					return minDepth;
				}
				if (node.left != null) {
					queue.offer(node.left); // QUEUE: the next level
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return minDepth;
	}

	public static int minDepth(TreeNode root) {
		if (root == null) { // BASE: an empty tree has depth 0
			return 0;
		}
		if (root.left == null) { // ONE CHILD: the path must go through the one that exists
			return 1 + minDepth(root.right);
		}
		if (root.right == null) {
			return 1 + minDepth(root.left);
		}
		return 1 + Math.min(minDepth(root.left), minDepth(root.right)); // BOTH: the nearer leaf
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);
		System.out.println(lot(root)); // 3 -- NODES: root, 3, 6
		System.out.println(minDepth(root)); // 3 -- the same tree, the same answer
	}
}

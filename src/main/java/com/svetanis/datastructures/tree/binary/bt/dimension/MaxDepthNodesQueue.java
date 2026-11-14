package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 104. Maximum Depth of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the maximum depth: the number of nodes along the longest path
// from the root down to the farthest leaf (0 for an empty tree).
//
// The one idea: the depth is the number of levels. A queue holds exactly one level at a time:
// take its size, pop that many nodes, push their children (the next level), and count one level.
// When the queue runs dry, the count is the number of levels. No recursion, so a chain of any
// length is safe.
//
// Time: O(n) -- each node is pushed and popped once.
// Space: O(n) -- the widest level sits in the queue; up to about n / 2 nodes.
//
// Siblings: MaxDepthNodes, MaxDepthTopDown and MaxDepthTopDownGlobal are the recursive
// versions; this one is the iterative one.

public final class MaxDepthNodesQueue {

	public static int lot(TreeNode root) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree has no levels
		}
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.offer(root);
		int height = 0;
		while (!queue.isEmpty()) {
			int size = queue.size(); // LEVEL: exactly the nodes of the current level
			height++; // COUNT this level
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (isNotNull(node.left)) {
					queue.offer(node.left); // PUSH the next level
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
		}
		return height; // RETURN: number of levels
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);
		System.out.println(lot(root)); // 3
		TreeNode root2 = new TreeNode(3);
		root2.left = new TreeNode(9);
		root2.right = new TreeNode(20);
		root2.right.left = new TreeNode(15);
		root2.right.right = new TreeNode(7);
		System.out.println(lot(root2)); // 3
		System.out.println(lot(null)); // 0
	}
}

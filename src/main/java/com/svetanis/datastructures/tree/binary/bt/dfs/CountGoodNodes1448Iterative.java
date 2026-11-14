package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1448. Count Good Nodes in Binary Tree
//
// Input: root of a binary tree. A node is good when no node on the path from the root to it
// holds a larger value. Returns the number of good nodes; the root is always good. On LeetCode:
// 1 <= number of nodes <= 10^5, -10^4 <= node.val <= 10^4.
//
// The one idea: whether a node is good depends only on the largest value above it, so keep that
// number next to each node that is waiting to be visited. Pop a node with its number; it is good
// when its value is at least that number; its children go on the stack with the larger of the
// two. This is the walk of CountGoodNodes1448 with the recursion replaced by an explicit stack,
// so a tree that is one long chain no longer overflows the call stack.
//
// Time: O(n) -- every node is pushed and popped once.
// Space: O(h) -- the stack holds the unvisited children along the current path, at most two per
// level.
//
// Siblings: CountGoodNodes1448 is the recursive version (and names this file for its stack
// limit); CountVisibleNodes is the same count under another name.

public final class CountGoodNodes1448Iterative {

	public static int count(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int count = 0;
		Deque<TreeNode> nodes = new ArrayDeque<>(); // STACK: nodes still to visit
		Deque<Integer> maxes = new ArrayDeque<>(); // the largest value above each stacked node
		nodes.push(root);
		maxes.push(Integer.MIN_VALUE); // START: nothing above the root, so the root is good
		while (!nodes.isEmpty()) {
			TreeNode node = nodes.pop();
			int max = maxes.pop();
			if (max <= node.val) {
				count++; // TAKE: nothing larger above, this node is good
				max = node.val; // RAISE: the children must beat this value
			}
			if (node.right != null) {
				nodes.push(node.right); // WALK: the children carry the maximum so far
				maxes.push(max);
			}
			if (node.left != null) {
				nodes.push(node.left);
				maxes.push(max);
			}
		}
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(1);
		root.right = newNode(4);
		root.left.left = newNode(3);
		root.right.left = newNode(1);
		root.right.right = newNode(5);
		System.out.println(count(root)); // 4

		TreeNode root1 = newNode(3);
		root1.left = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(count(root1)); // 3

		TreeNode root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}
}

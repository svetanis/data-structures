package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 863. All Nodes Distance K in Binary Tree
//
// Input: root of a binary tree, a target node of that tree, and k. Returns the values of every
// node exactly k edges from the target, in any order. On LeetCode: 1 <= number of nodes <= 500,
// 0 <= node.val <= 500, all values are unique, the target is a node of the tree,
// 0 <= k <= 1000.
//
// The one idea: a node at distance k from the target is one of two kinds. It is below the target
// -- k steps down -- or it hangs off an ancestor of the target: the ancestor a steps above the
// target, and then k - a steps down the other child's side. So one walk down finds the target,
// and each call returns on the way back up how far its node is above the target, or -1 when the
// target is not in that subtree. If the left child returns d, this node is d edges above the
// target; it is an answer when d == k, and its other child is d + 1 edges from the target, so
// k - d - 1 steps down from that child finish the job. The target is found by identity (==), so
// equal values elsewhere in the tree
// cannot confuse it.
//
// Time: O(n) -- each node is visited by the search walk once, and each kDown walk covers a
// subtree that no other kDown walk covers.
// Space: O(h) -- the recursion stack, h the height; the answer list holds the output.
//
// Siblings: AllNodesDistanceKBfs turns the tree into an undirected graph and runs a breadth-first
// search out from the target; AllNodesDistanceKDfs does the same with a parent map and a
// depth-first search. Both mark visited nodes by value, so they need the unique values LeetCode
// guarantees; this file does not.

public final class AllNodesDistanceK {

	public static List<Integer> nodesDistK(TreeNode root, TreeNode target, int k) {
		List<Integer> list = new ArrayList<>();
		dfs(root, target, k, list); // WALK: find the target, answer on the way back up
		return list;
	}

	private static int dfs(TreeNode node, TreeNode target, int k, List<Integer> list) {
		if (node == null) {
			return -1; // BASE: the target is not below an empty spot
		}
		if (node == target) {
			kDown(node, k, list); // RECORD: the nodes k steps below the target
			return 1; // RETURN: the parent is 1 above the target
		}
		int left = dfs(node.left, target, k, list); // left = this node's distance above the target
		if (left != -1) {
			if (left == k) {
				list.add(node.val); // RECORD: this node itself is k above the target
			}
			kDown(node.right, k - left - 1, list); // RECORD: the other side, the rest of the way down
			return left + 1; // RETURN: the parent is one step further above
		}
		int right = dfs(node.right, target, k, list); // mirror of the left case
		if (right != -1) {
			if (right == k) {
				list.add(node.val); // RECORD: this node itself is k above the target
			}
			kDown(node.left, k - right - 1, list); // RECORD: the other side, the rest of the way down
			return right + 1; // RETURN: the parent is one step further above
		}
		return -1; // RETURN: the target is not in this subtree
	}

	private static void kDown(TreeNode node, int dist, List<Integer> list) {
		if (node == null || dist < 0) {
			return; // BASE: past a leaf, or already closer to the target than k
		}
		if (dist == 0) {
			list.add(node.val); // RECORD: exactly dist steps below the start
			return;
		}
		kDown(node.left, dist - 1, list); // WALK: one step down, one step less to go
		kDown(node.right, dist - 1, list);
	}

	public static void main(String[] args) {
		TreeNode target = new TreeNode(5);
		TreeNode root = new TreeNode(3);
		root.left = target;
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(6);
		root.left.right = new TreeNode(2);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(4);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(8);
		System.out.println(nodesDistK(root, target, 2)); // [7, 4, 1]

		TreeNode root2 = new TreeNode(1);
		System.out.println(nodesDistK(root2, root2, 3)); // []
	}
}

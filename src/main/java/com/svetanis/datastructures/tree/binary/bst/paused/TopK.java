package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// K Largest Elements in a BST (not a LeetCode problem)
//
// Input: the root of a binary search tree and an integer k. Returns the k largest nodes in
// descending order (all the nodes when the tree has fewer than k; an empty list for k <= 0).
//
// The one idea: the REVERSE in-order walk -- right subtree, node, left subtree -- visits the
// nodes from the largest down, so the first k nodes visited are the answer. Add each node as
// it is visited, and stop descending as soon as the list holds k.
//
// Time: O(h + k) -- the walk goes down the right side to the largest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h + k) -- the recursion stack and the answer list.
//
// Siblings: SumKthLargest is the same walk adding the values instead of listing the nodes.

public final class TopK {

	public static List<TreeNode> topK(TreeNode node, int k) {
		List<TreeNode> list = new ArrayList<>();
		topK(node, k, list);
		return list;
	}

	private static void topK(TreeNode root, int k, List<TreeNode> list) {
		// perform reverse in-order traversal
		if (isNotNull(root) && list.size() < k) { // BASE: empty subtree, or k nodes already taken
			topK(root.right, k, list); // WALK: larger values first
			if (list.size() < k) {
				list.add(root); // TAKE: the next largest
				topK(root.left, k, list); // WALK: smaller values
			}
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		System.out.println(topK(root, 4)); // [6, 5, 4, 3]
	}
}
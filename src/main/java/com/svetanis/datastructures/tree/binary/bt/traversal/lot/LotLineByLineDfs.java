package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 102. Binary Tree Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level, top level first, each level
// left to right, as a list of lists. On LeetCode: 0 <= number of nodes <= 2000,
// -1000 <= node value <= 1000.
//
// The one idea: lists.get(d) holds the values of depth d, depth 0 being the root. A depth-first
// walk carries each node's depth down: the root is depth 0, a child is its parent's depth + 1.
// The first time the walk reaches a new depth, lists has one list too few, so a fresh list is
// added; the node's value then goes at the end of lists.get(depth). Going left before right
// means that, inside one depth, the values are appended left to right.
//
// Time: O(n) -- each node is visited once and its value appended once.
// Space: O(n) -- the output; the recursion is as deep as the tree (up to n for a chain).
//
// Siblings: LotLineByLineQueue reads a level at a time with a queue; LotLineByLineHashing keeps a
// map from depth to values; LotLineByLineRecursive walks the tree once per level.

public final class LotLineByLineDfs {

	public static List<List<Integer>> traverse(TreeNode root) {
		List<List<Integer>> lists = new ArrayList<>();
		dfs(root, 0, lists);
		return lists;
	}

	private static void dfs(TreeNode node, int depth, List<List<Integer>> list) {
		if (node == null) { // BASE: nothing below a missing child
			return;
		}
		if (depth == list.size()) { // NEW DEPTH: first node seen at this depth
			list.add(new ArrayList<>());
		}
		list.get(depth).add(node.val); // RECORD: appended in left-to-right order
		dfs(node.left, depth + 1, list); // WALK: left before right
		dfs(node.right, depth + 1, list);
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(traverse(root)); // [[3], [9, 20], [15, 7]]
	}
}

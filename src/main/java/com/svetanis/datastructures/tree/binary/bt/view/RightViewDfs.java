package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 199. Binary Tree Right Side View
//
// Input: the root of a binary tree. Returns the values of the nodes visible when the tree is
// seen from the right side: the rightmost node of every row, from the top row down. On
// LeetCode: 0 <= number of nodes <= 100, -100 <= val <= 100.
//
// The one idea: walk depth first, right subtree before left subtree, counting rows from 0 at
// the root. The first node the walk reaches on any row is the rightmost one on it. The answer
// list has one entry per row already written, so a node on row `depth` is the first of its row
// exactly when the list's size equals `depth`.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (ten
// thousand levels) overflows the default stack. LeetCode allows at most 100 nodes, so it never
// happens there; RightViewLevelOrder has no such limit.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion depth is the tree height h, plus the output.
//
// Siblings: RightViewLevelOrder walks row by row with a queue; RightViewPreOrder is the same
// depth-first walk that keeps a deepest-row counter and returns TreeNode objects.

public final class RightViewDfs {

	private List<Integer> list; // ROWS: one value per row written so far

	public List<Integer> rightView(TreeNode root) {
		list = new ArrayList<>(); // RESET: a new list, so a second call starts empty and the first answer is left intact
		dfs(root, 0); // WALK: from the root, on row 0
		return list; // RETURN: the rightmost value of every row, top row first
	}

	private void dfs(TreeNode root, int depth) {
		if (root == null) {
			return; // BASE: past a leaf
		}
		if (list.size() == depth) {
			list.add(root.val); // RECORD: first node reached on this row
		}
		dfs(root.right, depth + 1); // WALK: right first
		dfs(root.left, depth + 1);
	}

	public static void main(String[] args) {
		RightViewDfs rv = new RightViewDfs();
		TreeNode root = newNode(12);
		root.left = newNode(10);
		root.left.left = newNode(25);
		root.left.right = newNode(40);
		System.out.println(rv.rightView(root)); // [12, 10, 40]

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.right = newNode(5);
		root2.right.right = newNode(4);
		System.out.println(rv.rightView(root2)); // [1, 3, 4]
	}
}

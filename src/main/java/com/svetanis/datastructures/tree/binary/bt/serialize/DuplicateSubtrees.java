package com.svetanis.datastructures.tree.binary.bt.serialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 652. Find Duplicate Subtrees
//
// Input: the root of a binary tree. On LeetCode: 1 to 5000 nodes, -200 <= val <= 200.
// Returns: for every kind of subtree that occurs two or more times (same shape, same values),
// the root of any one of them; the order does not matter.
//
// The one idea: two subtrees are the same exactly when their texts are the same, where the text of
// a node is "value,<text of left>,<text of right>" and a missing child is "#". The text is
// complete enough to rebuild the subtree, so equal text means equal subtree, and the commas keep
// "1,2" and "12" apart. Each node is visited after its children, so its text is built from theirs.
// A map counts how many times each text has been seen; the moment a text is seen the second time,
// that node is added to the answer (not on the third time, so each kind is reported once).
//
// Time: O(n^2) -- every node builds a text as long as its subtree, so a chain of n nodes builds
// texts of length about n, n-1, ... in total.
// Space: O(n^2) -- the map keeps one text per distinct subtree; O(n log n) on a balanced tree.
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 5,000 nodes (the
// LeetCode limit) overflows the default stack; this only happens on very large input.
//
// Siblings: DuplicateSubtreesIterative is the same method with an explicit stack.

public final class DuplicateSubtrees {

	public static List<TreeNode> duplicateSubtrees(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		dfs(root, list, map);
		return list;
	}

	private static String dfs(TreeNode root, List<TreeNode> list, Map<String, Integer> map) {
		if (root == null) { // BASE: a missing child has the text "#"
			return "#";
		}
		String key = root.val + "," + dfs(root.left, list, map) + "," + dfs(root.right, list, map); // TEXT: children first
		map.put(key, map.getOrDefault(key, 0) + 1); // COUNT: how often this text has been seen
		if (map.get(key) == 2) { // REPORT: on the second sighting only
			list.add(root);
		}
		return key;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(4);
		root.right.left.left = new TreeNode(4);
		System.out.println(duplicateSubtrees(root)); // [4, 2]

		TreeNode root1 = new TreeNode(2);
		root1.left = new TreeNode(2);
		root1.right = new TreeNode(2);
		root1.left.left = new TreeNode(3);
		root1.right.left = new TreeNode(3);
		System.out.println(duplicateSubtrees(root1)); // [3, 2]

		TreeNode root2 = new TreeNode(2);
		root2.left = new TreeNode(1);
		root2.right = new TreeNode(1);
		System.out.println(duplicateSubtrees(root2)); // [1]
	}
}

package com.svetanis.datastructures.tree.binary.bt.serialize;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 652. Find Duplicate Subtrees, without recursion
//
// Input: the root of a binary tree. On LeetCode: 1 to 5000 nodes, -200 <= val <= 200.
// Returns: for every kind of subtree that occurs two or more times (same shape, same values),
// the root of any one of them; the order does not matter.
//
// The one idea: two subtrees are the same exactly when their texts are the same, where the text of
// a node is "value,<text of left>,<text of right>" and a missing child is "#". The text is
// complete enough to rebuild the subtree, so equal text means equal subtree, and the commas keep
// "1,2" and "12" apart. A node's text needs its children's texts, so children must be finished
// first. This file gets that order without recursion: a stack walk writes the nodes down as node,
// right, left; read backwards that list is left, right, node, so every node comes after both of
// its children. A map counts how many times each text has been seen; the moment a text is seen the
// second time, that node is added to the answer (not on the third time, so each kind is reported
// once).
//
// Time: O(n^2) -- every node builds a text as long as its subtree, so a chain of n nodes builds
// texts of length about n, n-1, ... in total.
// Space: O(n^2) -- the map keeps one text per distinct subtree; O(n log n) on a balanced tree.
//
// Siblings: DuplicateSubtrees is the same method with recursion; it overflows the default stack on
// a chain of about 5,000 nodes, this file does not.

public final class DuplicateSubtreesIterative {

	private static final String MISSING = "#";

	public static List<TreeNode> duplicateSubtrees(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		Map<TreeNode, String> texts = new HashMap<>(); // text of a finished node, until its parent reads it
		List<TreeNode> order = preOrderRightFirst(root);
		for (int i = order.size() - 1; i >= 0; i--) { // WALK: children before their parent
			TreeNode node = order.get(i);
			String key = node.val + "," + take(texts, node.left) + "," + take(texts, node.right); // TEXT: from the children's texts
			texts.put(node, key);
			map.put(key, map.getOrDefault(key, 0) + 1); // COUNT: how often this text has been seen
			if (map.get(key) == 2) { // REPORT: on the second sighting only
				list.add(node);
			}
		}
		return list;
	}

	// node, then the right subtree, then the left subtree; read backwards it is left, right, node
	private static List<TreeNode> preOrderRightFirst(TreeNode root) {
		List<TreeNode> order = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			order.add(node); // VISIT: node before its subtrees
			if (node.left != null) { // PUSH: left first, so right pops first
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		return order;
	}

	// the finished text of a child, which its parent now uses and no one else needs
	private static String take(Map<TreeNode, String> texts, TreeNode child) {
		if (child == null) { // BASE: a missing child has the text "#"
			return MISSING;
		}
		return texts.remove(child);
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

package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 222. Count Complete Tree Nodes
//
// Input: the root of a complete binary tree: every level except possibly the last is full, and the
// last level has its nodes as far left as possible. Returns the number of nodes. On LeetCode:
// 0 <= number of nodes <= 5 * 10^4, 0 <= Node.val <= 5 * 10^4, and the answer should be faster than
// O(n). The input must be complete; the method is not defined for other shapes.
//
// The one idea: compare the heights of the left and right subtrees, each measured by walking left
// only. If they are equal, the last level reaches into the right subtree, so the left subtree is
// full all the way down: it holds 2^h - 1 nodes, h its height, and with the root that makes 2^h
// nodes; only the right subtree is still unknown, so count it the same way. If the left subtree is
// taller, the last level stops inside the left subtree, so the right subtree is full and one level
// shorter: with the root it holds 2^(h-1) nodes, h the left height, and only the left subtree is
// unknown. Either way one side is counted by a formula and the other by one more call, so there
// is one call per level.
//
// Time: O(log^2 n) -- the tree has about log n levels, one call each, and each call walks down
// the left spine twice, at most log n steps each.
// Space: O(log n) -- the recursion stack; a complete tree is log n levels tall.
//
// Siblings: CountCompleteBinaryTreeNodesBinary -- the same count by binary search over the last
// level, also O(log^2 n).

public final class CountCompleteBinaryTreeNodes {

	public static int count(TreeNode root) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree has no nodes
		}
		int left = height(root.left); // MEASURE: left spine of the left subtree
		int right = height(root.right); // MEASURE: left spine of the right subtree
		if (left == right) {
			return (1 << left) + count(root.right); // TAKE: left side full, root counted in 2^left; walk right
		} else {
			return (1 << right) + count(root.left); // TAKE: right side full, root counted in 2^right; walk left
		}
	}

	private static int height(TreeNode root) {
		int height = 0;
		while (isNotNull(root)) {
			height++;
			root = root.left; // WALK: down the left spine
		}
		return height;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		System.out.println(count(root)); // 6

		TreeNode root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}
}

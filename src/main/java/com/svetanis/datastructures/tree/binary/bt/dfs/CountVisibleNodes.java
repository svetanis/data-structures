package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Count Visible Nodes in a Binary Tree (not a LeetCode problem; it is 1448, Count Good Nodes in
// Binary Tree, under another name)
//
// Input: root of a binary tree. A node is visible when no node on the path from the root to it
// holds a larger value; the root is always visible. Returns the number of visible nodes. No
// stated limits; the range of the values may include Integer.MIN_VALUE.
//
// The one idea: whether a node is visible depends only on the largest value above it, so carry
// that one number down as a parameter. A node is visible when its value is at least the carried
// maximum; the children get the larger of the two. Starting at Integer.MIN_VALUE makes the root
// visible without a special case.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Known limit: a chain of about 10^5 nodes overflows the default stack; CountGoodNodes1448Iterative
// is the same count with an explicit stack.
//
// Siblings: CountGoodNodes1448 is the same walk, updating the maximum inside the if.

public final class CountVisibleNodes {

	public static int countVisible(TreeNode root) {
		return dfs(root, MIN_VALUE); // START: nothing above the root, so the root is visible
	}

	private static int dfs(TreeNode node, int max) {
		if (isNull(node)) {
			return 0; // BASE: an empty spot has no visible nodes
		}
		int count = 0;
		if (node.val >= max) {
			count += 1; // TAKE: nothing larger above, this node is visible
		}
		count += dfs(node.left, max(max, node.val)); // WALK: left subtree with the maximum so far
		count += dfs(node.right, max(max, node.val));
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(4);
		root.right = newNode(6);
		root.left.left = newNode(3);
		root.left.right = newNode(8);
		System.out.println(countVisible(root)); // 3

		TreeNode root1 = newNode(-100);
		root1.left = newNode(-500);
		root1.left.left = newNode(-50);
		System.out.println(countVisible(root1)); // 2

		TreeNode root3 = newNode(9);
		root3.left = newNode(8);
		root3.right = newNode(6);
		root3.left.left = newNode(11);
		root3.left.right = newNode(20);
		System.out.println(countVisible(root3)); // 3

		TreeNode root4 = newNode(5);
		root4.left = newNode(8);
		root4.right = newNode(6);
		root4.left.left = newNode(3);
		root4.left.right = newNode(8);
		System.out.println(countVisible(root4)); // 4

		TreeNode root5 = newNode(3);
		root5.left = newNode(1);
		root5.right = newNode(1);
		root5.left.left = newNode(3);
		root5.left.right = newNode(3);
		root5.right.left = newNode(2);
		root5.right.left.left = newNode(5);
		System.out.println(countVisible(root5)); // 4

		TreeNode root6 = newNode(5);
		root6.left = newNode(8);
		root6.right = newNode(6);
		root6.left.left = newNode(3);
		root6.left.right = newNode(9);
		root6.left.right.left = newNode(7);
		root6.left.right.left.left = newNode(8);
		System.out.println(countVisible(root6)); // 4
	}
}
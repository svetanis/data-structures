package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 222. Count Complete Tree Nodes
//
// Input: the root of a complete binary tree: every level except possibly the last is full, and the
// last level has its nodes as far left as possible. Returns the number of nodes. On LeetCode:
// 0 <= number of nodes <= 5 * 10^4, 0 <= Node.val <= 5 * 10^4, and the answer should be faster than
// O(n). The input must be complete; the method is not defined for other shapes.
//
// The one idea: walking left from the root gives the depth d of the tree (the root is depth 0), so
// every level above the last is full and holds 2^d - 1 nodes in total. The last level has room for
// 2^d nodes, numbered 0 to 2^d - 1 from the left, and in a complete tree its nodes fill the numbers
// from 0 up to some point with no gap. So "does last-level node number k exist?" is true for the
// first stretch of numbers and false after it, and binary search finds where it flips. To test one
// number, walk from the root: at each level the numbers are split in two halves, the left half
// below the left child and the right half below the right child, so d steps reach the spot (a null
// there means the node does not exist). The answer is 2^d - 1 plus the count of numbers that exist.
//
// Time: O(log^2 n) -- the binary search takes about d = log n steps, each testing one number with a
// walk of d steps.
// Space: O(1) -- loops only.
//
// Siblings: CountCompleteBinaryTreeNodes -- the same count by comparing subtree heights, also
// O(log^2 n).

public final class CountCompleteBinaryTreeNodesBinary {

	public static int count(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty tree has no nodes
		}
		int depth = depth(root);
		if (depth == 0) {
			return 1; // BASE: a lone root
		}
		// binary search of last level
		int left = 0;
		int right = (int) Math.pow(2, depth) - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (exists(mid, depth, root)) {
				left = mid + 1; // SEARCH: number mid exists, so look further right
			} else {
				right = mid - 1; // SEARCH: it does not, so look further left
			}
		}
		return (int) Math.pow(2, depth) - 1 + left; // RETURN: the full levels plus the last-level nodes
	}

	// does last-level node number index exist? walk down, halving the range of numbers each step
	private static boolean exists(int index, int depth, TreeNode node) {
		int left = 0;
		int right = (int) Math.pow(2, depth) - 1;
		for (int i = 0; i < depth; i++) {
			int mid = left + (right - left) / 2;
			if (index <= mid) {
				node = node.left; // WALK: the number is in the left half
				right = mid;
			} else {
				node = node.right; // WALK: the number is in the right half
				left = mid + 1;
			}
		}
		return node != null;
	}

	private static int depth(TreeNode root) {
		int depth = 0;
		while (root.left != null) {
			depth++;
			root = root.left; // WALK: down the left spine
		}
		return depth;
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

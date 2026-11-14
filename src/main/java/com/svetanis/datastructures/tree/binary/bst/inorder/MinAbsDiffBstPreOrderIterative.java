package com.svetanis.datastructures.tree.binary.bst.inorder;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 530. Minimum Absolute Difference in BST
//
// Input: the root of a binary search tree with at least two nodes. Returns the smallest
// absolute difference between the values of any two different nodes. On LeetCode: 2 to 10^4
// nodes, 0 <= node.val <= 10^5. (LC 783, Minimum Distance Between BST Nodes, is the same
// problem.)
//
// The one idea: in sorted order the closest pair of values is always two NEIGHBOURS. Every
// node is compared with its nearest smaller ancestor (low) and its nearest larger ancestor
// (high); a node that has a subtree is paired with that subtree's nearest value when the
// subtree's node is visited. One stack entry holds one node still to visit together with the
// low and high bounds that apply to it: going left, the node becomes the new high; going
// right, it becomes the new low. A side with no ancestor has no bound, which is null (not
// Integer.MAX_VALUE: a node holding that value would be taken for "no bound"). Visiting order
// does not matter, so entries come off the stack in any order.
//
// Assumption: no two neighbours differ by more than Integer.MAX_VALUE. The gap is computed
// in int arithmetic, so a wider gap overflows and comes back as a plausible small number.
// LC's range [0, 100000] cannot reach it.
//
// Time: O(n) -- each node is pushed once, popped once and compared with at most two bounds.
// Space: O(h) -- the stack holds at most one waiting right child per level of the path being
// walked, plus the current node's two children.
//
// Siblings: MinAbsDiffBstPreOrder is the same walk, recursive; its call stack overflows on
// a one-sided chain of about six thousand nodes, this one does not.

public final class MinAbsDiffBstPreOrderIterative {

	private static final int INF = Integer.MAX_VALUE; // "no gap found yet"

	private record Frame(TreeNode node, Integer low, Integer high) {
	}

	public static int minDiff(TreeNode root) {
		int min = INF;
		Deque<Frame> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(new Frame(root, null, null)); // no ancestor yet on either side
		}
		while (!stack.isEmpty()) {
			Frame f = stack.pop(); // VISIT: any waiting node
			TreeNode node = f.node();
			if (f.low() != null) {
				min = Math.min(min, Math.abs(node.val - f.low())); // GAP: to the nearest smaller ancestor
			}
			if (f.high() != null) {
				min = Math.min(min, Math.abs(node.val - f.high())); // GAP: to the nearest larger ancestor
			}
			if (node.left != null) {
				stack.push(new Frame(node.left, f.low(), node.val)); // GO LEFT: this node is the new high
			}
			if (node.right != null) {
				stack.push(new Frame(node.right, node.val, f.high())); // GO RIGHT: this node is the new low
			}
		}
		return min;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		System.out.println(minDiff(root)); // 1

		TreeNode root2 = new TreeNode(1);
		root2.left = new TreeNode(0);
		root2.right = new TreeNode(48);
		root2.right.left = new TreeNode(12);
		root2.right.right = new TreeNode(49);
		System.out.println(minDiff(root2)); // 1

		// [236,104,701,null,227,null,911]
		TreeNode root3 = new TreeNode(236);
		root3.left = new TreeNode(104);
		root3.right = new TreeNode(701);
		root3.left.right = new TreeNode(227);
		root3.right.right = new TreeNode(911);
		System.out.println(minDiff(root3)); // 9

		// the larger of the two nodes holds Integer.MAX_VALUE, which is also INF; its pair
		// with 5 is still compared because a missing bound is null, not INF
		TreeNode root4 = new TreeNode(Integer.MAX_VALUE);
		root4.left = new TreeNode(5);
		System.out.println(minDiff(root4)); // 2147483642
	}
}
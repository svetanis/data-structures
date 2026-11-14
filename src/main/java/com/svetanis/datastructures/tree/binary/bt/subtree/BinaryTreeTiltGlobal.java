package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 563. Binary Tree Tilt
//
// Input: the root of a binary tree. The tilt of one node is the absolute difference between the sum
// of the values in its left subtree and the sum of the values in its right subtree; an empty side
// counts as 0. Returns the sum of the tilts of all nodes. On LeetCode: 0 <= number of nodes <= 10^4,
// -1000 <= Node.val <= 1000. The answer is returned as an int, as on LeetCode, and is assumed to
// fit; a chain of 10^4 nodes all holding 1000 would overflow it.
//
// The one idea: one call answers "what is the sum of everything at or below this node?". A node's
// tilt needs that answer for its left child and for its right child, and those are exactly what the
// two calls on the children return. So a node takes the two sums, adds |left sum - right sum| to a
// running total, and returns its own value plus both sums for its parent. Children finish before
// their parent, so each sum is computed once, and the running total is the answer.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 10^4 nodes (each
// node the only child of the one before) overflows the default Java stack. It happens only on a
// very deep input; BinaryTreeTiltGlobalIterative is the same method with an explicit stack.
//
// Siblings: BinaryTreeTilt passes the running total down as an AtomicInteger instead of keeping it
// in a field. BinaryTreeTiltGlobalIterative is the same total without recursion.

public final class BinaryTreeTiltGlobal {

	private int sum; // TOTAL: the tilts added so far

	public int tilt(TreeNode root) {
		this.sum = 0; // RESET: a second call on this object starts from zero
		dfs(root); // WALK: every node adds its tilt to the total
		return sum; // RETURN: the total of all tilts
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		int left = dfs(root.left); // WALK: sum of the left subtree
		int right = dfs(root.right); // WALK: sum of the right subtree
		sum += Math.abs(left - right); // RECORD: this node's tilt joins the total
		return root.val + left + right; // RETURN: sum of this subtree, for the parent
	}

	public static void main(String[] args) {
		BinaryTreeTiltGlobal btt = new BinaryTreeTiltGlobal();
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		System.out.println(btt.tilt(root)); // 1

		TreeNode root1 = newNode(4);
		root1.left = newNode(2);
		root1.right = newNode(9);
		root1.left.left = newNode(3);
		root1.left.right = newNode(5);
		root1.right.right = newNode(7);
		System.out.println(btt.tilt(root1)); // 15
	}
}

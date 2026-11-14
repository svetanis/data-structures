package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.concurrent.atomic.AtomicInteger;

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
// Siblings: BinaryTreeTiltGlobal keeps the running total in a field instead of passing an
// AtomicInteger down. BinaryTreeTiltGlobalIterative is the same total without recursion.

public final class BinaryTreeTilt {

	public static int tilt(TreeNode root) {
		AtomicInteger sum = new AtomicInteger(0); // TOTAL: the tilts added so far
		dfs(root, sum); // WALK: every node adds its tilt to the total
		return sum.get(); // RETURN: the total of all tilts
	}

	private static int dfs(TreeNode root, AtomicInteger sum) {
		if(root == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
	  int left = dfs(root.left, sum); // WALK: sum of the left subtree
		int right = dfs(root.right, sum); // WALK: sum of the right subtree
		int tilt = Math.abs(left - right); // TILT: this node's tilt
		sum.addAndGet(tilt); // RECORD: add it to the total
		return root.val + left + right; // RETURN: sum of this subtree, for the parent
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		System.out.println(tilt(root)); // 1

		TreeNode root1 = newNode(4);
		root1.left = newNode(2);
		root1.right = newNode(9);
		root1.left.left = newNode(3);
		root1.left.right = newNode(5);
		root1.right.right = newNode(7);
		System.out.println(tilt(root1)); // 15
	}
}

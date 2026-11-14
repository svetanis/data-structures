package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 687. Longest Univalue Path
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 10^4,
// -1000 <= val <= 1000, the depth of the tree is at most 1000. Returns the number of edges on
// the longest path whose nodes all hold the same value. The path may or may not pass through
// the root.
//
// The one idea: the longest path has a highest node, the one where it turns. So try every node
// as the turning point. dfs(node) returns the length in edges of the longest downward path that
// starts at node and keeps node's value. A child continues that path only when its value equals
// node's: then the path is the child's own length plus the one edge down to it; otherwise the
// arm is 0. A path that turns at node joins its two arms, so it has left + right edges. Every
// node reports that candidate and the biggest wins; the parent only gets the longer arm back,
// since a path cannot fork.
//
// Time: O(n) -- each node is visited once and does constant work.
// Space: O(h) -- the recursion stack, h is the height of the tree.

public final class LongestUnivaluePath {

	private int maxLen;

	public int longestUnivaluePath(TreeNode root) {
		this.maxLen = 0; // BASE: reset, so one instance can answer many trees
		dfs(root);
		return maxLen;
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty subtree has no path
		}
		int left = dfs(root.left); // WALK: longest arm below the left child
		int right = dfs(root.right); // WALK: longest arm below the right child
		boolean lup = root.left != null && root.left.val == root.val;
		left = lup ? left + 1 : 0; // TAKE the left arm only if the child has the same value
		boolean rup = root.right != null && root.right.val == root.val;
		right = rup ? right + 1 : 0; // TAKE the right arm only if the child has the same value
		int pathThroughNode = left + right; // TURN: both arms joined at this node
		maxLen = Math.max(maxLen, pathThroughNode); // RECORD: best turn so far
		return Math.max(left, right); // RETURN: only one arm goes up to the parent
	}

	public static void main(String[] args) {
		LongestUnivaluePath dop = new LongestUnivaluePath();
		TreeNode root = newNode(5);
		root.left = newNode(4);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(1);
		root.right.right = newNode(5);
		System.out.println(dop.longestUnivaluePath(root)); // 2

		TreeNode root2 = newNode(1);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(4);
		root2.left.right = newNode(4);
		root2.right.right = newNode(5);
		System.out.println(dop.longestUnivaluePath(root2)); // 2
		System.out.println(dop.longestUnivaluePath(null)); // 0
	}
}

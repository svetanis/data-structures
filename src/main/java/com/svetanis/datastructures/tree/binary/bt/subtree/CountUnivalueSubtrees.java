package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 250. Count Univalue Subtrees
//
// Input: the root of a binary tree. A univalue subtree is a node together with everything below it
// where every node holds the same value. Returns how many such subtrees the tree has. On LeetCode:
// 0 <= number of nodes <= 1000, -1000 <= Node.val <= 1000.
//
// The one idea: one call answers "is the subtree at this node univalue?". A node is univalue when
// both of its children's subtrees are univalue (an empty child counts as univalue) and each child
// that exists holds the node's own value. The two child answers are what the two calls on the
// children return, so one pass bottom-up decides every node, and each "yes" adds one to the count.
// A leaf has no children, so it is always univalue.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.

public final class CountUnivalueSubtrees {

	private int count = 0; // COUNT: univalue subtrees found so far

	public int countSubtrees(TreeNode root) {
		this.count = 0; // RESET: a second call on this object starts from zero
		dfs(root); // WALK: every node that is univalue adds one
		return count; // RETURN: the number of univalue subtrees
	}

	private boolean dfs(TreeNode root) {
		if (root == null) {
			return true; // BASE: an empty subtree is univalue
		}
		boolean isLeft = dfs(root.left); // WALK: is the left subtree univalue?
		boolean isRight = dfs(root.right); // WALK: is the right subtree univalue?
		if (!isLeft || !isRight) {
			return false; // SKIP: a mixed child makes this subtree mixed
		}
		int left = root.left == null ? root.val : root.left.val; // COMPARE: an absent child matches by default
		int right = root.right == null ? root.val : root.right.val;

		if (left == right && right == root.val) {
			count++; // RECORD: this subtree is univalue
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(1);
		root.right = newNode(1);
		root.left.left = newNode(1);
		root.right.left = newNode(1);
		root.right.right = newNode(1);
		CountUnivalueSubtrees cus = new CountUnivalueSubtrees();
		System.out.println(cus.countSubtrees(root)); // 6

		TreeNode root1 = newNode(5);
		root1.left = newNode(1);
		root1.right = newNode(5);
		root1.left.left = newNode(5);
		root1.left.right = newNode(5);
		root1.right.right = newNode(5);
		System.out.println(cus.countSubtrees(root1)); // 4
	}
}

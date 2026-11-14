package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 226. Invert Binary Tree
//
// Input: root -- the root of a binary tree, possibly null. Swaps the left and right subtree of
// every node, in place, and returns root. An empty tree stays empty. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: inverting a tree means swapping the two children of every node. Swap them at this
// node first, then do the same in each of the two subtrees -- the order does not matter, because
// swapping two links at one node never touches the links below it. Here the node is swapped before
// its subtrees (preorder); InvertBinaryTreeRecursiveInPlace swaps after (postorder).
//
// Time: O(n) -- every node is swapped once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: InvertBinaryTreeRecursiveInPlace swaps after the recursive calls.
// InvertBinaryTreeQueue and InvertBinaryTreeStack do it without recursion. InvertBinaryTreeRecursive
// builds a new tree instead of changing the given one.

public final class InvertBinaryTree226 {

	public static TreeNode invert(TreeNode root) {
		dfs(root);
		return root;
	}

	private static void dfs(TreeNode root) {
		if (root == null) { // BASE: nothing to swap
			return;
		}
		swap(root); // SWAP: the left and right children of this node
		dfs(root.left); // SUBTREES: the same swap inside each
		dfs(root.right);
	}

	private static void swap(TreeNode root) {
		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(7);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		root.right.left = newNode(6);
		root.right.right = newNode(9);
		System.out.println(traverse(root)); // [4, 2, 7, 1, 3, 6, 9]
		TreeNode inverted = invert(root);
		System.out.println(traverse(inverted)); // [4, 7, 2, 9, 6, 3, 1]

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(3);
		System.out.println(traverse(root2)); // [2, 1, 3]
		TreeNode inverted2 = invert(root2);
		System.out.println(traverse(inverted2)); // [2, 3, 1]
	}
}

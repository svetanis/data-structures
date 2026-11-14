package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 285. Inorder Successor in BST
//
// Input: root of a BST and target, a node of that tree. On LeetCode: 1 <= number of nodes <= 10^4,
// -10^5 <= node.val <= 10^5, all values distinct. Returns the node that comes right after target in
// an in-order walk -- the node with the smallest value larger than target.val -- or null when target
// holds the largest value.
//
// The one idea: search down from the root as if looking for target.val. At a node whose value is
// larger than target's, that node is a candidate for the answer, and a smaller candidate can only
// be on its left: remember it and go left. At a node whose value is not larger, the answer is on its
// right: go right. The last candidate remembered is the smallest value above target. The test is
// strictly greater, so the target itself is never its own successor.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) for inOrderSuccessor; O(h) for recursive, which is the same walk as recursion.
//
// Siblings: InorderSuccessorParent solves LC 510, where each node has a parent pointer, so it
// climbs from the target instead of searching from the root. FirstNodeLargerK is the same step for a
// key that must be present.

public final class InorderSuccessor {

	public static TreeNode inOrderSuccessor(TreeNode root, TreeNode target) {
		TreeNode successor = null;
		while (root != null) {
			if (root.val > target.val) { // strictly greater -- the successor is not the target
				successor = root; // RECORD: a candidate; a smaller one can only be on the left
				root = root.left;
			} else {
				root = root.right; // SKIP: too small, the answer is on the right
			}
		}
		return successor;
	}

	public static TreeNode recursive(TreeNode root, TreeNode target) {
		return dfs(root, target, null);
	}

	private static TreeNode dfs(TreeNode root, TreeNode target, TreeNode successor) {
		if (root == null) {
			return successor; // BASE: off the tree, the last candidate is the answer
		}
		if (root.val > target.val) {
			successor = root; // RECORD: a candidate; a smaller one can only be on the left
			return dfs(root.left, target, successor);
		} else {
			return dfs(root.right, target, successor); // SKIP: too small, the answer is on the right
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(6);
		root.left = newNode(4);
		root.right = newNode(10);
		root.right.left = newNode(8);
		root.right.right = newNode(12);
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(9);
		System.out.println(recursive(root, root)); // 7
		System.out.println(inOrderSuccessor(root, root)); // 7

		TreeNode root2 = newNode(20);
		root2.left = newNode(8);
		root2.right = newNode(22);
		root2.left.left = newNode(4);
		root2.left.right = newNode(12);
		root2.left.right.left = newNode(10);
		root2.left.right.right = newNode(14);

		TreeNode node = root2.left; // 8
		TreeNode node2 = root2.left.right.right; // 14
		System.out.println(recursive(root2, node)); // 10
		System.out.println(inOrderSuccessor(root2, node)); // 10

		System.out.println(recursive(root2, node2)); // 20
		System.out.println(inOrderSuccessor(root2, node2)); // 20

		TreeNode largest = root2.right; // 22
		System.out.println(inOrderSuccessor(root2, largest)); // null
	}
}

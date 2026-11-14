package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 226. Invert Binary Tree
//
// Input: root -- the root of a binary tree, possibly null. Swaps the left and right subtree of
// every node, in place. An empty tree stays empty. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: invert both subtrees first, then swap the two children of this node. After the two
// calls return, each child is already the root of an inverted subtree, so swapping them finishes
// the node (postorder). InvertBinaryTree226 swaps first and recurses after; the result is the same,
// because swapping two links at one node never touches the links below it.
//
// Time: O(n) -- every node is swapped once.
// Space: O(h) -- the recursion stack, O(n) on a skewed tree.
//
// Siblings: InvertBinaryTree226 swaps before the recursive calls. InvertBinaryTreeQueue and
// InvertBinaryTreeStack do it without recursion. InvertBinaryTreeRecursive builds a new tree.

public final class InvertBinaryTreeRecursiveInPlace {

	public static void invert(TreeNode root) {
		if (isNull(root)) { // BASE: nothing to swap
			return;
		}
		invert(root.left); // SUBTREES: invert both sides first
		invert(root.right);
		swap(root); // SWAP: the two inverted children trade places
	}

	private static void swap(TreeNode root) {
		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		inOrder(root); // prints 4 2 5 1 6 3 7
		System.out.println();

		invert(root);

		inOrder(root); // prints 7 3 6 1 5 2 4
		System.out.println();

		TreeNode root2 = newNode(4);
		root2.left = newNode(2);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);

		inOrder(root2);
		System.out.println(); // 1 2 3 4 5

		invert(root2);

		inOrder(root2);
		System.out.println(); // 5 4 3 2 1
	}
}

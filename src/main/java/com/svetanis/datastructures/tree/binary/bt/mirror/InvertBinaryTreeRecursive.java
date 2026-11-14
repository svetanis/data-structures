package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 226. Invert Binary Tree
//
// Input: root -- the root of a binary tree, possibly null. Returns the root of a new tree with the
// left and right subtree of every node swapped; the given tree is left untouched. An empty tree
// gives an empty tree. On LeetCode: 0 <= nodes <= 100, -100 <= Node.val <= 100 (LeetCode accepts
// an in-place swap as well).
//
// The one idea: invert both subtrees first, then build a new node with the same value and the two
// inverted subtrees handed over crossed -- the inverted right one as the left child, the inverted
// left one as the right child. Nothing is swapped in place, so the original tree is never changed.
//
// The one idea of mirror: the same method built with newNode and field assignments instead of the
// three-argument constructor. The mirror of a tree is a new node with the same value whose left
// child is the mirror of the original right subtree and whose right child is the mirror of the
// original left subtree. An empty tree mirrors to an empty tree. Every node is copied once, so the
// original is never changed.
//
// Time: O(n) for both -- one new node per original node.
// Space: O(n) for both -- the new tree itself; the call stack adds O(h) on top.
//
// Siblings: InvertBinaryTreeRecursiveInPlace, InvertBinaryTree226, InvertBinaryTreeQueue and
// InvertBinaryTreeStack swap the children in place.

public final class InvertBinaryTreeRecursive {

	public static TreeNode invert(TreeNode root) {
		if (isNull(root)) { // BASE: an empty tree inverts to an empty tree
			return root;
		}
		TreeNode left = invert(root.left); // SUBTREES: invert both sides first
		TreeNode right = invert(root.right);
		return new TreeNode(root.val, right, left); // CROSS: new node with the two results swapped
	}

	public static TreeNode mirror(TreeNode root) {
		if (isNull(root)) { // BASE: an empty tree mirrors to an empty tree
			return root;
		}
		TreeNode mirror = newNode(root.val); // COPY: same value, new node
		mirror.left = mirror(root.right); // CROSS: the right side becomes the left
		mirror.right = mirror(root.left); // CROSS: the left side becomes the right
		return mirror;
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
		TreeNode inverted = invert(root);
		inOrder(inverted); // prints 7 3 6 1 5 2 4
		System.out.println();

		TreeNode root2 = newNode(4);
		root2.left = newNode(2);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);

		inOrder(root2);
		System.out.println(); // 1 2 3 4 5

		TreeNode mirror = mirror(root2);

		inOrder(mirror);
		System.out.println(); // 5 4 3 2 1
	}
}

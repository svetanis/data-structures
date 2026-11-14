package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 545. Boundary of Binary Tree
//
// Input: the root of a binary tree. Returns the boundary values anti-clockwise: the root, then
// the left boundary top to bottom, then the leaves left to right, then the right boundary
// bottom to top. The left boundary starts at the root's left child and always steps to the
// left child, or to the right child when there is no left child; the right boundary is the
// mirror image. Leaves are never part of either boundary, so no node appears twice. On
// LeetCode: 1 <= number of nodes <= 10^4, -1000 <= val <= 1000.
//
// The one idea: the answer is three separate pieces, each found on its own. The left boundary
// is one downward walk, the leaves come from one depth-first walk of the whole tree, and the
// right boundary is one downward walk pushed on a stack so it reads back bottom to top. Leaves
// are skipped on the two boundary walks because the leaf walk writes every leaf.
//
// Known limit: the leaf walk recurses once per level of the tree, so a chain of 10^4 nodes (one
// child per node, ten thousand levels) overflows the default stack, and LeetCode allows that
// many nodes. BoundaryTraversalIterative has no such limit and returns the same answer.
//
// Time: O(n) -- each walk touches a node at most once.
// Space: O(n) -- the output, plus the stack and the recursion depth of the leaf walk.
//
// Siblings: BoundaryTraversalRecursive and BoundaryTraversalIterative return TreeNode lists and
// build the same three pieces; this file returns Integer values in the shape LeetCode wants.

public final class BoundaryTraversalSubmit {

	private List<Integer> boundary;

	public List<Integer> boundary(TreeNode root) {
		if (root == null) {
			return new ArrayList<>(); // BASE: empty tree, empty boundary
		}
		boundary = new ArrayList<>();
		if (!isLeaf(root)) {
			boundary.add(root.val); // ROOT: first, unless it is a lone leaf (addLeaves writes it)
		}
		// left boundary (excluding last left node)
		TreeNode node = root.left;
		while (node != null) {
			if (!isLeaf(node)) {
				boundary.add(node.val); // TAKE: skip the leaf at the bottom
			}
			node = node.left != null ? node.left : node.right; // STEP: left child, else right child
		}
		// add all leaf nodes
		addLeaves(root);

		// right boundary in reverse order
		Deque<Integer> dq = new ArrayDeque<>();
		TreeNode curr = root.right;
		while (curr != null) {
			if (!isLeaf(curr)) {
				dq.push(curr.val); // HOLD: skip the leaf at the bottom
			}
			curr = curr.right != null ? curr.right : curr.left; // STEP: right child, else left child
		}
		while (!dq.isEmpty()) {
			boundary.add(dq.pop()); // TAKE: deepest first
		}
		return boundary;
	}

	private void addLeaves(TreeNode node) {
		if (node == null) {
			return; // BASE: past a leaf
		}
		if (isLeaf(node)) {
			boundary.add(node.val); // TAKE: a leaf
		}
		addLeaves(node.left); // WALK: left subtree first, so leaves come left to right
		addLeaves(node.right);
	}

	private boolean isLeaf(TreeNode node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		BoundaryTraversalSubmit bt = new BoundaryTraversalSubmit();
		TreeNode root0 = newNode(1);
		root0.right = newNode(2);
		root0.right.left = newNode(3);
		root0.right.right = newNode(4);
		System.out.println(bt.boundary(root0)); // [1, 3, 4, 2]

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		root1.left.left.left = newNode(8);
		root1.left.left.right = newNode(9);
		root1.left.right.left = newNode(10);
		root1.right.left.left = newNode(11);
		root1.right.right.left = newNode(12);
		root1.right.right.right = newNode(13);
		System.out.println(bt.boundary(root1)); // [1, 2, 4, 8, 9, 10, 11, 12, 13, 7, 3]

		TreeNode root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(4);
		root2.left.right = newNode(3);
		root2.left.left.left = newNode(9);
		root2.left.right.left = newNode(15);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		root2.right.right.left = newNode(6);
		System.out.println(bt.boundary(root2)); // [12, 7, 4, 9, 15, 10, 6, 5, 1]

		TreeNode root3 = newNode(12);
		root3.right = newNode(1);
		root3.right.left = newNode(10);
		root3.right.right = newNode(5);
		System.out.println(bt.boundary(root3)); // [12, 10, 5, 1]
	}
}

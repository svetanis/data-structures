package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

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
// The one idea: the answer is three separate pieces, and each is found on its own. The left
// piece is one downward walk, the right piece is one downward walk whose nodes are read back in
// reverse (a stack does that), and the leaves come from a depth-first walk that pushes the right
// child first so the left child pops first. A node on a boundary walk is written only when it is
// not a leaf, because the leaf walk writes every leaf.
//
// Time: O(n) -- each walk touches a node at most once.
// Space: O(n) -- the output, plus the stacks.
//
// Siblings: BoundaryTraversalRecursive makes the same three pieces with recursion;
// BoundaryTraversalSubmit is the LeetCode submission with the same three pieces and returns
// plain values.

public final class BoundaryTraversalIterative {

	public static List<TreeNode> boundary(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		if (!isLeaf(root)) {
			list.add(root); // ROOT: first, unless it is a lone leaf (the leaf walk writes it)
		}

		TreeNode left = root.left; // LEFT BOUNDARY: top to bottom
		while (isNotNull(left)) {
			if (!isLeaf(left)) {
				list.add(left); // TAKE: skip the leaf at the bottom
			}
			left = isNotNull(left.left) ? left.left : left.right; // STEP: left child, else right child
		}

		list.addAll(leaves(root)); // LEAVES: left to right

		Deque<TreeNode> stack = new ArrayDeque<>(); // RIGHT BOUNDARY: found top to bottom,
		TreeNode right = root.right; // written bottom to top
		while (isNotNull(right)) {
			if (!isLeaf(right)) {
				stack.push(right); // HOLD: skip the leaf at the bottom
			}
			right = isNotNull(right.right) ? right.right : right.left; // STEP: right child, else left
		}
		while (!stack.isEmpty()) {
			list.add(stack.pop()); // TAKE: deepest first
		}
		return list;
	}

	private static List<TreeNode> leaves(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			if (isLeaf(node)) {
				list.add(node); // TAKE: a leaf
			}
			if (isNotNull(node.right)) {
				stack.push(node.right); // PUSH: right first, so left pops first
			}
			if (isNotNull(node.left)) {
				stack.push(node.left);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root0 = newNode(1);
		root0.right = newNode(2);
		root0.right.left = newNode(3);
		root0.right.right = newNode(4);
		System.out.println(boundary(root0)); // [1, 3, 4, 2]

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
		System.out.println(boundary(root1)); // [1, 2, 4, 8, 9, 10, 11, 12, 13, 7, 3]

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
		System.out.println(boundary(root2)); // [12, 7, 4, 9, 15, 10, 6, 5, 1]

		TreeNode root3 = newNode(12);
		root3.right = newNode(1);
		root3.right.left = newNode(10);
		root3.right.right = newNode(5);
		System.out.println(boundary(root3)); // [12, 10, 5, 1]
	}
}

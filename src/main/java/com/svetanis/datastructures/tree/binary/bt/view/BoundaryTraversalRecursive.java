package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
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
// boundary is written on the way down (before the recursive call), the right boundary on the
// way back up (after it), which reverses it for free, and the leaves come from one walk of each
// side of the root. A boundary node is written only when it has a child, because the leaf walk
// writes every leaf. The root is written first and its two sides are walked separately, so a
// root that is itself a lone leaf appears once.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (one
// child per node, ten thousand levels) overflows the default stack, and LeetCode allows that
// many nodes. BoundaryTraversalIterative has no such limit and returns the same answer.
//
// Time: O(n) -- each walk touches a node at most once.
// Space: O(n) -- the output, plus recursion depth up to the tree height.
//
// Siblings: BoundaryTraversalIterative makes the same three pieces with loops and stacks;
// BoundaryTraversalSubmit is the LeetCode submission with the same three pieces and returns
// plain values.

public final class BoundaryTraversalRecursive {

	public static List<TreeNode> boundary(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		if (isNotNull(root)) {
			list.add(root); // ROOT: first
			// left boundary in top-down manner
			boundaryLeft(root.left, list);

			// all leaf nodes
			leaves(root.left, list);
			leaves(root.right, list);

			// right boundary in bottom-up manner
			boundaryRight(root.right, list);
		}
		return list;
	}

	private static void boundaryLeft(TreeNode node, List<TreeNode> list) {
		if (isNotNull(node)) {
			if (isNotNull(node.left)) {
				// to ensure top down order, print the node
				// before calling itself for left subtree
				list.add(node); // TAKE: before the call -- top to bottom
				boundaryLeft(node.left, list);
			} else if (isNotNull(node.right)) {
				list.add(node); // TAKE: no left child, so the right child continues the boundary
				boundaryLeft(node.right, list);
			}
			// do nothing if it is a leaf node,
			// this way we avoid duplicates in output
		}
	}

	private static void boundaryRight(TreeNode node, List<TreeNode> list) {
		if (isNotNull(node)) {
			if (isNotNull(node.right)) {
				// to ensure bottom up order, first call for
				// right subtree, then print this node
				boundaryRight(node.right, list);
				list.add(node); // TAKE: after the call -- bottom to top
			} else if (isNotNull(node.left)) {
				boundaryRight(node.left, list);
				list.add(node); // TAKE: no right child, so the left child continues the boundary
			}
			// do nothing if it is a leaf node,
			// this way we avoid duplicates in output
		}
	}

	private static void leaves(TreeNode node, List<TreeNode> list) {
		if (isNull(node)) {
			return; // BASE: past a leaf
		}
		if (isLeaf(node)) {
			list.add(node); // TAKE: a leaf
		}
		leaves(node.left, list); // WALK: left subtree first, so leaves come left to right
		leaves(node.right, list);
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

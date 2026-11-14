package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 226. Invert Binary Tree
//
// Input: root -- the root of a binary tree, possibly null. Swaps the left and right subtree of
// every node, in place. An empty tree stays empty. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: inverting a tree means swapping the two children of every node, and swapping the
// children of one node never touches the links below it, so the nodes can be visited in any order.
// Take nodes from a queue (level order), swap each one's children, and queue the children for
// their own swap. The children are queued after the swap and are read from the node taken out --
// not from root, which never changes.
//
// Time: O(n) -- every node is queued, taken out and swapped once.
// Space: O(n) -- the queue holds a whole level, up to about n / 2 nodes.
//
// Siblings: InvertBinaryTreeStack is the same loop with the children added right first (despite its
// name it is also first in, first out).
// InvertBinaryTree226 and InvertBinaryTreeRecursiveInPlace do it by recursion.
// InvertBinaryTreeRecursive builds a new tree.

public final class InvertBinaryTreeQueue {

	public static void invert(TreeNode root) {
		if (isNull(root)) { // BASE: empty tree
			return;
		}

		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll(); // TAKE: the next node, level by level
			swap(node); // SWAP: its two children
			if (isNotNull(node.left)) { // QUEUE: the children, for their own swap
				queue.add(node.left);
			}
			if (isNotNull(node.right)) {
				queue.add(node.right);
			}
		}
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
	}
}

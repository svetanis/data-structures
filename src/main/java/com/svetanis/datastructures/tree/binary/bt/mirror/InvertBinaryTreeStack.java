package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 226. Invert Binary Tree
//
// Input: root -- the root of a binary tree, possibly null. Swaps the left and right subtree of
// every node, in place. An empty tree stays empty. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: inverting a tree means swapping the two children of every node, and swapping the
// children of one node never touches the links below it, so the nodes can be visited in any order.
// Take a node from the deque, swap its children, and put the children back for their own swap. The
// children are read from the node taken out -- not from root, which never changes.
//
// The deque is used with add (at the back) and poll (from the front), so it is first in, first out:
// a queue, not a stack, and it visits level by level, right to left. Any visiting order inverts
// the tree; a real stack (push and pop at the same end) would keep only one chain in memory.
//
// Time: O(n) -- every node is put in, taken out and swapped once.
// Space: O(n) -- first in, first out, so the deque holds a whole level, up to about n / 2 nodes.
//
// Siblings: InvertBinaryTreeQueue is the same loop with the children added left first.
// InvertBinaryTree226 and InvertBinaryTreeRecursiveInPlace do it by recursion.
// InvertBinaryTreeRecursive builds a new tree.

public final class InvertBinaryTreeStack {

	public static void invert(TreeNode root) {
		if (isNull(root)) { // BASE: empty tree
			return;
		}
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.add(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.poll(); // TAKE: the oldest node still waiting
			swap(node); // SWAP: its two children
			if (isNotNull(node.right)) { // ADD: the children, right first, for their own swap
				stack.add(node.right);
			}
			if (isNotNull(node.left)) {
				stack.add(node.left);
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

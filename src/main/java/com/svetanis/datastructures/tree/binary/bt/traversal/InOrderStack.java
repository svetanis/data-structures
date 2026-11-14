package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 94. Binary Tree Inorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Returns the values in inorder: the
// whole left subtree, then the node, then the whole right subtree. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: the stack holds the nodes that are waiting for their left subtree to be finished.
// push walks down the left links from a node and stacks every node it passes, so the node on top is
// the leftmost one still to be reported -- nothing is to its left. Pop it and report it; the only
// thing left to do for it is its right subtree, which is again "walk down the left links from the
// right child and stack them". The node under it on the stack is its parent, whose left subtree has
// just finished, so the parent is reported next -- the order is left, node, right at every level.
//
// Time: O(n) -- every node is pushed once and popped once.
// Space: O(h) -- the stack holds one chain of waiting ancestors; h is lg n on a balanced tree and
// n on a skewed one.
//
// Siblings: InOrderRecursive prints the same order with the call stack doing the waiting.
// InOrderStackSingleLoop is this walk with no helper: the next subtree to walk waits in a variable,
// so its loop runs while that variable or the stack has work.
// PreOrderStack and PostOrderTwoStacks are the other two orders on an explicit stack.

public final class InOrderStack {

	public static List<Integer> inOrder(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		Deque<TreeNode> stack = push(new ArrayDeque<>(), root); // WAIT: root and its whole left chain
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop(); // TAKE: the leftmost node still waiting
			list.add(node.val); // RECORD: its left side is done
			stack = push(stack, node.right); // WALK: its right subtree, left chain first
		}
		return list;
	}

	private static Deque<TreeNode> push(Deque<TreeNode> stack, TreeNode node) {
		while (node != null) { // WALK: down the left links
			stack.push(node); // WAIT: this node needs its left subtree reported first
			node = node.left;
		}
		return stack;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		System.out.println(inOrder(root)); // [4, 2, 5, 1, 3, 6]
	}
}

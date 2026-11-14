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
// The one idea: in the recursive version every call that is paused is paused on the same line --
// the call on its left subtree -- so the stack only has to hold nodes. Going left pushes each node
// passed (it is now waiting for its left subtree). When the left side runs out, the newest waiting
// node resumes: pop it and write it. Its last job is its right subtree, after which nothing is left
// for it, so it is not pushed back: node moves to its right child and the next walk left starts
// there. Work can wait in two places -- a subtree still to walk in node, paused nodes on the
// stack -- so the loop runs while either has some.
//
// Time: O(n) -- every node is pushed once and popped once.
// Space: O(h) -- the stack holds one chain of waiting nodes; h is lg n on a balanced tree and n on
// a skewed one. The recursive version overflows the call stack on a deep tree; this one does not.
//
// Siblings: InOrderStack is the same walk with the walk left in a helper, run before the loop and
// after each write, so the stack alone says whether work is left. InOrderRecursive is the version
// this one is built from.

public final class InOrderStackSingleLoop {

	public static List<Integer> inOrder(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>(); // nodes waiting for their left subtree
		TreeNode node = root; // where the next walk left starts
		while (node != null || !stack.isEmpty()) { // a subtree to walk, or a waiting node to write
			while (node != null) { // DESCEND: push every node passed going left
				stack.push(node);
				node = node.left;
			}
			node = stack.pop(); // RESUME: the newest waiting node
			list.add(node.val); // WRITE: its left subtree is finished
			node = node.right; // GO RIGHT: the next DESCEND starts here -- nothing pushed
		}
		return list;
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

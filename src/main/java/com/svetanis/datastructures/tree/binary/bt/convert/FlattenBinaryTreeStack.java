package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List
//
// Input: the root of a binary tree. On LeetCode: 0 to 2000 nodes, -100 <= val <= 100.
// Returns: nothing -- the tree is rewired in place so that, following only right pointers from the
// root, the nodes appear in pre-order (node, left subtree, right subtree), and every left pointer
// is null.
//
// The one idea: the stack always holds the nodes still to be visited, with the next one in pre-order
// on top. Pop a node; its children are visited next, left before right, so push the right child
// first and the left child on top of it. Whatever is now on top is the node that follows this one in
// pre-order, so point this node's right pointer at it and clear the left pointer. Every node is
// linked to its pre-order successor exactly once.
//
// Time: O(n) -- every node is pushed once and popped once.
// Space: O(h) -- popping a node replaces it by at most its two children, so the stack holds at
// most one waiting right child per level of the current root-to-node path, plus one.
//
// Siblings: FlattenBinaryTreeIterative does the same in O(1) space by rewiring while it walks;
// FlattenBinaryTreeRecursive flattens both children first (O(n^2) time on a left-leaning tree).

public final class FlattenBinaryTreeStack {

	public static void flatten(TreeNode root) {
		if (root == null) {
			return;
		}
		Deque<TreeNode> dq = new ArrayDeque<>();
		dq.push(root);
		while (!dq.isEmpty()) { // WALK: pre-order, one node per pop
			TreeNode node = dq.pop();
			if (node.right != null) { // PUSH: right child first, so it is visited second
				dq.push(node.right);
			}
			if (node.left != null) { // PUSH: left child on top, visited next
				dq.push(node.left);
			}
			if (!dq.isEmpty()) { // LINK: the node on top is this node's pre-order successor
				node.right = dq.peek();
			}
			node.left = null; // CLEAR: no left pointers in the result
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.right = newNode(6);
		flatten(root);
		Nodes.preOrder(root); // 1 2 3 4 5 6
		System.out.println();
		TreeNode root1 = newNode(0);
		flatten(root1);
		Nodes.preOrder(root1); // 0
		System.out.println();
	}
}

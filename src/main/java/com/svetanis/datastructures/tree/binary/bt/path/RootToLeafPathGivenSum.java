package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// Find a Root-to-Leaf Path With a Given Sum -- not a LeetCode problem; 112. Path Sum asks only
// whether such a path exists and 113. Path Sum II asks for all of them
//
// Input: the root of a binary tree and a number k. Returns the values, root first, of the first
// root-to-leaf path (going left before right) whose values add up to k, or an empty list if there
// is none. Assumes k minus a path sum does not overflow an int.
//
// The one idea: subtract each value from k on the way down, so what is carried is the sum the
// rest of the path must still reach. A leaf whose value equals what is carried completes the
// path. The path is written on the way BACK up: a node that hears "yes" from a child puts its own
// value at the front of the deque, so the root's value ends up first and the leaf's last.
//
// Time: O(n) -- each node is visited once; the front insertions are O(1) on a deque.
// Space: O(h) -- the recursion stack (h = height, n on a chain); the path is extra.
//
// Siblings: RootToLeafPathIsGivenSum answers only yes or no; RootToLeafPathsGivenSum lists every
// such path.
public final class RootToLeafPathGivenSum {

	public static List<Integer> path(TreeNode node, int k) {
		Deque<Integer> dq = new ArrayDeque<>(); // PATH: filled from the leaf back up to the root
		isPathSum(node, k, dq);
		return new ArrayList<>(dq);
	}

	private static boolean isPathSum(TreeNode node, int k, Deque<Integer> dp) {
		if (isNull(node)) { // BASE: no node, no path
			return false;
		}
		if (k == node.val && isLeaf(node)) { // FOUND: a leaf that uses up what is left
			dp.addFirst(node.val);
			return true;
		}
		if (isPathSum(node.left, k - node.val, dp)) { // ASK: left subtree first
			dp.addFirst(node.val); // RECORD: this node is on the path
			return true;
		}
		if (isPathSum(node.right, k - node.val, dp)) { // ASK: right subtree
			dp.addFirst(node.val); // RECORD: this node is on the path
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 14 : 10->2->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 2
		System.out.println();

		// given sum root-to-leaf path
		System.out.println(path(root, 21)); // [10, 8, 3]
		System.out.println(path(root, 23)); // [10, 8, 5]
		System.out.println(path(root, 14)); // [10, 2, 2]
		System.out.println(path(root, 99)); // []
	}
}

package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 99. Recover Binary Search Tree
//
// Input: the root of a binary search tree in which the values of exactly two nodes were
// swapped by mistake. Fixes the tree in place, by swapping those two values back; nothing is
// returned. On LeetCode: 2 to 1000 nodes, -2^31 <= node.val <= 2^31 - 1, all values unique.
//
// The one idea: the in-order walk of a BST is ascending, so after one swap the sequence has
// one or two places where a value is smaller than the one just before it. If the two swapped
// nodes are neighbours in the sequence, there is one such place: its pair is the answer. If
// not, there are two: the first pair's LARGER value (the earlier node, prev) is the big value
// that was moved too early, and the second pair's SMALLER value (the later node) is the small
// value that was moved too late. So keep the node visited just before (prev); on every
// descent in value, take first = prev only the first time, and second = the current node
// every time. Swap the values of first and second after the walk.
//
// A tree that is already a BST has no descent, first and second stay null, and nothing is
// swapped. The three fields live across the walk, so recover() resets them on its first
// lines; without that a second call would compare the new tree's first node with the old
// tree's last node.
//
// Time: O(n) -- one in-order walk and one swap.
// Space: O(h) -- the recursion stack.

public final class RecoverBST {

	private TreeNode prev; // the node visited just before this one
	private TreeNode first; // the earlier node of the swapped pair (the too-large value)
	private TreeNode second; // the later node of the swapped pair (the too-small value)

	public void recover(TreeNode root) {
		this.prev = null;
		this.first = null;
		this.second = null;
		inOrder(root);
		if (first == null || second == null) { // already a BST: nothing to swap
			return;
		}
		int temp = first.val; // SWAP: put the two values back
		first.val = second.val;
		second.val = temp;
	}

	public void inOrder(TreeNode root) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		inOrder(root.left); // WALK: smaller values first
		if (prev != null && root.val < prev.val) { // DESCENT: this value is smaller than the one before
			if (first == null) {
				first = prev; // FIRST: only the first descent names it
			}
			second = root; // SECOND: the last descent names it
		}
		prev = root; // ADVANCE
		inOrder(root.right);
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(3);
		root.left.right = newNode(2);
		RecoverBST r1 = new RecoverBST();
		r1.recover(root);
		System.out.println(LotQueue.traverse(root)); // 3, 1, 2

		TreeNode root1 = new TreeNode(3);
		root1.left = new TreeNode(1);
		root1.right = new TreeNode(4);
		root1.right.left = new TreeNode(2);
		RecoverBST r2 = new RecoverBST();
		r2.recover(root1);
		System.out.println(LotQueue.traverse(root1)); // 2, 1, 4, 3

		// an already-valid tree, and then the same object again: both change nothing
		TreeNode valid = newNode(2);
		valid.left = newNode(1);
		valid.right = newNode(3);
		r2.recover(valid);
		System.out.println(LotQueue.traverse(valid)); // 2, 1, 3
		r2.recover(valid);
		System.out.println(LotQueue.traverse(valid)); // 2, 1, 3
	}
}
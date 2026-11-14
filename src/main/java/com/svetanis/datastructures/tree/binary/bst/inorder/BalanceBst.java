package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1382. Balance a Binary Search Tree
//
// Input: the root of a binary search tree. Returns the root of a balanced binary search tree
// (the depths of the two subtrees of every node differ by at most 1) holding the same values.
// On LeetCode: 1 to 10^4 nodes, 1 <= node.val <= 10^5.
//
// The one idea: the in-order walk of a BST lists its values in ascending order, and that order
// is all a BST is. So rebuild from the list: the middle value becomes the root, the values left
// of it become the left subtree and the values right of it the right subtree, each built the
// same way from its own middle. A range halves at every step, so the tree is as shallow as
// n values allow.
//
// Time: O(n) -- one walk to fill the list, then each value becomes a node exactly once.
// Space: O(n) -- the list of values (the recursion is O(h) deep on the old tree and O(log n)
// deep on the new one).

public final class BalanceBst {

	private List<Integer> list;

	public TreeNode balance(TreeNode root) {
		this.list = new ArrayList<>(); // RESET: a second call must not see the first tree's values
		bstToList(root);
		return listToBst();
	}

	private void bstToList(TreeNode root) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		bstToList(root.left); // WALK: smaller values first
		list.add(root.val); // RECORD: ascending order
		bstToList(root.right);
	}

	private TreeNode listToBst() {
		int n = list.size();
		return listToBst(0, n - 1);
	}

	private TreeNode listToBst(int left, int right) {
		if (left > right) { // BASE: no values left in this range
			return null;
		}
		int mid = left + (right - left) / 2; // SPLIT: the middle value is the root
		TreeNode root = new TreeNode(list.get(mid));
		root.left = listToBst(left, mid - 1); // BUILD: the values below it
		root.right = listToBst(mid + 1, right); // BUILD: the values above it
		return root;
	}

	public static void main(String[] args) {
		BalanceBst bb1 = new BalanceBst();
		TreeNode root = newNode(1);
		root.right = newNode(2);
		root.right.right = newNode(3);
		root.right.right.right = newNode(4);
		Nodes.preOrder(bb1.balance(root)); // 2 1 3 4
		System.out.println();

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(3);
		Nodes.preOrder(bb1.balance(root2)); // 2 1 3
		System.out.println();
	}
}

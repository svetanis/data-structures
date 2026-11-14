package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 701. Insert into a Binary Search Tree
//
// Input: root -- the root of a binary search tree (possibly empty); val -- a value not already in
// the tree. Returns the root of the tree after a node holding val is added as a new leaf. On
// LeetCode: 0 <= number of nodes <= 10^4, -10^8 <= Node.val <= 10^8, all values distinct, val
// not in the tree; any valid result is accepted.
//
// The one idea: a new value always ends up as a leaf, in the one empty spot the search for it
// reaches. Walk down from the root the way a search would: a smaller val goes left, a larger one
// right. Stop when the chosen child is empty; the node just left behind, parent, is where the new
// node hangs. Nothing else is remembered on the way down, because the nodes above are not
// changed.
//
// Time: O(h) -- one path from the root to the empty spot; h is the tree's height, up to n on a
// chain.
// Space: O(1) -- two node variables, no recursion, so a chain of any length is fine.
//
// Siblings: InsertIntoBst -- the same walk by recursion, O(h) call stack.

public final class InsertIntoBstIterative {

	public static TreeNode insert(TreeNode root, int val) {
		TreeNode parent = null; // the last node looked at, once node falls off the tree
		TreeNode node = root;
		while (node != null) {
			parent = node;
			node = node.val < val ? node.right : node.left; // WALK: larger goes right, smaller left
		}
		TreeNode leaf = new TreeNode(val);
		if (parent == null) {
			return leaf; // BASE: the empty tree is just the new leaf
		}
		if (parent.val < val) {
			parent.right = leaf; // PLACE: the empty spot on the right
		} else {
			parent.left = leaf; // PLACE: the empty spot on the left
		}
		return root; // RETURN: the root did not change
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(7);
		root.left.left = newNode(1);
		root.left.right = newNode(3);

		Nodes.preOrder(insert(root, 5)); // 4 2 1 3 7 5
		System.out.println();

		TreeNode root2 = newNode(5);
		root2.left = newNode(2);
		root2.right = newNode(7);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(4);
		Nodes.preOrder(insert(root2, 25)); // 5 2 1 3 4 7 25
		System.out.println();
	}
}

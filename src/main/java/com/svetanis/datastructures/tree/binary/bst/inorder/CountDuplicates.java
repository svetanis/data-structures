package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Count Duplicates in a BST (not a LeetCode problem)
//
// Input: the root of a binary search tree of integers that may hold equal values. A duplicate
// is always in the right subtree of the node it equals: this is how Nodes.insert places a value
// equal to a node's. Returns the number of EXTRA copies -- the node count minus the number of
// distinct values. The values 2, 2, 2 count 2; "how many values repeat" would count 1, and the
// two readings agree until some value appears three or more times. The closest LeetCode
// problem is 501 (Find Mode in Binary Search Tree), which is ModeInBST in this package.
//
// The one idea: the in-order walk visits the values in ascending order, so equal values come
// out next to each other, and a node is a duplicate exactly when it equals the node visited
// just before it. Here that "node just before" is not kept as a running variable: prev is
// passed down the recursion instead. The left call gets the parent's prev, the right call gets
// the current node. That gives the true predecessor only for the insertion rule above. A copy
// goes right of the copy inserted before it, so the copy is the leftmost node of that
// copy's right subtree, and the prev handed down to it is the copy before it. A tree that put
// duplicates on the left would be miscounted.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack.

public final class CountDuplicates {

	public static int duplicates(TreeNode root) {
		return duplicates(root, root); // the root has no predecessor: it is its own prev
	}

	private static int duplicates(TreeNode root, TreeNode prev) {
		int count = 0;
		if (isNull(root)) { // BASE: empty subtree
			return count;
		}
		count += duplicates(root.left, prev); // WALK: left subtree, with the parent's prev
		if (root != prev && root.val == prev.val) { // COUNT: equals the node before it
			count++;
		}
		count += duplicates(root.right, root); // WALK: right subtree, this node is its prev
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		insert(root, 5);
		insert(root, 3);
		insert(root, 2);
		insert(root, 2);
		insert(root, 4);
		insert(root, 4);
		insert(root, 6);
		insert(root, 6);

		inOrder(root); // 2 2 3 4 4 5 5 6 6
		System.out.println();
		System.out.println(duplicates(root)); // 4 -- one extra copy each of 2, 4, 5 and 6
	}
}
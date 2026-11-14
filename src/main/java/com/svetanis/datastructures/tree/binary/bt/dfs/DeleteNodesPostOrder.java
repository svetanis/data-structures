package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1110. Delete Nodes And Return Forest
//
// Input: root of a binary tree whose node values are distinct, and an array of values to delete.
// Deleting a node leaves its two subtrees as separate trees. Returns the roots of the trees that
// remain, in any order. On LeetCode: 1 <= number of nodes <= 1000, 1 <= node.val <= 1000,
// to_delete.length <= 1000, the values in to_delete are distinct and may be absent from the tree.
//
// The one idea: a node becomes the root of a new tree in exactly two cases -- it is the original
// root and survives, or its parent is deleted and it survives. This version works from the
// bottom up (post-order): clean both subtrees first, so every child is either a kept subtree or
// null. Then if this node is deleted, the children that are still there are the roots of new
// trees, and the node returns null to cut itself off from its parent. The original root is added
// first when it is not deleted.
//
// Time: O(n + m) -- one pass over the n nodes, and m values put in a set first.
// Space: O(n + m) -- the set, the answer, and the recursion stack.
//
// Siblings: DeleteNodesPreOrder decides top-down instead, passing down whether the parent was
// deleted, and so needs no special case for the original root.

public final class DeleteNodesPostOrder {

	public static List<TreeNode> forest(TreeNode root, int[] delete) {
		List<TreeNode> list = new ArrayList<>();
		Set<Integer> set = Arrays.stream(delete)
											.boxed().collect(Collectors.toSet()); // SET: values to delete
		if (isNull(root)) {
			return list;
		}
		if (!set.contains(root.val)) {
			list.add(root); // RECORD: a surviving original root starts a tree
		}
		dfs(root, set, list);
		return list;
	}

	private static TreeNode dfs(TreeNode root, Set<Integer> set, List<TreeNode> list) {
		if (isNull(root)) {
			return null; // BASE: an empty spot
		}
		root.left = dfs(root.left, set, list); // WALK: children first
		root.right = dfs(root.right, set, list);
		if (set.contains(root.val)) {
			if (isNotNull(root.left)) {
				list.add(root.left); // RECORD: a kept child of a deleted node starts a tree
			}
			if (isNotNull(root.right)) {
				list.add(root.right);
			}
			return null; // CUT: the parent loses this node
		}
		return root; // KEEP: the node stays where it is
	}

	public static void main(String[] args) {
		int[] a = { 3, 5 };
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		System.out.println(forest(root, a)); // [1, 6, 7] -- the roots; tree 1 is 1, 2, 4
	}
}

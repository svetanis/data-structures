package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
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
// The one idea: a node becomes the root of a new tree when it survives and nothing above it
// does -- it is the original root, or its parent was deleted. So pass one flag down: "my parent
// is gone" (true for the original root, which has no parent). A node that survives and has the
// flag is added to the answer. Each call then returns null for a deleted node, which cuts it off
// from its parent, and the node itself otherwise.
//
// Time: O(n + m) -- one pass over the n nodes, and m values put in a set first.
// Space: O(n + m) -- the set, the answer, and the recursion stack.
//
// Siblings: DeleteNodesPostOrder cleans the children first and adds the children of a deleted
// node as it goes, with a special case for the original root.

public final class DeleteNodesPreOrder {

	public static List<TreeNode> forest(TreeNode root, int[] delete) {
		List<TreeNode> list = new ArrayList<>();
		Set<Integer> set = Arrays.stream(delete).boxed().collect(Collectors.toSet()); // SET: values to delete
		if (isNull(root)) {
			return list;
		}
		root = dfs(root, true, set, list); // WALK: the original root counts as having no parent
		return list;
	}

	private static TreeNode dfs(TreeNode root, boolean isRoot, 
								Set<Integer> set, List<TreeNode> list) {
		if (isNull(root)) {
			return null; // BASE: an empty spot
		}
		boolean deleted = set.contains(root.val);
		if (isRoot && !deleted) {
			list.add(root); // RECORD: survives with no parent above -- the root of a new tree
		}
		root.left = dfs(root.left, deleted, set, list); // WALK: tell the children whether this node is gone
		root.right = dfs(root.right, deleted, set, list);
		return deleted ? null : root; // RETURN: null cuts a deleted node off from its parent
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

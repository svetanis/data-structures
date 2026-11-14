package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.List;

// 257. Binary Tree Paths
//
// Input: the root of a binary tree. Returns every root-to-leaf path as a string of its values
// joined by "->", in left-to-right order of the leaves. On LeetCode: 1 <= n <= 100,
// -100 <= val <= 100.
//
// The one idea: hand the path so far down to each child as a string. A node adds its own value
// to the string; a leaf has a finished path and records it, any other node adds "->" and hands
// the string on to both children. Nothing has to be undone on the way back, because a string
// cannot be changed, so each call has its own copy.
//
// Time: O(n + L * h) -- each node is visited once, and each step of the way builds a new string
//   of up to h values (h = height, L = leaves); O(n^2) in the worst case.
// Space: O(h) -- the recursion stack and the strings along it; the answer is extra.
//
// Siblings: RootToLeafPathsSubmit builds the same strings from a list of values and
// String.join, taking each value back out after use; RootToLeafPathsRecursive lists the values
// instead of building strings.
public final class RootToLeafPathsSimple {

	public static List<String> paths(TreeNode root) {
		List<String> paths = new ArrayList<>();
		dfs(root, "", paths);
		return paths;
	}

	private static void dfs(TreeNode node, String path, List<String> paths) {
		if (node == null) { // BASE: nothing below
			return;
		}
		path += node.val; // TAKE: the current node joins the string
		if (node.left == null && node.right == null) { // RECORD: a leaf ends the path
			paths.add(path);
		} else {
			dfs(node.left, path + "->", paths); // WALK: left subtree
			dfs(node.right, path + "->", paths); // WALK: right subtree
		}
	}

	public static void main(String[] args) {
		// 10->8->3
		// 10->8->5
		// 10->2->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 2
		System.out.println();

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		System.out.println(paths(root)); // [10->8->3, 10->8->5, 10->2->2]
		System.out.println();
	}
}

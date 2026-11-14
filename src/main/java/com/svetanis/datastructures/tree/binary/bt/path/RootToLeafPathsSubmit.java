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
// The one idea: walk down depth first keeping the values from the root to the current node as a
// list of strings. At a leaf the list IS one complete path: join it with "->" and record it.
// Leaving a node removes its value from the list again, so the list always holds exactly the
// path to the current node.
//
// Time: O(n + L * h) -- each node is visited once, and each of the L leaves joins a path of up
//   to h values (h = height); O(n^2) in the worst case.
// Space: O(h) -- the list and the recursion stack; the answer is extra.
//
// Siblings: RootToLeafPathsSimple passes an immutable string down instead, so it has nothing to
// undo; RootToLeafPathsRecursive lists the values instead of building strings.
public final class RootToLeafPathsSubmit {

	public static List<String> paths(TreeNode root) {
		List<String> path = new ArrayList<>(); // PATH: values from the root to the current node
		List<String> paths = new ArrayList<>();
		dfs(root, path, paths);
		return paths;
	}

	private static void dfs(TreeNode node, List<String> path, List<String> paths) {
		if (node == null) { // BASE: nothing below
			return;
		}
		path.add(node.val + ""); // TAKE: the current node joins the path
		if (node.left == null && node.right == null) { // RECORD: a leaf ends the path
			paths.add(String.join("->", path));
		} else {
			dfs(node.left, path, paths); // WALK: left subtree
			dfs(node.right, path, paths); // WALK: right subtree
		}
		path.remove(path.size() - 1); // UNDO: leave the node
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

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

// All Root-to-Leaf Paths -- not a LeetCode problem; the nearest is 257. Binary Tree Paths (which
// wants strings)
//
// Input: the root of a binary tree. Returns the values of every root-to-leaf path, root first,
// one list per leaf, in left-to-right order of the leaves. An empty tree gives an empty list.
//
// The one idea: walk down depth first keeping the values from the root to the current node in a
// deque. At a leaf the deque IS one complete root-to-leaf path: copy it into the answer. Leaving
// a node removes its value from the deque again, so the deque always holds exactly the path to
// the current node.
//
// Time: O(n + L * h) -- each node is visited once, and each of the L leaves copies a path of up
//   to h values (h = height); O(n^2) in the worst case.
// Space: O(h) -- the deque and the recursion stack; the answer is extra.
//
// Siblings: RootToLeafPathsIterative lists the same paths level by level with a parent map;
// RootToLeafPathsSimple and RootToLeafPathsSubmit build strings; RootToLeafPathSums adds each
// path up instead of listing it.
public final class RootToLeafPathsRecursive {

	public static List<List<Integer>> paths(TreeNode root) {
		Deque<Integer> path = new ArrayDeque<>(); // PATH: values from the root to the current node
		List<List<Integer>> paths = new ArrayList<>();
		paths(root, path, paths);
		return paths;
	}

	private static void paths(TreeNode node, Deque<Integer> path, List<List<Integer>> paths) {
		if (isNull(node)) { // BASE: nothing below
			return;
		}
		path.addLast(node.val); // TAKE: the current node joins the path
		if (isLeaf(node)) { // RECORD: the deque is a complete root-to-leaf path
			paths.add(new ArrayList<>(path));
		} else {
			paths(node.left, path, paths); // WALK: left subtree
			paths(node.right, path, paths); // WALK: right subtree
		}
		path.removeLast(); // UNDO: leave the node
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
		System.out.println(paths(root)); // [[10, 8, 3], [10, 8, 5], [10, 2, 2]]
		System.out.println();
	}
}

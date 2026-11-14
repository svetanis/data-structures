package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 968. Binary Tree Cameras
//
// Input: the root of a binary tree. A camera placed on a node watches that node, its parent and
// its children. Returns the fewest cameras that watch every node. On LeetCode: 1 <= nodes <= 1000,
// every Node.val == 0.
//
// The one idea: each subtree reports one state to its parent, and a camera is placed only when a
// child is not watched:
//   state 0 -- the node is not watched
//   state 1 -- the node is watched by a camera on one of its children
//   state 2 -- the node has a camera
// A missing child counts as state 1, watched and needing nothing. If a child is in state 0, the
// only node that can still watch it is this one, so this node gets the camera (state 2). Waiting
// for the parent to cover a leaf is never worse: a camera on the parent watches the leaf, the
// parent, the parent's parent and the leaf's sibling, which a camera on the leaf itself would
// not. If a child has a camera, this node is watched (state 1). Otherwise both children are
// watched but hold no camera, so nothing watches this node yet (state 0), and the parent will
// decide. At the end the root may still be in state 0, and there is no parent to help, so one more
// camera goes on the root.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack.
//
// Siblings: BinaryTreeCameras carries three numbers per subtree and takes the cheapest at the
// root.

public final class BinaryTreeCamerasGreedy {

	private int count = 0;

	public int btc(TreeNode root) {
		// the count is a field, so it has to be cleared on entry:
		// without this the same instance adds each call to the last
		count = 0;
		if (dfs(root) == 0) {
			count++; // ROOT: still not watched, no parent to help
		}
		return count;
	}

	private int dfs(TreeNode node) {
		if (node == null) {
			return 1; // BASE: nothing to watch, nothing to ask for
		}
		int left = dfs(node.left);
		int right = dfs(node.right);
		// if any child not covered, place camera
		if (left == 0 || right == 0) {
			count++; // CAMERA: a child can only be watched from here
			return 2;
		}
		// if any child has camera, this node covered
		if (left == 2 || right == 2) {
			return 1; // WATCHED: by a child's camera
		}
		// if both children covered, but don't have camera
		// this node is not covered
		return 0; // WAIT: let the parent's camera watch this node
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(0);
		root1.left = newNode(0);
		root1.left.left = newNode(0);
		root1.left.right = newNode(0);
		BinaryTreeCamerasGreedy btc = new BinaryTreeCamerasGreedy();
		System.out.println(btc.btc(root1)); // 1

		TreeNode root2 = newNode(0);
		root2.left = newNode(0);
		root2.left.left = newNode(0);
		root2.left.left.left = newNode(0);
		root2.left.left.left.right = newNode(0);
		BinaryTreeCamerasGreedy btc2 = new BinaryTreeCamerasGreedy();
		System.out.println(btc2.btc(root2)); // 2

		TreeNode root3 = newNode(0);
		BinaryTreeCamerasGreedy btc3 = new BinaryTreeCamerasGreedy();
		System.out.println(btc3.btc(root3)); // 1
	}
}

package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 968. Binary Tree Cameras
//
// Input: the root of a binary tree. A camera placed on a node watches that node, its parent and
// its children. Returns the fewest cameras that watch every node. On LeetCode: 1 <= nodes <= 1000,
// every Node.val == 0.
//
// The one idea: every subtree reports three numbers, each the fewest cameras inside the subtree
// for one situation, with every node below the subtree's root watched in all three:
//   current -- there is a camera on the root; the root is watched.
//   child   -- no camera on the root, and the root is watched by a camera on one of its children.
//   parent  -- no camera on the root, none on its children, so the root is NOT watched yet; only a
//              camera on its parent can watch it.
// A camera on the root watches both children whatever they report, so current is 1 plus each
// child's best of the three. For child, at least one child must hold a camera and the other
// holds a camera or is watched from below; a child reporting `parent` would be left unwatched.
// For parent, both children must be watched from below without a camera of their own: both
// report `child`. A missing child cannot hold a camera, so it reports current as a huge number
// and 0 for the others. The root has no parent above it, so the answer is the smaller of
// current and child.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; each Result is dropped once its parent has used it.
//
// Siblings: BinaryTreeCamerasGreedy gets the same answer with one number per subtree, by placing
// a camera as high as it can.

public final class BinaryTreeCameras {

	public static int btc(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Result r = dfs(root);
		return Math.min(r.current, r.child); // RETURN: the root has no parent to watch it
	}

	private static Result dfs(TreeNode root) {
		if (root == null) {
			return new Result(Integer.MAX_VALUE / 2, 0, 0); // BASE: no camera possible, nothing to watch
		}
		Result left = dfs(root.left);
		Result right = dfs(root.right);
		int leftMin = Math.min(left.current, Math.min(left.child, left.parent));
		int rightMin = Math.min(right.current, Math.min(right.child, right.parent));
		int current = 1 + leftMin + rightMin; // CAMERA: on this node, it watches both children
		int one = left.current + right.current; // BOTH: a camera on each child
		int two = left.current + right.child; // LEFT: a camera on the left child only
		int three = left.child + right.current; // RIGHT: a camera on the right child only
		int children = Math.min(one, Math.min(two, three)); // CHILD: no camera here, watched by a child
		int parent = left.child + right.child; // PARENT: not watched, waits for the parent's camera
		return new Result(current, children, parent);
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(0);
		root1.left = newNode(0);
		root1.left.left = newNode(0);
		root1.left.right = newNode(0);
		System.out.println(btc(root1)); // 1

		TreeNode root2 = newNode(0);
		root2.left = newNode(0);
		root2.left.left = newNode(0);
		root2.left.left.left = newNode(0);
		root2.left.left.left.right = newNode(0);
		System.out.println(btc(root2)); // 2

		TreeNode root3 = newNode(0);
		System.out.println(btc(root3)); // 1 -- a lone node still needs one camera
	}

	private static class Result {
		int current;
		int child;
		int parent;

		public Result(int current, int child, int parent) {
			this.current = current;
			this.child = child;
			this.parent = parent;
		}
	}
}

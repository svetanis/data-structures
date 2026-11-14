package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 112. Path Sum
//
// Input: the root of a binary tree and an integer targetSum. Returns true if some path from the
// root down to a leaf has values adding up to targetSum; an empty tree has no path, so the
// answer is false. On LeetCode: 0 <= n <= 5000, -1000 <= val <= 1000,
// -1000 <= targetSum <= 1000.
//
// The one idea: subtract each value from the target on the way down, so what is carried is the
// sum the rest of the path must still reach. A leaf completes a path when its value equals what is
// carried; any other node asks both children with the smaller target. A node that is not a leaf
// is never accepted by itself, because a path has to end at a leaf.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: hasPath, hasPath1 and hasPath2 answer the same question, written three ways:
// hasPath and hasPath2 compare the leaf's value with the target before subtracting; hasPath1
// subtracts first and tests for 0. hasPath1 and hasPath2 make the first step themselves and then
// recurse through hasPath, not through themselves. PathSum is hasPath1 with its own recursion;
// RootToLeafPathGivenSum returns the path itself.
public final class RootToLeafPathIsGivenSum {

	public static boolean hasPath(TreeNode root, int target) {
		if (root == null) { // BASE: no node, no path
			return false;
		}
		if (isLeaf(root)) { // LEAF: the path ends here; it must use up the target exactly
			return target == root.val;
		}
		target -= root.val; // LEFT-OVER: what the rest of the path must still add up to
		boolean left = hasPath(root.left, target); // ASK: left subtree
		boolean right = hasPath(root.right, target); // ASK: right subtree
		return left || right;
	}

	public static boolean hasPath1(TreeNode root, int target) {
		if (root == null) { // BASE: no node, no path
			return false;
		}
		int sum = target - root.val; // LEFT-OVER: what the rest of the path must still add up to
		// FOUND: a leaf with nothing left over
		if (sum == 0 && isLeaf(root)) {
			return true;
		}
		boolean left = hasPath(root.left, sum); // ASK: left subtree
		boolean right = hasPath(root.right, sum); // ASK: right subtree
		return left || right;
	}

	public static boolean hasPath2(TreeNode root, int sum) {
		if (root == null) { // BASE: no node, no path
			return false;
		}
		// FOUND: a leaf whose value is the whole target
		if (root.val == sum && isLeaf(root)) {
			return true;
		}
		boolean left = hasPath(root.left, sum - root.val); // ASK: left subtree
		boolean right = hasPath(root.right, sum - root.val); // ASK: right subtree
		return left || right;
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 14 : 10->2->2
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(8);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(2);
		System.out.println(hasPath(root, 21)); // true
		System.out.println(hasPath2(root, 21)); // true
		System.out.println(hasPath1(root, 21)); // true
		System.out.println(hasPath(root, 25)); // false

		// LeetCode example 1
		TreeNode root2 = new TreeNode(5);
		root2.left = new TreeNode(4);
		root2.right = new TreeNode(8);
		root2.left.left = new TreeNode(11);
		root2.right.left = new TreeNode(13);
		root2.right.right = new TreeNode(4);
		root2.left.left.left = new TreeNode(7);
		root2.left.left.right = new TreeNode(2);
		root2.right.right.right = new TreeNode(1);
		System.out.println(hasPath(root2, 22)); // true

		// LeetCode example 2
		TreeNode root3 = new TreeNode(1);
		root3.left = new TreeNode(2);
		root3.right = new TreeNode(3);
		System.out.println(hasPath(root3, 5)); // false

		// LeetCode example 3: an empty tree
		System.out.println(hasPath(null, 0)); // false
	}
}

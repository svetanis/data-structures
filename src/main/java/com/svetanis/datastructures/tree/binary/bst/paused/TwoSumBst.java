package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 653. Two Sum IV - Input is a BST
//
// Input: the root of a binary search tree and an integer target. Returns true if two different
// nodes hold values that add up to the target. On LeetCode: 1 to 10^4 nodes,
// -10^4 <= node.val <= 10^4, -10^5 <= target <= 10^5.
//
// The one idea: the same as Two Sum on an array, ignoring the ordering. Walk the tree and keep
// a set of the values seen so far. At each node the value that would complete the pair is
// target - node.val; if the set already holds it, an earlier node and this one add up to the
// target. Otherwise add this node's value and go on. The check comes before the add, so a node
// is never paired with itself. This does not use the BST property at all -- TwoSumBsts, which
// moves two pointers along sorted lists, does.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of about six
// thousand nodes or more overflows the default stack (LC allows 10^4 nodes).
// TwoSumBstIterative is the same walk with an explicit stack.
//
// Time: O(n) -- each node is visited once, with an O(1) set lookup and insert.
// Space: O(n) -- the set, plus the O(h) recursion stack.
//
// Siblings: TwoSumBstIterative is this walk with an explicit stack; TwoSumBsts is the
// two-tree version of the problem (LC 1214), done with sorted lists and two pointers.

public final class TwoSumBst {

	public static boolean twoSum(TreeNode root, int target) {
		Set<Integer> set = new HashSet<>(); // the values of the nodes visited so far
		return dfs(root, target, set);
	}

	private static boolean dfs(TreeNode root, int target, Set<Integer> set) {
		if (root == null) { // BASE: empty subtree
			return false;
		}
		if (set.contains(target - root.val)) { // FOUND: an earlier node completes the pair
			return true;
		}
		set.add(root.val); // RECORD: after the check, so a node never pairs with itself
		boolean left = dfs(root.left, target, set);
		boolean right = dfs(root.right, target, set);
		return left || right;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.right.right = newNode(7);
		System.out.println(twoSum(root, 9)); // true -- 3 + 6
		System.out.println(twoSum(root, 28)); // false
	}
}
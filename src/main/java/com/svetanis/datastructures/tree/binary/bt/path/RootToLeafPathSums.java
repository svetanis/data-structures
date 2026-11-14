package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.List;

// Root-to-Leaf Path Sums -- not a LeetCode problem
//
// Input: the root of a binary tree. Returns the sum of the values of every root-to-leaf path,
// one number per leaf, in left-to-right order of the leaves. An empty tree gives an empty list.
// Assumes each sum fits in an int.
//
// The one idea: hand the sum so far down to each child as a number. A node adds its own value to
// what it was handed; if it is a leaf, that total is one finished path sum and goes in the list,
// otherwise the total is handed on to both children. Nothing has to be undone on the way back,
// because each call has its own copy of the number.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain); the list is extra.
//
// Siblings: RootToLeafPathsRecursive lists the values of each path instead of its sum;
// SumRootToLeafNums reads each path as a number and adds those.
public final class RootToLeafPathSums {

	public static List<Integer> paths(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		dfs(root, 0, list);
		return list;
	}

	private static void dfs(TreeNode node, int sum, List<Integer> list) {
		if (node == null) { // BASE: nothing below
			return;
		}
		sum += node.val; // TAKE: the current node joins the sum
		if (node.left == null && node.right == null) { // LEAF: the sum is complete
			list.add(sum); // RECORD
		} else {
			dfs(node.left, sum, list); // WALK: left subtree
			dfs(node.right, sum, list); // WALK: right subtree
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

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		System.out.println(paths(root)); // [21, 23, 14]
		System.out.println();
	}
}

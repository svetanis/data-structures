package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1457. Pseudo-Palindromic Paths in a Binary Tree
//
// Input: the root of a binary tree whose values are digits 1 to 9. A root-to-leaf path is
// pseudo-palindromic if its values can be rearranged into a palindrome. Returns how many
// root-to-leaf paths are. On LeetCode: 1 <= n <= 10^5, 1 <= val <= 9.
//
// The one idea: a list of digits can be rearranged into a palindrome exactly when at most one
// digit occurs an odd number of times (that one goes in the middle; every other digit is
// used in pairs). So walk down from the root keeping counter[d] = how many times digit d is on
// the path so far. At a leaf, count the digits whose counter is odd and accept the path if that
// is 0 or 1. Going back up takes the node's digit out of the counter again.
//
// Time: O(n) -- each node is visited once, and each leaf looks at 9 counters.
// Space: O(h) -- the recursion stack (h = height, n on a chain); the counter is 10 ints.
//
// Known limit: a chain tens of thousands of nodes deep (the constraints allow 10^5) overflows the
// default thread stack, because the recursion is as deep as the tree. It only happens on very
// large input.
//
// Siblings: CountPseudoPalindromicPathsIterative is the same walk with an explicit stack, so the
// depth of the tree does not matter.
public final class CountPseudoPalindromicPaths {

	public static int pathCount(TreeNode root) {
		int[] counter = new int[10]; // COUNTER: counter[d] = times digit d is on the current path
		return pathCount(root, counter);
	}

	private static int pathCount(TreeNode node, int[] counter) {
		if (isNull(node)) { // BASE: nothing below
			return 0;
		}
		counter[node.val]++; // TAKE: the current node's digit joins the path
		int count = 0;
		if (isLeaf(node)) {
			if (pseudoPalindrome(counter)) { // RECORD: the whole path is known at a leaf
				count++;
			}
		} else {
			count += pathCount(node.left, counter); // WALK: left subtree
			count += pathCount(node.right, counter); // WALK: right subtree
		}
		counter[node.val]--; // UNDO: leave the node
		return count;
	}

	private static boolean pseudoPalindrome(int[] counter) {
		int odd = 0;
		for (int i = 1; i <= 9; i++) {
			if (counter[i] % 2 == 1) { // ODD: this digit has no partner
				odd++;
			}
		}
		return odd == 0 || odd == 1; // RETURN: at most one digit may stand alone, in the middle
	}

	public static void main(String[] args) {
		TreeNode root = newNode(2);
		root.left = newNode(3);
		root.right = newNode(1);
		root.left.left = newNode(3);
		root.left.right = newNode(1);
		root.right.right = newNode(1);
		System.out.println(pathCount(root)); // 2

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(1);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(1);
		System.out.println(pathCount(root2)); // 1

		TreeNode root3 = newNode(9);
		System.out.println(pathCount(root3)); // 1
	}
}

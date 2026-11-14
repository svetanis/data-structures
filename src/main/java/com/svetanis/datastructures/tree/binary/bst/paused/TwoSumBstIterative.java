package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;
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
// is never paired with itself. The walk uses an explicit stack of the nodes still to visit, so
// a very tall tree cannot overflow the call stack, and it stops at the first pair found.
//
// Time: O(n) -- each node is pushed once and popped once, with an O(1) set lookup and insert.
// Space: O(n) -- the set; the stack holds at most one waiting node per level of the path.
//
// Siblings: TwoSumBst is the same walk, recursive; its call stack overflows on a one-sided
// chain of about six thousand nodes, this one does not.

public final class TwoSumBstIterative {

	public static boolean twoSum(TreeNode root, int target) {
		Set<Integer> set = new HashSet<>(); // the values of the nodes visited so far
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop(); // VISIT: any waiting node
			if (set.contains(target - node.val)) { // FOUND: an earlier node completes the pair
				return true;
			}
			set.add(node.val); // RECORD: after the check, so a node never pairs with itself
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		return false;
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
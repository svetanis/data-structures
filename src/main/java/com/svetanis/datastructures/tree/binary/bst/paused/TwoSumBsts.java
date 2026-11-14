package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1214. Two Sum BSTs
//
// Input: the roots of two binary search trees and an integer target. Returns true if one value
// from the first tree and one value from the second add up to the target. On LeetCode: each
// tree has 1 to 5000 nodes, -10^9 <= node.val, target <= 10^9.
//
// The one idea: the in-order walk turns each tree into an ascending list. Put a pointer
// (left) at the smallest value of the first list and a pointer (right) at the largest value of
// the second. If the two add up to the target, done. If the sum is too small, the only way to
// raise it is a larger value from the first list, so move left up; if too big, the only way to
// lower it is a smaller value from the second list, so move right down. Each step discards a
// value that cannot be part of any pair with what remains, so the pointers meet the end after
// n + m steps at most.
//
// The two lists live in a field and are emptied at the start of twoSum(), so one object can
// answer many questions.
//
// Time: O(n + m) -- one walk of each tree, then at most n + m pointer steps.
// Space: O(n + m) -- the two lists.
//
// Siblings: TwoSumBst (LC 653) is the one-tree version, done with a set.

public final class TwoSumBsts {

	private List<Integer>[] list = new List[2]; // list[0]: first tree, list[1]: second tree

	public boolean twoSum(TreeNode root1, TreeNode root2, int target) {
		Arrays.setAll(list, x -> new ArrayList<>()); // RESET: both lists empty
		dfs(root1, 0);
		dfs(root2, 1);
		int n = list[0].size();
		int m = list[1].size();
		int left = 0, right = m - 1; // smallest of the first list, largest of the second
		while (left < n && right >= 0) {
			int sum = list[0].get(left) + list[1].get(right);
			if (sum == target) {
				return true; // FOUND
			} else if (sum < target) {
				left++; // SKIP: too small, a larger first value is the only way up
			} else {
				right--; // SKIP: too big, a smaller second value is the only way down
			}
		}
		return false;
	}

	private void dfs(TreeNode root, int index) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		dfs(root.left, index); // WALK: smaller values first
		list[index].add(root.val); // RECORD: ascending order
		dfs(root.right, index);
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(2);
		root1.left = newNode(1);
		root1.right = newNode(3);

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(4);
		TwoSumBsts tsb = new TwoSumBsts();
		System.out.println(tsb.twoSum(root1, root2, 5)); // true -- 1 + 4
		System.out.println(tsb.twoSum(root1, root2, 10)); // false
	}
}
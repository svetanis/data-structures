package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 129. Sum Root to Leaf Numbers
//
// Input: the root of a binary tree whose values are digits 0 to 9. Each root-to-leaf path read
// from the root is a decimal number. Returns the sum of those numbers over all leaves; the answer
// is guaranteed to fit in an int. On LeetCode: 1 <= n <= 1000, 0 <= val <= 9, depth at most 10.
// sumMod is a variant with no LeetCode statement: the same sum, but each number and the running
// total are kept below 1003.
//
// The one idea: reading one more digit of a number multiplies what was read by 10 and adds the
// digit: number = number * 10 + digit. So carry the number read so far down to each child. A
// leaf's number is complete and is returned; any other node returns the sum of what its two
// children return. Each call has its own copy of the number, so nothing is undone.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: SumRootToLeafBinaryNums does the same in binary (shift instead of multiply by 10).
public final class SumRootToLeafNums {

	private static final int MOD = 1003;

	public static int sum(TreeNode root) {
		return sum(root, 0);
	}

	private static int sum(TreeNode root, int sum) {
		if (isNull(root)) { // BASE: no node, no number
			return 0;
		}
		sum = sum * 10 + root.val; // TAKE: shift the digits read so far, add this one
		if (isLeaf(root)) { // LEAF: the number is complete
			return sum;
		}
		int left = sum(root.left, sum); // WALK: left subtree
		int right = sum(root.right, sum); // WALK: right subtree
		return left + right;
	}

	public static int sumMod(TreeNode root) {
		return sumMod(root, 0);
	}

	private static int sumMod(TreeNode root, int sum) {
		if (isNull(root)) { // BASE: no node, no number
			return 0;
		}
		sum = (sum * 10 + root.val) % MOD; // TAKE: as above, kept below MOD
		if (isLeaf(root)) { // LEAF: the number mod MOD is complete
			return sum;
		}
		int left = sumMod(root.left, sum); // WALK: left subtree
		int right = sumMod(root.right, sum); // WALK: right subtree
		return (left + right) % MOD; // RETURN: the total, kept below MOD
	}

	public static void main(String[] args) {
		TreeNode root = newNode(6);
		root.left = newNode(3);
		root.right = newNode(5);
		root.left.left = newNode(2);
		root.right.right = newNode(4);
		root.left.right = newNode(5);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		System.out.println(sum(root)); // 13997
		System.out.println(sumMod(root)); // 958

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		System.out.println(sum(root2)); // 25

		TreeNode root3 = newNode(4);
		root3.left = newNode(9);
		root3.right = newNode(0);
		root3.left.left = newNode(5);
		root3.left.right = newNode(1);
		System.out.println(sum(root3)); // 1026
	}
}

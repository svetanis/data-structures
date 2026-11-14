package com.svetanis.datastructures.tree.binary.bst.bounds;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 333. Largest BST Subtree
//
// Input: root of a binary tree that need not be a BST. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^4 <= node.val <= 10^4. Returns the number of nodes in the largest subtree that is a BST
// (everything in the left part strictly smaller, everything in the right part strictly larger,
// all the way down). A subtree is a node together with all its descendants.
//
// The one idea: each node asks its two children one question and answers it for its parent. One
// answer, a Subtree, holds three numbers for one whole subtree that is a BST: its smallest value,
// its largest value and its node count. A node is the top of a BST exactly when both children's
// subtrees are BSTs, the left one's largest value is below the node, and the node is below the
// right one's smallest value. Then its own answer is the count 1 + left + right, with the left's
// smallest and the right's largest as its bounds. When any of that fails, the answer is absent --
// not a BST -- and every node above it is absent too. The biggest count ever produced is the
// result.
//
// What says "not a BST" and what says "no bound on this side" must not be a number. LC 333 allows
// any int as a value, Integer.MIN_VALUE and MAX_VALUE included, so neither can double as a
// marker. Absent says not a BST, and a null bound says the side is empty. ValidBstRange.java
// makes the same choice; its Integer.MIN_VALUE/MAX_VALUE version is still there, commented out.
//
// Time: O(n) -- each node is visited once and combines two answers in constant time.
// Space: O(h) -- the recursion is as deep as the tree is tall, h <= n.
//
// Siblings: bt/subtree/MaxSumBST.java (LC 1373) is this file with one more field, a sum, and it
// stays in bt/ beside bt/subtree/BinaryTreeTilt.java, the other example of a node returning a record.

public final class LargestBstInBt {

	// min and max are null when the subtree is empty, which is the only
	// reason they are Integer rather than int
	private record Subtree(Integer min, Integer max, int size) {

		private static final Subtree EMPTY = new Subtree(null, null, 0);
	}

	private int maxSize;

	public int largestBstSubTree(TreeNode root) {
		this.maxSize = 0; // RESET: a second call on the same object starts from zero
		dfs(root);
		return maxSize;
	}

	// empty means this subtree is not a BST. It has to be absence and not
	// a chosen triple of values, because every triple is also real data
	private Optional<Subtree> dfs(TreeNode root) {
		if (root == null) {
			return Optional.of(Subtree.EMPTY); // BASE: an empty subtree is a BST of size 0
		}
		Optional<Subtree> left = dfs(root.left); // WALK: children first
		Optional<Subtree> right = dfs(root.right);
		if (!left.isPresent() || !right.isPresent()) {
			return Optional.empty(); // SKIP: a child is not a BST, so neither is this subtree
		}
		return combine(root.val, left.get(), right.get());
	}

	private Optional<Subtree> combine(int data, Subtree left, Subtree right) {
		if (!fits(data, left, right)) {
			return Optional.empty(); // SKIP: the node breaks the order
		}
		int size = 1 + left.size() + right.size();
		maxSize = Math.max(maxSize, size); // RECORD: the largest BST seen so far
		// an empty side contributes no bound, so this node is the bound
		Integer min = left.min() == null ? data : left.min();
		Integer max = right.max() == null ? data : right.max();
		return Optional.of(new Subtree(min, max, size));
	}

	// an empty side imposes nothing, so it always fits
	private static boolean fits(int data, Subtree left, Subtree right) {
		boolean above = left.max() == null || left.max() < data; // the left side is all below the node
		boolean below = right.min() == null || data < right.min(); // the right side is all above it
		return above && below;
	}

	public static void main(String[] args) {
		LargestBstInBt lbst = new LargestBstInBt();
		TreeNode root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(60);
		root.left.left = newNode(5);
		root.left.right = newNode(20);
		root.right.left = newNode(45);
		root.right.right = newNode(70);
		root.right.right.left = newNode(65);
		root.right.right.right = newNode(80);
		System.out.println(lbst.largestBstSubTree(root)); // 5

		// a three node BST whose smallest value is Integer.MIN_VALUE
		TreeNode extremes = newNode(0);
		extremes.left = newNode(Integer.MIN_VALUE);
		extremes.right = newNode(Integer.MAX_VALUE);
		System.out.println(lbst.largestBstSubTree(extremes)); // 3
		System.out.println(lbst.largestBstSubTree(newNode(Integer.MIN_VALUE))); // 1
		System.out.println(lbst.largestBstSubTree(null)); // 0
	}
}

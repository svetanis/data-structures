package com.svetanis.datastructures.tree.binary.bst.bounds;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 333. Largest BST Subtree

// Given a binary tree, find the largest subtree 
// which is a Binary Search Tree (BST), 
// where largest means subtree with largest number of nodes in it.

// NOTE what says "not a BST" and what says "no bound on this side".
// LC 333 allows any int as a node value, Integer.MIN_VALUE and MAX_VALUE
// included, so neither of them can double as a marker -- a tree holding one
// would have the marker mistaken for data. Absent says not a BST, and a null
// bound says the side is empty. ValidBstRange.java made the same choice, and
// its Integer.MIN_VALUE/MAX_VALUE version is still there, commented out.

public final class LargestBstInBt {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	// min and max are null when the subtree is empty, which is the only
	// reason they are Integer rather than int
	private record Subtree(Integer min, Integer max, int size) {

		private static final Subtree EMPTY = new Subtree(null, null, 0);
	}

	private int maxSize;

	public int largestBstSubTree(Node root) {
		this.maxSize = 0;
		dfs(root);
		return maxSize;
	}

	// absent means this subtree is not a BST. It has to be absence and not
	// a chosen triple of values, because every triple is also real data
	private Optional<Subtree> dfs(Node root) {
		if (root == null) {
			return of(Subtree.EMPTY);
		}
		Optional<Subtree> left = dfs(root.left);
		Optional<Subtree> right = dfs(root.right);
		if (!left.isPresent() || !right.isPresent()) {
			return absent();
		}
		return combine(root.data, left.get(), right.get());
	}

	private Optional<Subtree> combine(int data, Subtree left, Subtree right) {
		if (!fits(data, left, right)) {
			return absent();
		}
		int size = 1 + left.size() + right.size();
		maxSize = Math.max(maxSize, size);
		// an empty side contributes no bound, so this node is the bound
		Integer min = left.min() == null ? data : left.min();
		Integer max = right.max() == null ? data : right.max();
		return of(new Subtree(min, max, size));
	}

	// an empty side imposes nothing, so it always fits
	private static boolean fits(int data, Subtree left, Subtree right) {
		boolean above = left.max() == null || left.max() < data;
		boolean below = right.min() == null || data < right.min();
		return above && below;
	}

	public static void main(String[] args) {
		LargestBstInBt lbst = new LargestBstInBt();
		Node root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(60);
		root.left.left = newNode(5);
		root.left.right = newNode(20);
		root.right.left = newNode(45);
		root.right.right = newNode(70);
		root.right.right.left = newNode(65);
		root.right.right.right = newNode(80);
		System.out.println(lbst.largestBstSubTree(root)); // 5

		// a three node BST whose smallest value is the old marker.
		// the {MAX, MIN} version answered 1 here
		Node extremes = newNode(0);
		extremes.left = newNode(Integer.MIN_VALUE);
		extremes.right = newNode(Integer.MAX_VALUE);
		System.out.println(lbst.largestBstSubTree(extremes)); // 3
		System.out.println(lbst.largestBstSubTree(newNode(Integer.MIN_VALUE))); // 1
	}
}

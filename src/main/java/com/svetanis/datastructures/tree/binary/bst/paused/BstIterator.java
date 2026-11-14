package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 173. Binary Search Tree Iterator
//
// Input: the root of a binary search tree. Provides next(), which returns the next smallest
// value not yet returned, and hasNext(), which says whether there is one. (seek(x) below is an
// addition to LeetCode's interface.) On LeetCode: 1 to 10^5 nodes, 0 <= node.val <= 10^6, at
// most 10^5 calls, and next() is only called when a next value exists.
//
// The one idea: an in-order walk, paused after every value. A walk keeps the nodes whose
// left subtree it has gone into and not yet come back from; the iterator keeps exactly those
// nodes in a stack, the next value to return on top. One stack entry is one node that is
// waiting for its turn. next() pops the top node (the smallest remaining), then pushes the
// left spine of that node's right subtree, which holds the values that come next.
//
// next(), hasNext() and pushLeft() never compare values: pushLeft follows left links and
// next() follows right links. So bt/iterator/BtIterator runs the identical code on a tree
// with no ordering at all, and neither version can be faster than the other. What the BST
// adds is the meaning of the sequence -- it is sorted -- and seek(int) below is what that
// meaning is worth: a method the binary-tree version cannot have.
//
// Time: next() is amortized O(1) and O(h) for a single call -- every node is pushed once and
// popped once across the whole iteration, so n calls do O(n) work between them. hasNext() is
// O(1). seek(x) is O(h).
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: bt/iterator/BtIterator is the same iterator over a tree with no order;
// KthSmallestIterative and KClosestToTargetIterator use the same stack walk.

public final class BstIterator {

	private final TreeNode root;
	private Deque<TreeNode> dq;

	public BstIterator(TreeNode root) {
		this.root = root;
		this.dq = new ArrayDeque<>();
		pushLeft(root);
	}

	public boolean hasNext() {
		return !dq.isEmpty(); // a node is waiting for its turn
	}

	public int next() {
		TreeNode node = dq.pop(); // TAKE: the smallest value not yet returned
		pushLeft(node.right); // PREPARE: the values that come after it
		return node.val;
	}

	// reposition so that the next call to next() returns the
	// smallest value >= x. O(h), whatever x is and wherever the
	// iterator currently stands -- including BACKWARDS, to a value
	// already emitted, because nothing has been consumed.

	// the descent is the ceiling hunt: a node >= x might be the
	// answer, so remember it and look left for a smaller one that
	// still qualifies; a node < x cannot be the answer and neither
	// can anything on its left, so go right.

	// the nodes kept are exactly the pending ancestors the walk
	// would have had if it had arrived here one next() at a time,
	// which is why the iterator carries on correctly afterwards.
	public void seek(int x) {
		dq.clear();
		TreeNode node = root;
		while (isNotNull(node)) {
			if (node.val >= x) {
				dq.push(node); // KEEP: a candidate, and a node still waiting for its turn
				node = node.left; // a smaller one may still qualify
			} else {
				node = node.right; // SKIP: this node and its left subtree are all below x
			}
		}
	}

	// the loop, not the recursion: the deque already costs O(h), and
	// recursing here spends a second O(h) on the call stack to walk the
	// same spine
	private void pushLeft(TreeNode root) {
		while (isNotNull(root)) {
			dq.push(root); // PUSH: wait for the left side to be returned first
			root = root.left;
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(10);
		root.left = newNode(1);
		root.right = newNode(11);
		root.left.right = newNode(6);
		root.right.right = newNode(12);

		BstIterator iter = new BstIterator(root);
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println(); // 1 6 10 11 12

		// seek jumps to a value without emitting the ones before it,
		// and jumps back again -- neither is possible on bt/BtIterator
		BstIterator seeking = new BstIterator(root);
		seeking.seek(11);
		System.out.println(seeking.next()); // 11
		seeking.seek(6);
		System.out.println(seeking.next()); // 6
		seeking.seek(7);
		System.out.println(seeking.next()); // 10 -- nothing is 7, the next above it
		seeking.seek(13);
		System.out.println(seeking.hasNext()); // false -- nothing is >= 13
	}
}

package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 173. Binary Search Tree Iterator

// implement an iterator over a BST
// iterator initialized with the root node
// next() returns the next smallest num in BST

// PAIRED WITH bt/iterator/BtIterator, ON PURPOSE.
// next()/hasNext()/pushLeft below never read a value to
// decide anything -- pushLeft follows left links, next()
// follows right links, and nothing is compared. So that
// class runs the identical code on a tree with no ordering
// at all, and neither version can be made faster than the
// other. What the BST buys is not the iteration; it is what
// the emitted sequence MEANS, which is: sorted.

// seek(int) below is what that meaning is worth, and it is
// the one method the binary-tree version cannot have.

public final class BstIterator {
	// next(): amortized O(1), worst case O(h) for a single call --
	// every node is pushed once and popped once across the whole
	// iteration, so n calls do O(n) work between them
	// Space Complexity: O(h)

	private final Node root;
	private Deque<Node> dq;

	public BstIterator(Node root) {
		this.root = root;
		this.dq = new ArrayDeque<>();
		pushLeft(root);
	}

	public boolean hasNext() {
		return !dq.isEmpty();
	}

	public int next() {
		Node node = dq.pop();
		pushLeft(node.right);
		return node.data;
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
		Node node = root;
		while (isNotNull(node)) {
			if (node.data >= x) {
				dq.push(node);
				node = node.left;
			} else {
				node = node.right;
			}
		}
	}

	// the loop, not the recursion: the deque already costs O(h), and
	// recursing here spends a second O(h) on the call stack to walk the
	// same spine
	private void pushLeft(Node root) {
		while (isNotNull(root)) {
			dq.push(root);
			root = root.left;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(10);
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

package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// an iterator over the inorder traversal of ANY binary tree

// Implement an iterator over a binary tree. The iterator is
// initialized with the root node. next() returns the next
// value in the inorder traversal; hasNext() says whether
// there is one. next() and hasNext() should run in average
// O(1) time and use O(h) memory, where h is the height.

// PAIRED WITH bst/paused/BstIterator, ON PURPOSE.
// The constructor, hasNext(), next() and pushLeft are line
// for line identical in the two files -- before seek() was
// added there, the only difference in either class was a
// parameter name. That is the point rather than an accident:
// pushLeft follows left links, next() follows right links,
// and no value is ever compared to anything. Nothing in the
// iteration needs an ordering, so the BST version cannot be
// shorter or faster than this one.

// What the BST version HAS, and this one cannot, is seek(x):
// reposition to the smallest value >= x in O(h). See
// smallestAtLeast below for why the same request costs a
// full scan here.

public final class BtIterator {
	// next(): amortized O(1), worst case O(h) for a single call --
	// every node is pushed once and popped once across the whole
	// iteration, so n calls do O(n) work between them
	// Space Complexity: O(h)

	private Deque<Node> dq;

	public BtIterator(Node root) {
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

	// the smallest remaining value >= x -- the request that
	// bst/paused/BstIterator answers with a seek(x) descent.

	// here every remaining value has to be read, and the loop
	// below is not a lazy implementation of a better idea: on an
	// unordered tree a node's value says nothing about which way
	// a smaller qualifying value lies, so no subtree can be
	// skipped and no early exit is available. Finding that no
	// such value exists costs exactly the same as finding one.

	// it also CONSUMES the iterator, where seek(x) leaves the
	// BST version able to seek again, in either direction.
	public Optional<Integer> smallestAtLeast(int x) {
		Integer best = null;
		while (hasNext()) {
			int value = next();
			if (value >= x && (best == null || value < best)) {
				best = value;
			}
		}
		return best == null ? absent() : of(best);
	}

	// the loop, not the recursion: the deque already costs O(h), and
	// recursing here spends a second O(h) on the call stack to walk the
	// same spine
	private void pushLeft(Node node) {
		while (isNotNull(node)) {
			dq.push(node);
			node = node.left;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(10);
		root.left = newNode(1);
		root.right = newNode(11);
		root.left.right = newNode(6);
		root.right.right = newNode(12);
		BtIterator iter = new BtIterator(root);
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println(); // without this the next line runs into this one

		Node root2 = newNode(7);
		root2.left = newNode(3);
		root2.right = newNode(15);
		root2.right.left = newNode(9);
		root2.right.right = newNode(20);
		BtIterator iter2 = new BtIterator(root2);
		System.out.println(iter2.next()); // 3
		System.out.println(iter2.next()); // 7
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 9
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 15
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 20
		System.out.println(iter2.hasNext()); // false

		// the same shape holding values in no order at all:
		// inorder emits 20, 3, 15, 9, 7 -- the walk is unchanged,
		// only what the sequence means is gone
		Node any = newNode(15);
		any.left = newNode(3);
		any.left.left = newNode(20);
		any.right = newNode(7);
		any.right.left = newNode(9);
		BtIterator iter3 = new BtIterator(any);
		while (iter3.hasNext()) {
			System.out.print(iter3.next() + " ");
		}
		System.out.println(); // 20 3 15 9 7

		BtIterator iter4 = new BtIterator(any);
		System.out.println(iter4.smallestAtLeast(8)); // Optional.of(9), after reading all five
		BtIterator iter5 = new BtIterator(any);
		System.out.println(iter5.smallestAtLeast(21)); // Optional.absent(), also after all five
	}
}

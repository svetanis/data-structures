package com.svetanis.datastructures.tree.ternary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

// One node of a tree whose nodes may have any number of children (the package name says ternary,
// but nothing limits a node to three). Not a LeetCode problem.
//
// Input: build(s, f) reads a tree written in preorder as tokens separated by spaces: a node's
// value, then the number of its children, then each child written the same way. So
// "1 3 2 1 3 0 4 0 6 0" is the node 1 with three children: the node 2 with one child, the leaf 3,
// then the leaves 4 and 6. f turns a value token into the node's value. Returns the root.
//
// The one idea: the count after each value says exactly how many subtrees follow, so one pass
// over the tokens, taking the next token each time it is needed, rebuilds the tree with no
// brackets and no lookahead: read a value, read a count, then build that many children by the
// same steps.
//
// Time: O(n) -- each token is read once.
// Space: O(n) for the nodes, plus O(h) recursion stack for a tree of height h.

public final class Node<T> {
	private T val;
	private List<Node<T>> children;

	public Node(T val, List<Node<T>> children) {
		this.val = val;
		this.children = children;
	}

	public static <T> boolean isLeaf(Node<T> node) {
		return node.children.size() == 0;
	}

	public static <T> boolean isNotNull(Node<T> node) {
		return !isNull(node);
	}

	public static <T> boolean isNull(Node<T> node) {
		return node == null;
	}

	public static <T> Node<T> build(String s, Function<String, T> f) {
		Iterator<String> iter = Arrays.asList(s.trim().split("\\s+")).iterator(); // TOKENS: by spaces
		return build(iter, f);
	}

	public static <T> Node<T> build(Iterator<String> iter, Function<String, T> f) {
		String val = iter.next(); // READ: this node's value
		int size = Integer.parseInt(iter.next()); // READ: how many children follow
		List<Node<T>> children = new ArrayList<>();
		for (int i = 0; i < size; i++) { // BUILD: that many subtrees, in order
			children.add(build(iter, f));
		}
		return new Node<>(f.apply(val), children);
	}

	public T getVal() {
		return val;
	}

	public List<Node<T>> getChildren() {
		return children;
	}
}

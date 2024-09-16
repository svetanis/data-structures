package com.svetanis.datastructures.tree.ternary;

import static com.svetanis.java.base.Splitters.split;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;

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
		Iterator<String> iter = split(' ', s).iterator();
		return build(iter, f);
	}

	public static <T> Node<T> build(Iterator<String> iter, Function<String, T> f) {
		String val = iter.next();
		int size = Integer.parseInt(iter.next());
		List<Node<T>> children = new ArrayList<>();
		for (int i = 0; i < size; i++) {
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

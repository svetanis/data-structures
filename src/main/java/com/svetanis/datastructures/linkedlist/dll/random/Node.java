package com.svetanis.datastructures.linkedlist.dll.random;

public final class Node {

	public int val;
	public Node next;
	public Node rand;

	public Node() {
		this(0);
	}

	public Node(int val) {
		this.val = val;
		this.next = null;
		this.rand = null;
	}

	@Override
	public String toString() {
		return Integer.toString(val);
	}
}
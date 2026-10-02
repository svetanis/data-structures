package com.svetanis.datastructures.linkedlist.dll.random;

// One node of LC 138 (Copy List with Random Pointer): val, next, and random, which points to
// any node of the same list, itself included, or is null. It matches LeetCode's Node for
// 138, which uses the same three field names; there is no prev pointer.

public final class Node {

	public int val;
	public Node next;
	public Node random;

	public Node() {
		this(0);
	}

	public Node(int val) {
		this.val = val;
		this.next = null;
		this.random = null;
	}

	@Override
	public String toString() {
		return Integer.toString(val);
	}
}
package com.svetanis.datastructures.linkedlist.dll.random;

// One node of LC 138 (Copy List with Random Pointer): val, next, and random, which points to
// any node of the same list, itself included, or is null. It matches LeetCode's Node for
// 138, which uses the same three field names; there is no prev pointer. The fields are public
// on purpose, as in LeetCode's own node classes, so a solution written against it can be
// pasted into LeetCode unchanged.

public final class Node {

	public int val;
	public Node next;
	public Node random;

	public Node() {
		this(0);
	}

	public Node(int val) {
		this.val = val;
		this.next = null; // a new node points nowhere yet
		this.random = null;
	}

	@Override
	public String toString() {
		return Integer.toString(val); // RETURN: the value alone, so print shows 1 2 3
	}
}
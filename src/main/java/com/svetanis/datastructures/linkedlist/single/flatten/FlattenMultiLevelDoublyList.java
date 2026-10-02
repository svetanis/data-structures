package com.svetanis.datastructures.linkedlist.single.flatten;

// 430. Flatten a Multilevel Doubly Linked List
//
// Each node has next, prev, and child; child may point to the head of another doubly linked
// list, which may have children of its own. Return the head of one doubly linked list with
// every node: each child list comes right after its parent node, before the parent's next.
// Every child pointer ends up null.
//
// flatten(prev, curr) attaches curr after prev, flattens curr's child list right behind
// curr, then carries on with curr's old next behind the last node of that child list, and
// returns the last node it attached. A fake node in front of the head gives the head a node
// to be attached to, so the head is not a special case.

public final class FlattenMultiLevelDoublyList {
	// Time Complexity: O(n), each node is attached once
	// Space Complexity: O(n), flatten calls itself once per node, and up to n calls wait at once

	public static Node flatten(Node head) {
		if (head == null) {
			return null;
		}
		Node dummy = new Node();
		dummy.next = head;
		flatten(dummy, head);
		dummy.next.prev = null; // the head's prev pointed at the fake node
		return dummy.next;
	}

	private static Node flatten(Node prev, Node curr) {
		if (curr == null) {
			return prev;
		}
		// connect the curr node to prev
		curr.prev = prev;
		prev.next = curr;

		Node temp = curr.next; // saved: the child list is attached to curr.next first
		Node tail = flatten(curr, curr.child); // the last node of the flattened child list, or curr
		curr.child = null; // LC 430 wants every child pointer null
		return flatten(tail, temp); // the rest of this level goes after the child list
	}

	public static void main(String[] args) {}

	private static class Node {
		private int val;
		private Node prev;
		private Node next;
		private Node child;

	}

}

package com.svetanis.datastructures.linkedlist.single.flatten;

// 430. Flatten a Multilevel Doubly Linked List
//
// Input: the head of a doubly linked list whose nodes have next, prev, and child; a child may
// point to the head of another doubly linked list, which may have children of its own. On
// LeetCode: at most 1000 nodes, 1 <= val <= 10^5. Returns the head of one doubly linked list
// with every node: each child list comes right after its parent node, before the parent's next.
// Every child pointer ends up null.
//
// The one idea: the answer reads the nodes in the order a depth-first walk meets them: a node,
// then everything under its child, then the rest of its own row. flatten(prev, curr) attaches
// curr after prev, flattens curr's child list right behind curr, then carries on with curr's old
// next behind the last node of that child list, and returns the last node it attached. That last
// node is what lets the rest of the row be hooked on behind a child list of any depth. A fake
// node in front of the head gives the head a node to be attached to, so the head is not a
// special case.
//
// Time: O(n) -- each node is attached once.
// Space: O(n) -- flatten calls itself once per node, and up to n calls wait at once.
//
// Siblings: FlattenMultiLevelDoublyListStack does the same with a stack of put-aside rows and no
// recursion.

public final class FlattenMultiLevelDoublyList {

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
			return prev; // BASE: nothing to attach, the last node so far is prev
		}
		// connect the curr node to prev
		curr.prev = prev; // ATTACH in both directions
		prev.next = curr;

		Node temp = curr.next; // saved: the child list is attached to curr.next first
		Node tail = flatten(curr, curr.child); // the last node of the flattened child list, or curr
		curr.child = null; // CLEAR: LC 430 wants every child pointer null
		return flatten(tail, temp); // the rest of this level goes after the child list
	}

	public static void main(String[] args) {
		// LeetCode example 1: 1 2 3 4 5 6, with 3 owning the row 7 8 9 10, and 8 owning 11 12
		Node head = row(1, 2, 3, 4, 5, 6);
		head.next.next.child = row(7, 8, 9, 10);
		head.next.next.child.next.child = row(11, 12);
		print(flatten(head)); // 1 2 3 7 8 11 12 9 10 4 5 6

		// LeetCode example 2: 1 2, with 1 owning the row 3
		Node head2 = row(1, 2);
		head2.child = row(3);
		print(flatten(head2)); // 1 3 2

		print(flatten(null)); // (empty line)
	}

	private static final String VALUE = "%d ";

	// a row of nodes joined by next and prev
	private static Node row(int... values) {
		Node head = null;
		Node tail = null;
		for (int value : values) {
			Node node = new Node();
			node.val = value;
			if (head == null) {
				head = node;
			} else {
				tail.next = node;
				node.prev = tail;
			}
			tail = node;
		}
		return head;
	}

	// the values along next
	private static void print(Node head) {
		for (Node curr = head; curr != null; curr = curr.next) {
			System.out.print(VALUE.formatted(curr.val));
		}
		System.out.println();
	}

	private static class Node {
		private int val;
		private Node prev;
		private Node next;
		private Node child;

	}

}

package com.svetanis.datastructures.linkedlist.dll.random;

import static com.svetanis.java.base.collect.Maps.newMap;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

// 138. Copy List with Random Pointer
//
// Each node has a next pointer and a random pointer to any node in the list, or null.
// Return a deep copy: new nodes only, with every pointer aimed at the matching new node.
//
// A random pointer can aim forward, at a node not copied yet, so copy in two passes. The
// first makes a copy of every node and records original -> copy in a map; the second sets
// each copy's next and random by looking up the copies of the original's targets.

public final class CloneWithRandomHashing {
	// Time Complexity: O(n)
	// Space Complexity: O(n) for the map

	public static Node clone(Node head) {
		Map<Node, Node> copyOf = copies(head); // PASS 1: every copy exists before any arrow is set
		Node curr = head; // PASS 2 walks the originals again, from the head
		while (curr != null) {
			Node copy = copyOf.get(curr);
			copy.next = copyOf.get(curr.next); // WIRE: null maps to null, get(null) finds nothing
			copy.random = copyOf.get(curr.random); // WIRE: the copy of the original's target
			curr = curr.next;
		}
		return copyOf.get(head); // RETURN the copy of the head, never head itself
	}

	// original -> its copy, pointers not set yet
	private static ImmutableMap<Node, Node> copies(Node head) {
		Map<Node, Node> copyOf = new HashMap<>();
		Node curr = head;
		while (curr != null) {
			copyOf.put(curr, new Node(curr.val)); // COPY and RECORD original -> copy; no arrows yet
			curr = curr.next;
		}
		return newMap(copyOf);
	}

	private static final String NODE = "[%d, %d] ";

	public static void main(String[] args) {
		Node head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(4);
		head.next.next.next.next = new Node(5);

		// the random pointers
		head.random = head.next.next;
		head.next.random = head.next.next.next;
		head.next.next.random = head.next.next.next.next;
		head.next.next.next.next.random = head.next;

		Node clone = clone(head);

		System.out.println("original linked list: ");
		print(head); // [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]

		System.out.println("cloned linked list: ");
		print(clone); // [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]
	}

	// each node as [value, value of its random target], -1 for none
	private static void print(Node head) {
		for (Node curr = head; curr != null; curr = curr.next) {
			int randomValue = curr.random == null ? -1 : curr.random.val;
			System.out.print(NODE.formatted(curr.val, randomValue));
		}
		System.out.println();
	}
}

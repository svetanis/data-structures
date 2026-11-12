package com.svetanis.datastructures.linkedlist.dll.random;

import java.util.HashMap;
import java.util.Map;

// 138. Copy List with Random Pointer
//
// Input: the head of a linked list in which every node has a next pointer and a random pointer,
// the random one aimed at any node of the same list, or null. On LeetCode: 0 <= number of nodes
// <= 1000, -10^4 <= val <= 10^4. Returns a deep copy: only new nodes, with every next and random
// pointer aimed at the new node that matches the old target. The original list is left as it was.
//
// The one idea: one node is a value plus two arrows, and a copy needs its own arrows, aimed at
// copies. A random arrow can aim forward, at a node that has no copy yet, so the copying takes
// two passes. Pass 1 makes a lone copy of every node (a value, no arrows) and writes
// original -> copy in a map. Pass 2 walks the originals again; for each one the map answers
// "which copy belongs to the node this arrow aims at", and the copy gets that arrow. In pass 2
// every copy already exists, so no arrow can aim at a node that is missing.
//
// Time: O(n) -- two passes over the list, and each map put or get is O(1) on average.
// Space: O(n) -- the map holds one entry per node.
//
// Siblings: CloneWithRandomNoExtraSpace and CloneWithRandomSubmit use no map: they weave each
// copy in right after its original, so the copy of any node X is X.next.

public final class CloneWithRandomHashing {

	public static Node clone(Node head) {
		Map<Node, Node> copyOf = copies(head); // PASS 1: every copy exists before any arrow is set
		Node curr = head; // PASS 2 walks the originals again, from the head
		while (curr != null) {
			Node copy = copyOf.get(curr);
			copy.next = copyOf.get(curr.next); // WIRE: null maps to null, get(null) finds nothing
			copy.random = copyOf.get(curr.random); // WIRE: the copy of the original's target
			curr = curr.next; // WALK to the next original
		}
		return copyOf.get(head); // RETURN the copy of the head, never head itself
	}

	// original -> its copy, pointers not set yet
	private static Map<Node, Node> copies(Node head) {
		Map<Node, Node> copyOf = new HashMap<>();
		Node curr = head;
		while (curr != null) {
			copyOf.put(curr, new Node(curr.val)); // COPY and RECORD original -> copy; no arrows yet
			curr = curr.next; // WALK
		}
		return copyOf;
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

		System.out.println("original linked list: "); // original linked list:
		print(head); // [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]

		System.out.println("cloned linked list: "); // cloned linked list:
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

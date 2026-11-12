package com.svetanis.datastructures.linkedlist.dll.random;

// 138. Copy List with Random Pointer
//
// Input: the head of a linked list in which every node has a next pointer and a random pointer,
// the random one aimed at any node of the same list, or null. On LeetCode: 0 <= number of nodes
// <= 1000, -10^4 <= val <= 10^4. Returns a deep copy: only new nodes, with every next and random
// pointer aimed at the new node that matches the old target. The original list is left as it was.
//
// The one idea: a map from original to copy is only a way to answer "where is the copy of X".
// The list itself can answer that: put each copy right after its original, A -> A' -> B -> B',
// and the copy of any node X is simply X.next. Three passes. Pass 1 inserts every copy after its
// original. Pass 2 gives each copy its random arrow: the copy of the original's target is
// target.next. Pass 3 pulls the two lists apart again, restoring every original next pointer.
//
// Time: O(n) -- three passes over the list.
// Space: O(1) -- besides the n new nodes that are the answer, only a few pointers.
//
// Siblings: CloneWithRandomNoExtraSpace is this same method written with while loops;
// CloneWithRandomHashing keeps a map from original to copy instead, at O(n) extra space.

public final class CloneWithRandomSubmit {

	public static Node clone(Node head) {
		if (head == null) {
			return null;
		}

		// 1. insert a copy of each node right after it
		for (Node curr = head; curr != null;) {
			Node clone = new Node(curr.val); // COPY
			clone.next = curr.next; // LINK the copy to the next original
			curr.next = clone; // INSERT the copy right after its original
			curr = clone.next; // WALK: the next original
		}

		// 2. set each copy's random: the copy of curr.random is curr.random.next
		for (Node curr = head; curr != null; curr = curr.next.next) { // WALK original to original
			if (curr.random != null) {
				curr.next.random = curr.random.next; // RANDOM: the copy of the target is target.next
			}
		}

		// 3. separate original and copied lists
		Node cloneHead = head.next;
		for (Node curr = head; curr != null;) {
			Node clone = curr.next;
			curr.next = clone.next; // SEPARATE: restore the original's next
			clone.next = clone.next != null ? clone.next.next : null; // the next copy, or null
			curr = curr.next; // WALK: the next original
		}
		return cloneHead; // RETURN the copy of the head
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

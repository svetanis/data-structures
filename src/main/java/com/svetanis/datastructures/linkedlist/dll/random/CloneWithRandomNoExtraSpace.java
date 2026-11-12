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
// Siblings: CloneWithRandomHashing keeps a map from original to copy instead; the answer is the
// same, the extra space is O(n). CloneWithRandomSubmit is this same method written with for loops.

public final class CloneWithRandomNoExtraSpace {

	public static Node clone(Node head) {
		if (head == null) {
			return null;
		}

		// 1. insert a copy of each node right after it
		Node curr = head;
		while (curr != null) {
			Node next = curr.next; // SAVE the next original
			curr.next = new Node(curr.val); // INSERT the copy right after its original
			curr.next.next = next; // LINK the copy to the next original
			curr = next; // WALK: the next original, two boxes on now
		}

		// 2. set each copy's random: the copy of curr.random is curr.random.next
		curr = head;
		while (curr != null) {
			// RANDOM: the copy of any X is X.next
			curr.next.random = curr.random == null ? null : curr.random.next;
			curr = curr.next.next; // WALK to the next original
		}

		// 3. separate original and copied lists
		Node given = head;
		Node clone = head.next;
		Node cloneHead = clone;
		while (given != null) {
			given.next = clone.next; // SEPARATE: the next original, or null
			clone.next = clone.next == null ? null : clone.next.next; // the next copy, or null
			given = given.next; // WALK
			clone = clone.next; // already the next copy, or null: never reads through a null given
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

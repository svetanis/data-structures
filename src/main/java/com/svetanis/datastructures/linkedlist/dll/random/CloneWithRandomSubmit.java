package com.svetanis.datastructures.linkedlist.dll.random;

// 138. Copy List with Random Pointer
//
// Each node has a next pointer and a random pointer to any node in the list, or null.
// Return a deep copy: new nodes only, with every pointer aimed at the matching new node.
//
// No map: each copy is put right after its original, A -> A' -> B -> B' -> ..., so the copy
// of any node X is X.next. That sets every random pointer in one pass, and a third pass
// separates the two lists again.

public final class CloneWithRandomSubmit {
	// Time Complexity: O(n), three passes
	// Space Complexity: O(1) besides the copy itself

	public static Node clone(Node head) {
		if (head == null) {
			return null;
		}

		// 1. insert a copy of each node right after it
		for (Node curr = head; curr != null;) {
			Node clone = new Node(curr.val);
			clone.next = curr.next;
			curr.next = clone;
			curr = clone.next; // the next original
		}

		// 2. set each copy's random: the copy of curr.random is curr.random.next
		for (Node curr = head; curr != null; curr = curr.next.next) {
			if (curr.random != null) {
				curr.next.random = curr.random.next;
			}
		}

		// 3. separate original and copied lists
		Node cloneHead = head.next;
		for (Node curr = head; curr != null;) {
			Node clone = curr.next;
			curr.next = clone.next; // restore the original's next
			clone.next = clone.next != null ? clone.next.next : null; // the next copy, or null
			curr = curr.next; // the next original
		}
		return cloneHead;
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

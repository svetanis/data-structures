package com.svetanis.datastructures.linkedlist.dll.random;

// 138. Copy List with Random Pointer

public final class CloneWithRandomSubmit {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node clone(Node head) {
		if (head == null) {
			return null;
		}

		// 1. insert copy of the node
		// after every node in the list
		for (Node curr = head; curr != null;) {
			// clone node
			Node clone = new Node(curr.val);
			// set clone's next to current node's next
			clone.next = curr.next;
			// insert cloned node after the current node
			curr.next = clone;
			// move to the next original node
			curr = clone.next;
		}

		// 2. adjust random pointers
		// of the newly added nodes
		for (Node curr = head; curr != null; curr = curr.next.next) {
			if (curr.rand != null) {
				// set the cloned node's random to the
				// cloned node of the original node's random
				curr.next.rand = curr.rand.next;
			}
		}

		// 3. separate original and copied lists
		Node cloneHead = head.next;
		for (Node curr = head; curr != null;) {
			Node clone = curr.next;
			// restore the original list's next pointer
			curr.next = clone.next;
			// set cloned node's next pointer
			clone.next = clone.next != null ? clone.next.next : null;
			// move to the next original node
			curr = curr.next;
		}
		return cloneHead;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(4);
		head.next.next.next.next = new Node(5);

		// Setting up random references
		head.rand = head.next.next;
		head.next.rand = head.next.next.next;
		head.next.next.rand = head.next.next.next.next;
		head.next.next.next.next.rand = head.next;

		// making a clone of the original linked list
		Node clone = clone(head);

		System.out.println("original linked list: ");
		print(head);

		System.out.println("cloned linked list: ");
		print(clone);
	}

	private static void print(Node head) {
		Node curr = head;
		while (curr != null) {
			Node rand = curr.rand;
			int randomData = (rand != null) ? rand.val : -1;
			System.out.print("[" + curr.val + ", " + randomData + "] ");
			curr = curr.next;
		}
		System.out.println();
	}

	// original linked list:
	// [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]
	// cloned linked list:
	// [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]

}

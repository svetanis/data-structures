package com.svetanis.datastructures.linkedlist.single.flatten;

import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.printNext;

// Flatten a Multilevel Linked List, Level by Level
//
// Each node has a next pointer to the node on its right and a down pointer to the head of
// another list, or null. Join every list into one list along next pointers: the first row,
// then each list one level down, left to right, then the level below that, and so on. The
// list is changed in place and nothing is returned; down pointers are left as they were.
//
// tail holds the last node of the joined list. curr walks the joined list from the head;
// whenever curr has a down list, that list is attached after tail and tail moves to its end.
// A list attached earlier is walked earlier, so its own down lists are attached earlier too,
// and the levels come out in order.

public final class FlattenMultiLevelList {
	// Time Complexity: O(n), curr passes each node once, and so does the walk to each new tail
	// Space Complexity: O(1), a few pointers

	public static void flatten(Node head) {
		if (head == null) {
			return;
		}

		// find tail node of first level linked list
		Node tail = head;
		while (tail.next != null) {
			tail = tail.next;
		}

		// one by one traverse through all nodes of first level
		// linked list till we reach the tail node
		Node curr = head;
		while (curr != null) { // the last node too: it may have a down list of its own
			// if current node has child
			if (curr.down != null) {
				// then append the child at the end of current list
				tail.next = curr.down;
				// and update the tail to new last node
				Node temp = curr.down;
				while (temp.next != null) {
					temp = temp.next;
				}
				tail = temp;
			}
			curr = curr.next;
		}
	}

	public static void main(String[] args) {
		Node root = null;
		root = createList();
		flatten(root);
		printNext(root); // 10 5 12 7 11 4 20 13 17 6 2 16 9 8 3 19 15
	}

	private static Node createList() {
		int a1[] = { 10, 5, 12, 7, 11 };
		int a2[] = { 4, 20, 13 };
		int a3[] = { 17, 6 };
		int a4[] = { 9, 8 };
		int a5[] = { 19, 15 };
		int a6[] = { 2 };
		int a7[] = { 16 };
		int a8[] = { 3 };

		// create 8 linked lists
		Node head1 = fromArray(a1);
		Node head2 = fromArray(a2);
		Node head3 = fromArray(a3);
		Node head4 = fromArray(a4);
		Node head5 = fromArray(a5);
		Node head6 = fromArray(a6);
		Node head7 = fromArray(a7);
		Node head8 = fromArray(a8);

		head1.down = head2;
		head1.next.next.next.down = head3;
		head3.down = head4;
		head4.down = head5;
		head2.next.down = head6;
		head2.next.next.down = head7;
		head7.down = head8;

		return head1;
	}
}

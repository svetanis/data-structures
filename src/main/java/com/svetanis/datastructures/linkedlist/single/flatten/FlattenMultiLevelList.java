package com.svetanis.datastructures.linkedlist.single.flatten;

import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.printNext;

public final class FlattenMultiLevelList {
	// Time Complexity: O(n)

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
		while (curr != tail) {
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
		printNext(root);
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

package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 328. Odd Even Linked List

public final class RearrangeOddEven {
	// Time Complexity: O(n)

	public static Node rearrange(Node head) {

		if (head == null) {
			return head;
		}

		Node odd = head;
		Node even = head.next;
		Node headEven = even;

		while (even != null && even.next != null) {
			// connecting odd nodes
			odd.next = even.next;
			odd = odd.next;

			// connecting even nodes
			even.next = odd.next;
			even = even.next;
		}
		odd.next = headEven;
		return head;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(1, 2, 3, 4, 5));
		print(head);
		head = rearrange(head);
		print(head); // 1,3,5,2,4

		Node head2 = fromList(asList(10, 22, 30, 43, 56, 70));
		print(head2);
		head2 = rearrange(head2);
		print(head2); // 10,30,56,22,43,70

		Node head3 = fromList(asList(2, 1, 3, 5, 6, 4, 7));
		print(head3);
		head3 = rearrange(head3);
		print(head3); // 2,3,6,7,1,5,4
	}
}

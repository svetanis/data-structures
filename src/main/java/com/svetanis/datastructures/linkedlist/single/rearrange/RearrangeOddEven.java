package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 328. Odd Even Linked List
//
// Put the nodes at odd positions (1st, 3rd, 5th, ...) first and the nodes at even positions
// (2nd, 4th, ...) after them, keeping the order inside each group. Returns the head.
//
// Two chains grow side by side: odd sits on the last odd-position node taken so far, even on
// the last even-position node. The node after even is always the next odd one, and the node
// after that is the next even one. No fake node is needed, because the first node of each
// chain is known before the loop starts: the first and second nodes of the list. At the end,
// the odd chain is joined to the start of the even chain.

public final class RearrangeOddEven {
	// Time Complexity: O(n), one pass
	// Space Complexity: O(1), the existing nodes are relinked

	public static ListNode rearrange(ListNode head) {

		if (head == null) {
			return head;
		}

		ListNode odd = head;
		ListNode even = head.next;
		ListNode headEven = even;

		// stop when no odd node is left to take: the list ends at even, or just before it
		while (even != null && even.next != null) {
			// connecting odd nodes
			odd.next = even.next;
			odd = odd.next;

			// connecting even nodes
			even.next = odd.next;
			even = even.next;
		}
		odd.next = headEven; // join: the last odd node points at the first even node
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5));
		print(head);
		head = rearrange(head);
		print(head); // 1,3,5,2,4

		ListNode head2 = fromList(asList(10, 22, 30, 43, 56, 70));
		print(head2);
		head2 = rearrange(head2);
		print(head2); // 10,30,56,22,43,70

		ListNode head3 = fromList(asList(2, 1, 3, 5, 6, 4, 7));
		print(head3);
		head3 = rearrange(head3);
		print(head3); // 2,3,6,7,1,5,4
	}
}

package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 143. Reorder List
//
// Rearrange L0 -> L1 -> ... -> Ln-1 -> Ln into L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...,
// moving nodes, not values.
//
// Three steps that each exist on their own: find the middle, reverse the second half, then
// weave the two halves together one node from each. The first half is the same length as
// the second or one node longer.

public final class Zipping {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static ListNode zip(ListNode head) {
		if (head == null) {
			return null;
		}

		// 1. find the middle point of LL
		ListNode slow = head;
		ListNode fast = head.next;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		ListNode first = head;
		ListNode second = slow.next;
		// 2. split the SLL in two halves
		slow.next = null;
		// 3. reverse the second half
		ListNode reversed = reverse(second);

		// 4. weave: one node from the first half, then one from the reversed second half
		while (first != null && reversed != null) {
			ListNode nextFirst = first.next; // save before first.next is overwritten
			first.next = reversed;
			first = nextFirst;

			// false only at the last pair of an even-length list, where reversed is the last
			// node and its next is already null; the link it skips would change nothing
			if (first != null) {
				ListNode nextReversed = reversed.next; // save before reversed.next is overwritten
				reversed.next = first;
				reversed = nextReversed;
			}
		}
		return head;
	}

	private static ListNode reverse(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	public static void main(String[] args) {
		ListNode head = null;
		for (int i = 8; i >= 1; --i) {
			head = insertAtHead(head, i);
		}
		print(head); // 1 2 3 4 5 6 7 8
		ListNode zipped = zip(head);
		print(zipped); // 1 8 2 7 3 6 4 5
	}
}

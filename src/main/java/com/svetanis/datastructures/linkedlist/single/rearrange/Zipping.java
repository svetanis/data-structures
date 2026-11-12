package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 143. Reorder List
//
// Input: the head of a list L0 -> L1 -> ... -> Ln-1 -> Ln. On LeetCode: 1 <= number of nodes <=
// 5 * 10^4, 1 <= val <= 1000. Rearrange it into L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...,
// moving nodes, not values. This version also returns the head (LeetCode's returns nothing); an
// empty list gives null.
//
// The one idea: the new order takes one node from the front, then one from the back, then the
// next from the front, and a singly linked list cannot be read from the back. So turn the back
// around. Three steps that each exist on their own: find the middle, reverse the second half,
// then weave the two halves together one node from each. The first half is the same length as
// the second or one node longer.
//
// Time: O(n) -- each step is one pass over the list or half of it.
// Space: O(1) -- the halves are relinked in place.
//
// Siblings: ZippingComposed makes the same three steps as three small methods, and its first
// half can be two nodes longer than the second.

public final class Zipping {

	public static ListNode zip(ListNode head) {
		if (head == null) {
			return null; // BASE
		}

		// 1. find the middle point of LL
		ListNode slow = head;
		ListNode fast = head.next;
		while (fast != null && fast.next != null) {
			slow = slow.next; // STEP: one node
			fast = fast.next.next; // STEP: two nodes
		}

		ListNode first = head;
		ListNode second = slow.next;
		// 2. split the SLL in two halves
		slow.next = null; // CUT: the first half ends at the middle
		// 3. reverse the second half
		ListNode reversed = reverse(second);

		// 4. weave: one node from the first half, then one from the reversed second half
		while (first != null && reversed != null) {
			ListNode nextFirst = first.next; // save before first.next is overwritten
			first.next = reversed; // WEAVE: a front node, then a back node
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
			ListNode next = curr.next; // SAVE
			curr.next = prev; // FLIP
			prev = curr; // KEEP
			curr = next; // MOVE
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

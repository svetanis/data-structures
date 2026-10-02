package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 143. Reorder List
//
// Rearrange L0 -> L1 -> ... -> Ln-1 -> Ln into L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...,
// moving nodes, not values.
//
// Built from three methods that each solve a problem on their own: the middle (876), the
// reversal (206), and a weave that is the merge of 21 with the comparison taken out, one node
// from each half in turn. Zipping does the same three steps inline and weaves in place.
//
// The middle here is the second of the two on an even length, so the first half can be two
// nodes longer than the second. The weave still comes out right: those two nodes are
// neighbours at the end of the answer, and the LEFTOVER line attaches them as they are.

public final class ZippingComposed {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static void reorder(ListNode head) {
		ListNode middle = middle(head); // MIDDLE: the last node of the first half
		ListNode second = middle.next; // SAVE the second half before the cut destroys the way to it
		middle.next = null; // CUT: the first half now ends here
		weave(head, reverse(second)); // REVERSE the second half, then WEAVE the two
	}

	private static ListNode middle(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next; // one node
			fast = fast.next.next; // two nodes: fast covers the list while slow covers half
		}
		return slow;
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

	private static ListNode weave(ListNode first, ListNode second) {
		ListNode dummy = new ListNode(); // FAKE, as in 21
		ListNode tail = dummy;
		while (first != null && second != null) {
			tail.next = first; // ATTACH one from the first half
			tail = tail.next;
			first = first.next; // ADVANCE
			tail.next = second; // ATTACH one from the reversed second half
			tail = tail.next;
			second = second.next; // ADVANCE
		}
		if (first != null) {
			tail.next = first; // LEFTOVER: the first half is never the shorter one
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		ListNode head = null;
		for (int i = 8; i >= 1; --i) {
			head = insertAtHead(head, i);
		}
		print(head); // 1 2 3 4 5 6 7 8
		reorder(head);
		print(head); // 1 8 2 7 3 6 4 5

		ListNode head2 = null;
		for (int i = 5; i >= 1; --i) {
			head2 = insertAtHead(head2, i);
		}
		reorder(head2);
		print(head2); // 1 5 2 4 3
	}
}

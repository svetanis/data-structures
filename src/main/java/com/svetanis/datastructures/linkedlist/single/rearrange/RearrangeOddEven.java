package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 328. Odd Even Linked List
//
// Input: the head of a list. On LeetCode: 0 <= number of nodes <= 10^4, -10^6 <= val <= 10^6.
// Put the nodes at odd positions (1st, 3rd, 5th, ...) first and the nodes at even positions
// (2nd, 4th, ...) after them, keeping the order inside each group. Returns the head. Positions,
// not values, decide the groups.
//
// The one idea: the nodes at odd positions are every second node, and so are the even ones, so
// each group is built by skipping over the other group's node. Two chains grow side by side: odd
// sits on the last odd-position node taken so far, even on the last even-position node. The node
// after even is always the next odd one, and the node after that is the next even one. No fake
// node is needed, because the first node of each chain is known before the loop starts: the
// first and second nodes of the list. At the end, the odd chain is joined to the start of the
// even chain.
//
// Time: O(n) -- one pass.
// Space: O(1) -- the existing nodes are relinked.
//
// Siblings: SplitAlternate builds the same two chains and keeps them apart instead of joining them.

public final class RearrangeOddEven {

	public static ListNode rearrange(ListNode head) {

		if (head == null) {
			return head; // BASE: an empty list
		}

		ListNode odd = head;
		ListNode even = head.next;
		ListNode headEven = even; // KEEP the first even node: the odd chain ends by pointing at it

		// stop when no odd node is left to take: the list ends at even, or just before it
		while (even != null && even.next != null) {
			// connecting odd nodes
			odd.next = even.next; // TAKE the next odd node
			odd = odd.next;

			// connecting even nodes
			even.next = odd.next; // TAKE the next even node
			even = even.next;
		}
		odd.next = headEven; // JOIN: the last odd node points at the first even node
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5));
		print(head); // 1 2 3 4 5
		head = rearrange(head);
		print(head); // 1 3 5 2 4

		ListNode head2 = fromList(asList(10, 22, 30, 43, 56, 70));
		print(head2); // 10 22 30 43 56 70
		head2 = rearrange(head2);
		print(head2); // 10 30 56 22 43 70

		ListNode head3 = fromList(asList(2, 1, 3, 5, 6, 4, 7));
		print(head3); // 2 1 3 5 6 4 7
		head3 = rearrange(head3);
		print(head3); // 2 3 6 7 1 5 4
	}
}
